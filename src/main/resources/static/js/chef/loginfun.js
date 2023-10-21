function dataSend(){
    const chefName = document.getElementById("login-userName").value;
    const password = document.getElementById("login-Password").value;

    const data = {
        chefName: chefName,
        password: password
    };

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
        redirect: 'follow'
    };

    fetch("http://localhost:8080/api/all/signin", requestOptions)
        .then(response => response.json())  // 응답을 JSON 형태로 받아옵니다.
        .then(result => {
            console.log(result);
            if(result.isSuccess === true) {  // 서버 응답을 기반으로 조건을 확인
                localStorage.setItem('token', result.data.token);
                window.location.href = '/main';  // 성공시 /main 로 리디렉션
            } else {
                // 실패시 다른 액션을 취하거나 사용자에게 메시지를 표시
            }
        })
        .catch(error => console.log('error', error));
}
