const token = localStorage.getItem('token');
document.addEventListener('DOMContentLoaded', function () {
    // 이곳에 사용자 아이디와 토큰을 설정하세요
    const chefNameInput = document.getElementById("chefName"); // 현재 사용자의 아이디로 설정
    const chefDetailTextArea = document.getElementById("chefDetail");
    const chefImg = document.getElementById("chefImg");

    const token = localStorage.getItem('token');

    // 사용자 정보 가져오기
    if (token) {
        fetch('http://localhost:8080/api/user/getUserInfo', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 상태가 좋지 않습니다.');
                }
                return response.json();
            })
            .then(data => {
                // 데이터에서 값을 추출합니다.
                const apiChefName = data.data.chefName;
                let chefDetial = data.data.chefDetial;
                const chefImgUrl = data.data.chefImgUrl;

                // 값들을 DOM 요소에 할당합니다.
                chefNameInput.textContent = apiChefName;

                if(chefDetail == null || chefDetail == ""){
                    chefDetailTextArea.innerHTML = "안녕하세요! <br>" + apiChefName + " 입니다!";
                }else{
                    chefDetailTextArea.textContent = chefDetail;
                }

                chefImg.src = chefImgUrl;
            })
            .catch(error => {
                console.error('API 호출 중 오류 발생:', error);
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
            window.location.href = "/main";
        })
        .catch(error => {
            console.error('There was an error!', error);
        });
}

// 버튼 업데이트 함수
function updateButtons(apiChefName, chefName) {
    const menuButtons = document.getElementById("buttonContainer");
    menuButtons.forEach(button => {
        const menuButtonMyList = document.getElementById('menuButtonMyList');
        const menuButtonLikeRecipe = document.getElementById('menuButtonLikeRecipe');
        const menuButtonChat = document.getElementById('menuButtonChat');
        const menuButtonInfoEdit = document.getElementById('menuButtonInfoEdit');
        if (apiChefName == chefName) {
            // 현재 사용자와 API에서 가져온 사용자가 동일한 경우
            // 내가 쓴 글, 찜한 레시피, 나의 정보 공유, 내 정보 수정 버튼을 표시
            menuButtonMyList.style.display = "block";
            menuButtonLikeRecipe.style.display = "block";
            menuButtonChat.style.display = "block";
            menuButtonInfoEdit.style.display = "block";
        } else {
            // 사용자가 동일하지 않은 경우 두 버튼을 숨깁니다.
            menuButtonMyList.style.display = "block";
            menuButtonLikeRecipe.style.display = "none";
            menuButtonChat.style.display = "block";
            menuButtonInfoEdit.style.display = "none";
        }
    });
}
});
