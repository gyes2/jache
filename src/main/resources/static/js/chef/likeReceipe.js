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
                // 사용자 아이디 비교
            })
            .catch(error => {
                console.error('API 호출 중 오류 발생:', error);
            });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');

    // 찜한 레시피 불러오기
    fetch('http://localhost:8080/api/user/love/list', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('API 요청 실패');
            }
            return response.json();
        })
        .then(async data => {
            const receipeList = data.data;
            const table = document.querySelector('.receipeList table');
            table.innerHTML = ''; // 테이블 내용 초기화

            if (receipeList.length === 0) {
                const noRecipeRow = document.createElement('tr');
                noRecipeRow.innerHTML = '<td colspan="2">찜한 레시피가 없습니다.</td>';
                table.appendChild(noRecipeRow);
            } else {
                receipeList.forEach(receipe => {
                    const row = document.createElement('tr');
                    row.classList.add('receipeContent');

                    const foodImgCell = document.createElement('td');
                    foodImgCell.setAttribute('id', 'foodImg');
                    foodImgCell.setAttribute('rowspan', '2');
                    const img = document.createElement("img");
                    foodImgCell.innerHTML = `<img src="${receipe.imgUrl}" alt="food-img">`;
               /*     img.src = receip.imgUrl;
                    img.alt = receipe.title;*/


                    const titleCell = document.createElement('td');
                    titleCell.setAttribute('id', 'title');

                    const titleLink = document.createElement('a');
                    titleLink.href = `http://localhost:8080/receipe/detailReceipe?receipeId=${receipe.receipeId}`; // 레시피 세부 정보 페이지로 이동
                    titleLink.innerHTML = `<div id="receipeTitle">${receipe.title}</div>`;
                    titleCell.appendChild(titleLink);

                    row.appendChild(foodImgCell);
                    row.appendChild(titleCell);

                    const writerRow = document.createElement('tr');
                    const writerCell = document.createElement('td');
                    const writerLink = document.createElement('a');
                    writerLink.href = `http://localhost:8080/chef/profilePage/${receipe.chefName}`; // 작성자의 프로필 페이지로 이동
                    writerLink.innerHTML = `<div id="writer">작성자: ${receipe.chefName}</div>`;
                    writerCell.appendChild(writerLink);
                    writerRow.appendChild(writerCell);

                    table.appendChild(row);
                    table.appendChild(writerRow);

                });
            }
        })
        .catch(error => {
            console.error('API 오류:', error);
        });
});
