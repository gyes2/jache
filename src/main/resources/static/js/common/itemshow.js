let containers = document.querySelectorAll('.main-item-container');
let hearts = document.querySelectorAll('.heartcheck');
let num = document.getElementById("3");
let heartCheck = true

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



    if(heartCheck && num) {
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
    if (button) {
        button.addEventListener('click', toggleItems);
    }
});

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
