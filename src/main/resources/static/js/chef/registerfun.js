const checkEmail = document.getElementById("reg-email");
const checkName = document.getElementById("reg-userName");
function checkChefEmail(chefemail) {
    $.ajax({
        url: '/api/email-verification/',
        type: 'POST',
        dataType: 'json',
        data: {email: chefemail},
        success: function (response) {
            // 성공적으로 데이터를 받아온 경우
            console.log(response);

            // 예: 서버로부터 받은 응답을 기반으로 UI 변경
            if (response.isAvailable) {
                alert('인증 메일 전송!');
            } else {
                alert('인증 메일 실패.');
            }
        },
        error: function (error) {
            // 에러 발생 시
            console.error('Error fetching data:', error);
        }
    });
}
    checkChefEmail(checkEmail.value);


function checkChefName(chefname) {
    $.ajax({
        url: '/api/check/name/' + chefname,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            // 성공적으로 데이터를 받아온 경우
            console.log(response);

            // 예: 서버로부터 받은 응답을 기반으로 UI 변경
            if (response.isAvailable) {
                alert('사용 가능한 이름입니다!');
            } else {
                alert('이미 사용 중인 이름입니다.');
            }
        },
        error: function(error) {
            // 에러 발생 시
            console.error('Error fetching data:', error);
        }
    });

}



checkChefName(checkName.value);

document.getElementById('register-form-id').addEventListener('submit', function(event) {
    // 폼 제출 동작 중단
    event.preventDefault();

    // 폼 데이터를 가져옵니다.
    let formData = new FormData(this);

    // fetch API를 사용하여 비동기 요청을 보냅니다.
    fetch('/api/all/join', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json()) // 예상되는 응답 형식이 JSON이라고 가정합니다.
        .then(data => {
            if (data.success) {
                // 로그인이 성공한 경우, 여기에 코드를 추가하세요.
                alert('가입 완료!');
            } else {
                // 로그인이 실패한 경우, 여기에 코드를 추가하세요.
                alert('가입 실패: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
});