function back() { //이전 페이지로 돌아가기
    var referrer = document.referrer;
    window.location.href = referrer;
    console.log(referrer);
}

function modal(id) {
    var zIndex = 9999;
    var modal = document.getElementById(id);

    // 모달 div 뒤에 희끄무레한 레이어
    var bg = document.createElement('div');
    bg.setStyle({
        position: 'fixed',
        zIndex: zIndex,
        left: '0px',
        top: '0px',
        width: '100%',
        height: '100%',
        overflow: 'auto',
        // 레이어 색갈은 여기서 바꾸면 됨
        backgroundColor: 'rgba(0,0,0,0.4)'
    });
    document.body.append(bg);

    // 닫기 버튼 처리, 시꺼먼 레이어와 모달 div 지우기
    modal.querySelector('.modal_close_btn').addEventListener('click', function() {
        bg.remove();
        modal.style.display = 'none';
    });

    modal.setStyle({
        position: 'fixed',
        display: 'block',
        boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',

        // 시꺼먼 레이어 보다 한칸 위에 보이기
        zIndex: zIndex + 1,

        // div center 정렬
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        msTransform: 'translate(-50%, -50%)',
        webkitTransform: 'translate(-50%, -50%)'
    });
}

// Element 에 style 한번에 오브젝트로 설정하는 함수 추가
Element.prototype.setStyle = function(styles) {
    for (var k in styles) this.style[k] = styles[k];
    return this;
};

document.getElementById('popup_open_btn').addEventListener('click', function() {
    // 모달창 띄우기
    modal('my_modal');
});

document.addEventListener('DOMContentLoaded', function () {
    // 이곳에 사용자 아이디와 토큰을 설정하세요
    const chefNameInput = document.getElementById("chefName"); // 현재 사용자의 아이디로 설정
    const chefDetailTextArea = document.getElementById("chefDetail");
    const chefImg = document.getElementById("chefImg");

    const token = localStorage.getItem('token');

    // 사용자 정보 가져오기
    if (token) {
        fetch('http://localhost:8080/api/user/getUserInfo', {
            method: 'get',
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
                const chefDetail = data.data.chefDetail;
                const chefImgUrl = data.data.chefImgUrl;

                // 값들을 DOM 요소에 할당합니다.
                chefNameInput.textContent = apiChefName;
                chefDetailTextArea.textContent = chefDetail;
                chefImg.src = chefImgUrl;
            })
            .catch(error => {
                console.error('API 호출 중 오류 발생:', error);
            });
    }
});