document.getElementById('login-form-id').addEventListener('submit', function(event) {
    // 폼 제출 동작 중단
    event.preventDefault();

    // 폼 데이터를 가져옵니다.
    let formData = new FormData(this);

    // fetch API를 사용하여 비동기 요청을 보냅니다.
    fetch('/api/login', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json()) // 예상되는 응답 형식이 JSON이라고 가정합니다.
        .then(data => {
            if (data.success) {
                // 로그인이 성공한 경우, 여기에 코드를 추가하세요.
                alert('로그인 성공!');
            } else {
                // 로그인이 실패한 경우, 여기에 코드를 추가하세요.
                alert('로그인 실패: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
});