document.addEventListener('DOMContentLoaded', function() {
    //const token = localStorage.getItem('token');

    // 찜한 레시피 불러오기
    fetch('/api/love/list', {
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
        .then(data => {
            const receipeList = data.data;
            const table = document.querySelector('.receipeList table');
            table.innerHTML = ''; // 테이블 내용 초기화

            if (receipeList.length === 0) {
                table.innerHTML = '<tr><td colspan="2">찜한 레시피가 없습니다.</td></tr>';
            } else {
                receipeList.forEach(receipe => {
                    const row = document.createElement('tr');
                    row.classList.add('receipeContent');

                    const foodImgCell = document.createElement('td');
                    foodImgCell.setAttribute('id', 'foodImg');
                    foodImgCell.setAttribute('rowspan', '2');
                    foodImgCell.innerHTML = `<img src="${receipe.foodImgUrl}" alt="food-img">`;

                    const titleCell = document.createElement('td');
                    titleCell.setAttribute('id', 'title');

                    const titleLink = document.createElement('a');
                    titleLink.href = `/chef/profilePage/${receipe.chef}`; // 작성자의 프로필 페이지로 이동
                    titleLink.innerHTML = `<div id="receipeTitle">${receipe.title}</div>`;

                    titleCell.appendChild(titleLink);
                    titleCell.innerHTML += `<br><br><div id="writer">작성자: ${receipe.chef}</div>`;

                    row.appendChild(foodImgCell);
                    row.appendChild(titleCell);

                    table.appendChild(row);
                });
            }
        })
        .catch(error => {
            console.error('API 오류:', error);
        });
});
