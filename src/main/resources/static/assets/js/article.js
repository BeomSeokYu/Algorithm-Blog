const deleteButton = document.getElementById('delete-btn');
if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        if(confirm('정말로 삭제하시겠습니까?')) {
            let id = document.getElementById('article-id').value;

            function success() {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles')
            }

            function fail() {
                alert('삭제에 실패하였습니다.');
                location.replace('/articles')
            }
            httpRequest("DELETE", "/api/articles/" + id, null, success, fail);
        }
    })
}

const modifyButton = document.getElementById('modify-btn');
if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');
        let body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });

        function success() {
            alert('수정이 완료되었습니다.');
            location.replace(`/articles/` + id)
        }

        function fail() {
            alert('수정에 실패하였습니다.');
            location.replace('/articles/' + id)
        }

        httpRequest("PUT", "/api/articles/" + id, body, success, fail)
    })
}

const createButton = document.getElementById('create-btn');
if (createButton) {
    createButton.addEventListener('click', event => {
        let body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });

        function success() {
            alert('등록이 완료되었습니다.')
            location.replace(`/articles`)
        }

        function fail() {
            alert('등록에 실패하였습니다.')
            location.replace(`/articles`)
        }
        httpRequest("POST", "/api/articles", body, success, fail)
    })
}

// 쿠키 가져오는 함수
function getCookie(key) {
    let result = null;
    let cookie = document.cookie.split(";");
    cookie.some(function (item) {
        item = item.replace(" ", "");
        let dic = item.split("=");
        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    })

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch('/api/articles', {
        method: method,
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json'
        },
        body: body
    })
        .then(response => {
            if (response.status === 200 || response.status === 201) {
                return success();
            }
            const refresh_token = getCookie("refresh_token");
            if (response.status === 401 && refresh_token) {
                fetch('/api/token', {
                    method: "POST",
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('access_token'),
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        refreshToken: getCookie("refresh_token")
                    })
                })
                    .then((res) => {
                        if (res.ok) {
                            return res.json();
                        }
                    })
                    .then((result) => {
                        localStorage.setItem('access_token', result.accessToken);
                        httpRequest(method, url, body, success, fail);
                    })
                    .catch(() => fail());
            } else {
                return fail();
            }
        })
}