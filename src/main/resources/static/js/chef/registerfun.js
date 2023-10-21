let checkEmail = document.getElementById("reg-email");
let checkName = document.getElementById("reg-userName");
let checkEmailNum = "";
let isEmailVerified = false;
let isNameAvailable = false;
async function checkChefEmail() {
    try {
        const response = await fetch(`http://localhost:8080/api/all/email-verification/${checkEmail.value}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();
        console.log(data);

        if (data.isSuccess) {
            alert('인증 메일 전송!');
            checkEmailNum = data.data;

        } else {
            alert('인증 메일 실패.');
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}


async function checkChefName() {
    try {
        const response = await fetch(`http://localhost:8080/api/all/check/name/${checkName.value}`);

        const data = await response.json();
        console.log(data);

        if (data.isSuccess) {
            alert('사용 가능한 이름입니다!');
            isNameAvailable = true;
        } else {
            alert('이미 사용 중인 이름입니다.');
            isNameAvailable = false;
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

function checkEmailAuth(){
    let emailAuthNumber = document.getElementById("reg-authNumber")
    if(emailAuthNumber.value === checkEmailNum){
        alert('이메일 인증 완료!');
        isEmailVerified = true;
    } else {
        alert('다시 확인해 주세요.');
        isEmailVerified = false;
    }
}

function finalRegCheck() {
    if (!isEmailVerified || !isNameAvailable) {
        alert('이메일 인증 또는 이름 확인을 통과하지 못했습니다. 다시 확인해주세요.');
    } else {
        sendRegData()
    }

}

function sendRegData(){
    let phone = document.getElementById("reg-phone").value;
    let email = document.getElementById("reg-email").value;
    let password= document.getElementById("reg-password").value;
    let chefName = document.getElementById("reg-userName").value;

    const data = {
        "chefName": chefName,
        "password": password,
        "phone": phone,
        "email": email
    };

    fetch('http://localhost:8080/api/all/join', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json()) // 예상되는 응답 형식이 JSON이라고 가정합니다.
        .then(data => {
            if (data.success) {
                // 로그인이 성공한 경우, 여기에 코드를 추가하세요.
                window.location.href = '/main';
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
}


