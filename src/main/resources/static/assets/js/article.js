const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        if(confirm('정말로 삭제하시겠습니까?')) {
            let id = document.getElementById('article-id').value;
            fetch(`/api/articles/${id}`, {
                method: 'DELETE',
            })
                .then(() => {
                    alert('삭제가 완료되었습니다.');
                    location.replace('/articles')
                })
        }
    })
}