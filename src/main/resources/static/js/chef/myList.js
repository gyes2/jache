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

//글쓰기
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
                    fetch('http://localhost:8080/api/user/receipe/initial', {
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
});

document.addEventListener('DOMContentLoaded', function () {
    const chefNameInput = document.getElementById("chefName"); // 현재 사용자의 아이디로 설정
    const receipeBoxContainer = document.querySelector('#myReceipe');

    const token = localStorage.getItem('token');

    // 사용자 정보 가져오기
    if (token) {
        // fetch(`/user/myreceipe/${theme}`, {
        fetch(`http://localhost:8080/api/user/myreceipe/S`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed Server responded with: ' + response.statusText);
                }
                return response.json();
            })
            .then(async data => {
                console.log(data.data);
                // 받아온 데이터를 사용하여 레시피 목록을 생성

                const myReceipe = document.getElementById("myReceipe");

                myReceipe.innerHTML = "";

                for (const receipe of data.data) {
                    const receipeDiv = document.createElement("div");
                    receipeDiv.className = "receipeBox";
                    receipeDiv.setAttribute('dataReceipeId', receipe.receipeId);
                    receipeDiv.addEventListener("click", function () {
                        window.location.href = `http://localhost:8080/receipe/detailReceipe?receipeId=${receipe.receipeId}`;
                    });

                    const imgDiv = document.createElement("div");
                    imgDiv.className = "receipeImg"

                    const img = document.createElement("img");
                    img.src = receipe.imgUrl;
                    img.alt = receipe.title;

                    imgDiv.appendChild(img);

                    const textDiv = document.createElement("div");
                    textDiv.className = "main-item-text";

                    /*const heartDiv = document.createElement("div");
                    heartDiv.className = "heart";

                    const iTag = document.createElement("i");
                    const heartStatus = await checkHeartStatus(receipe.receipeId);

                      iTag.classList.add('fa');
                      if (heartStatus === 'y') {
                          iTag.classList.remove('fa-heart-o');
                          iTag.classList.add('fa-heart');
                      } else {
                          iTag.classList.add('fa-heart-o');
                          iTag.classList.remove('fa-heart');
                      }

                    iTag.setAttribute("aria-hidden", "true");

                    heartDiv.appendChild(iTag);*/

                    const titleDiv = document.createElement("div");
                    titleDiv.className = "receipeTitle";
                    titleDiv.innerHTML = `<p>${receipe.title}</p>`;
                    console.log(titleDiv.innerText);

                    const categoryDiv = document.createElement("div");
                    categoryDiv.className = "receipeCategory";
                    categoryDiv.innerHTML = "<p>카테고리</p>";
                    console.log(categoryDiv.innerHTML);

                    const recipeDiv = document.createElement("div");
                    recipeDiv.className = "receipeContent";
                    recipeDiv.innerHTML = `<p>${receipe.introduce}</p>`;
                    console.log(recipeDiv.innerHTML);

                    const chefDiv = document.createElement("div");
                    chefDiv.className = "receipeChef";
                    chefDiv.innerHTML = `<p>${receipe.chefName}</p>`;
                    console.log(chefDiv.innerHTML);

                    // textDiv.appendChild(heartDiv);
                    textDiv.appendChild(titleDiv);
                    textDiv.appendChild(categoryDiv);
                    textDiv.appendChild(recipeDiv);
                    textDiv.appendChild(chefDiv);

                    receipeDiv.appendChild(imgDiv);
                    receipeDiv.appendChild(textDiv);

                    receipeBoxContainer.appendChild(receipeDiv);
                }
               /*updateContainersAndHearts();
                toggleItems();*/
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
        }
});
