// 글쓰기 버튼 클릭 시

const token = localStorage.getItem('token');
const num = "1";

document.getElementById('writeButton').addEventListener('click', function () {
    // 서버에서 receipeId 가져오기
    fetch('/api/user/receipe/initial', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token, // 토큰을 추가
        },
        credentials: 'same-origin',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch receipeId');
            }
            return response.json();

        })
        .then(data => {
            num = data.receipeId;

            // 글쓰기 페이지로 이동 및 receipeId 전달
            location.href = '/receipe/receipe-form/' + num;
        })
        .catch(error => {
            console.error('Error:', error);
        });
    console.log(token);
});