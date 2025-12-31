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
        const title = getTitleValue();
        if (!title) {
            alert('제목을 입력해 주세요.');
            return;
        }
        let categoryIds = getSelectedCategoryIds();
        let body = JSON.stringify({
            title: title,
            content: editor.getData(),
            type: document.querySelector('input[name="type"]:checked').value,
            categoryIds: categoryIds
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
        const title = getTitleValue();
        if (!title) {
            alert('제목을 입력해 주세요.');
            return;
        }
        let categoryIds = getSelectedCategoryIds();
        let body = JSON.stringify({
            title: title,
            content: editor.getData(),
            type: document.querySelector('input[name="type"]:checked').value,
            categoryIds: categoryIds
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

function getTitleValue() {
    const input = document.getElementById('title');
    if (!input) {
        return '';
    }
    return input.value.trim();
}

function getSelectedCategoryIds() {
    const input = document.getElementById('category-ids');
    if (!input || !input.value.trim()) {
        return [];
    }
    return input.value
        .split(',')
        .map(value => Number(value.trim()))
        .filter(value => Number.isFinite(value) && value > 0);
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
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
            if (response.status === 401) {
                fetch('/api/token', {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json'
                    }
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
