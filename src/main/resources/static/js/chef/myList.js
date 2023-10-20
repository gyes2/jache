/*const token = localStorage.getItem('token');
document.addEventListener('DOMContentLoaded', function() {
    // 페이지가 로드되면 실행

    const writeUrl = '/api/user/receipe/initial';
    const chefNameInput = document.getElementById("chefName");
    // 글쓰기 버튼 클릭 시
    document.getElementById('writeButton').addEventListener('click', function () {
        // API 호출을 통해 글 작성자 확인
        fetch(writeUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
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
                    // 내 글일 경우 서버에서 receipeId 가져오기
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
                                throw new Error('Failed to fetch receipeId');
                            }
                            return response.json();
                        })
                        .then(data => {
                            const receipeId = data.receipeId;

                            // 글쓰기 페이지로 이동 및 receipeId 전달
                            location.href = `/receipe/receipe-form/${receipeId}`;
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
    });

    // 각 레시피 상자에 대한 클릭 이벤트 처리
    const receipeBoxes = document.querySelectorAll('.receipeBox');*/

// myList.js
document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');

    // 글쓰기 버튼 클릭 시 처리 함수
    function handleWriteButtonClick() {
        fetch('/api/user/receipe/initial', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
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
                            const recipeId = data.recipeId;

                            // 글쓰기 페이지로 이동 및 recipeId 전달
                            location.href = `/receipe/receipe-form/${recipeId}`;
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
    const recipeBoxes = document.querySelectorAll('.recipeBox');

    recipeBoxes.forEach(box => {
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
