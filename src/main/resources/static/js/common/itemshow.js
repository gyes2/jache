let containers = document.querySelectorAll('.main-item-container');
let hearts = document.querySelectorAll('.heartcheck');
let heartCheck = true
const token = localStorage.getItem('token');

document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM이 로드되었습니다.');

    let includes = document.querySelectorAll('[data-include]');
    Array.prototype.forEach.call(includes, function (element) {
        let path = element.getAttribute('data-include');
        fetch(path)
            .then(response => response.text())
            .then(data => {
                element.innerHTML = data;
                initializeEventHandlers(element);
            });
    });

    if(heartCheck) {
        hearts.forEach(singleHeart => {
            singleHeart.classList.remove("fa-heart");
            singleHeart.classList.add("fa-heart-o");
        });
    } else{
        hearts.forEach(singleHeart => {
            singleHeart.classList.add("fa-heart");
            singleHeart.classList.remove("fa-heart-o");
        });
    }

    for (var i = 4; i < containers.length; i++) {
        containers[i].classList.toggle('hidden');
    }

    // 버튼 클릭 이벤트 처리는 여기서 바로 설정
    let button = document.getElementById('login-button');
    if (token) {
        let loginChefName = document.getElementById("main-aside-login-username");
        let loginChefImg = document.getElementById("main-aside-login-img-id");
        let loginChefText = document.getElementById("main-aside-login-overview");

        toggleItems();

        fetch('http://localhost:8080/api/user/getUserInfo', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                // 유효하지 않은 토큰에 대한 서버 응답을 확인
                if (!response.ok) {
                    throw new Error('Invalid Token');
                }
                return response.json();
            })
            .then(data => {
                // 데이터에서 값을 추출합니다.
                let chefName = data.data.chefName;
                let chefDetail = data.data.chefDetail;
                let chefImgUrl = data.data.chefImgUrl;

                // 값들을 DOM 요소에 할당합니다.
                loginChefName.textContent = chefName;
                loginChefText.textContent = chefDetail;
                loginChefImg.src = chefImgUrl;
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }

});

function logout(){
    let logoutA = document.getElementById("logout-a");
    fetch('http://localhost:8080/api/user/logout', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to logout. Server responded with: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            // 로그아웃 성공 후 토큰 삭제
            localStorage.removeItem('token');
            // 추가적인 로그아웃 후 처리 로직
            alert('Successfully logged out!');
            toggleItems();
            logoutA.href = "/main";
        })
        .catch(error => {
            console.error('There was an error!', error);
        });
}


function initializeEventHandlers(element) {
    if (element.querySelectorAll('.main-item-container').length > 0) {
        toggleItems();
    }
}

function toggleItems() {
    for (var i = 4; i < containers.length; i++) {
        containers[i].classList.toggle('hidden');
    }

    let mainAsideLogin = document.getElementById('main-aside-login-html');
    let mainAside = document.getElementById('main-aside-html');

    if (mainAsideLogin && mainAside) {
        mainAside.classList.toggle('hidden');
        mainAsideLogin.classList.toggle('hidden');
    }
}
