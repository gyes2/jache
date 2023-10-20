
document.addEventListener('DOMContentLoaded', function () {
    // 이곳에 사용자 아이디와 토큰을 설정하세요
    const chefNameInput = document.getElementById("chefName"); // 현재 사용자의 아이디로 설정
    const chefDetailTextArea = document.getElementById("chefDetail");
    const chefImg = document.getElementById("chefImg");

    const token = localStorage.getItem('token');

    // 사용자 정보 가져오기
    if (token) {
        fetch('/api/user/getUserInfo', {
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

                const chefName = chefNameInput.innerText;
                // 사용자 아이디 비교
                updateButtons(apiChefName, chefName);
            })
            .catch(error => {
                console.error('API 호출 중 오류 발생:', error);
                console.log(chefName);

                // 아무 동작을 하지 않고 버튼을 유지하려면 여기에 추가 로직을 작성하지 않습니다.
            });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');

    // 글쓰기 버튼 클릭 시 처리 함수
    function handleWriteButtonClick() {
        fetch('/api/user/receipe/initial', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('API 요청 실패');
                }
                return response.json();
            })
            .then(data => {
                const isMyRecipe = data.data;

                // 내 글인지 확인 후 처리
                if (isMyRecipe) {
                    // 내 글일 경우 서버에서 recipeId 가져오기
                    fetch('/api/user/receipe/initial', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + token,
                        },
                        credentials: 'same-origin',
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Failed to fetch recipeId');
                            }
                            return response.json();
                        })
                        .then(data => {
                            const receipeId = data.data.receipeId;

                            if (receipeId !== null) {
                                // 글쓰기 페이지로 이동 및 receipeId 전달
                                location.href = `/receipe/receipe-form/${receipeId}`;
                            } else {
                                // 서버에서 유효한 receipeId가 반환되지 않은 경우 처리
                                console.log('유효한 receipeId가 없습니다.');
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
                } else {
                    // 내 글이 아닐 경우 처리
                    console.log('이 글은 내가 작성한 글이 아닙니다.');
                }
            })
            .catch(error => {
                console.error('API 오류:', error);
            });
    }

    // 글쓰기 버튼 클릭 이벤트 리스너 등록
    const writeButton = document.getElementById('writeButton');
    writeButton.addEventListener('click', handleWriteButtonClick);

    // 각 레시피 상자에 대한 클릭 이벤트 처리 로직
    const receipeBoxes = document.querySelectorAll('.receipeBox');

    receipeBoxes.forEach(box => {
        receipeBoxes.forEach(box => {
            const titleLink = box.querySelector('a');
            const writerLink = box.querySelectorAll('a')[1];

            titleLink.addEventListener('click', function (event) {
                // 레시피 제목 클릭 시 해당 레시피로 이동
                event.preventDefault(); // 기본 링크 동작 방지
                const receipeLink = titleLink.getAttribute('href');
                location.href = receipeLink;
            });

            writerLink.addEventListener('click', function (event) {
                // 작성자 이름 클릭 시 작성자 정보 페이지로 이동
                event.preventDefault();
                const chefProfileLink = writerLink.getAttribute('href');
                location.href = chefProfileLink;
            });
        });
    })
});
