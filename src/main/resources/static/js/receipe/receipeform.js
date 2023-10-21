const token = localStorage.getItem('token');
console.log(token);
const receipeCheckNum = 21; // 상세조회할 레시피 아이디 불러오기

const urlParams = new URLSearchParams(window.location.search);
const receipeId = urlParams.get('receipeId');
let deleteButton = document.getElementById("receipe-delete");
console.log(receipeId);

// 상세 정보 불러오기
async function fetchReceipeDetails() {
    try {
        // API URL을 설정합니다. 실제 API 주소로 수정해주세요.
        const apiUrl = "http://localhost:8080/api/user/receipe/read/detail/" + receipeId;

        var myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer " + token);

        var requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        // fetch를 사용하여 새로운 요청을 보냅니다.
        const response = await fetch(apiUrl, requestOptions);

        if (!response.ok) {
            const errorData = await response.json();
            alert("데이터 조회 실패: " + JSON.stringify(errorData));
        } else {
            const data = await response.json();

            console.log(data);
            // 데이터를 HTML 요소에 할당합니다.
            document.querySelector('input[id="receipe-title"]').value = data.data.title;
            document.querySelector('input[name="작성자"]').value = data.data.chefName;
            document.querySelector('input[name="카테고리"]').value = data.data.theme;
            document.querySelector('input[name="레시피 소개"]').value = data.data.introduce;
            //document.querySelector('.receipe-detail-img').src = data.data.receipeImgUrl;
            let receipeImg = document.getElementById('receipe-img');
            console.log(data.data.receipeImgUrl);
            let imgUrl = data.data.receipeImgUrl;
            receipeImg.src = imgUrl;

            // 재료 정보 표시
            let ingredientContainer = document.querySelector('.form-container-ingredientitems');
            console.log(data.data.ingredients);

            data.data.ingredients.forEach((ingredient, index) => {

                let ingredientDiv = document.createElement("div");
                ingredientDiv.className = "ingredient-div";

                let labelIng = document.createElement("label");
                labelIng.setAttribute("for", "");
                labelIng.id = "ingredient" + (index + 1);
                labelIng.textContent = "재료 " + (index + 1);

                let inputIng = document.createElement("input");
                inputIng.type = "text";
                inputIng.className = "receipe-detail-material";
                inputIng.placeholder = "재료명";
                inputIng.value = ingredient.ingredientName;
                inputIng.disabled = true;

                let inputUnit = document.createElement("input");
                inputUnit.type = "text";
                inputUnit.className = "receipe-detail-material-unit";
                inputUnit.placeholder = "단위";
                inputUnit.value = ingredient.weight;
                inputUnit.disabled = true;

                ingredientDiv.appendChild(labelIng);
                ingredientDiv.appendChild(inputIng);
                ingredientDiv.appendChild(inputUnit);

                ingredientContainer.appendChild(ingredientDiv);

            });

            // 순서 정보 표시
            let orderContainer = document.querySelector('.form-container-orderitems');
            console.log(data.data.orders);

            data.data.orders.forEach((order, index) => {
                let sequenceDiv = document.createElement("div");
                sequenceDiv.className = "form-container-items"; // 클래스 수정

                let orderDiv = document.createElement("div");
                orderDiv.className = "order-div";

                let label = document.createElement("label");
                label.textContent = "순서 " + (index + 1);

                let img = document.createElement("img");
                img.className = "receipe-detail-sequence-img";
                console.log(order.contentUrl);
                img.src = order.contentUrl;

                let textarea = document.createElement("textarea");
                textarea.className = "receipe-detail-sequence";
                textarea.rows = "10";
                textarea.cols = "10";
                textarea.disabled = true;
                textarea.value = order.content;

                orderDiv.appendChild(label);
                sequenceDiv.appendChild(orderDiv); // orderDiv를 sequenceDiv의 자식으로 추가
                sequenceDiv.appendChild(img);
                sequenceDiv.appendChild(textarea);

                orderContainer.appendChild(sequenceDiv);
            });

            // 레시피 작성자 체크
            let chefName = data.data.chefName;
            await receipeCheck(chefName);
        }
    } catch (error) {
        console.error("에러 발생: " + error.message);
    }
}

// 레시피 작성자 체크
async function receipeCheck(chefName) {
    try {
        var myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer " + token);

        var requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        let response = await fetch("http://localhost:8080/api/user/get/isMyReceipe/" + chefName, requestOptions);

        if (!response.ok) {
            const errorData = await response.json();
            alert("데이터 조회 실패: " + JSON.stringify(errorData));
        }

        let data = await response.json();
        let check = data.data

        if (check === true) {
            deleteButton.classList.remove('hidden');
        } else {
            deleteButton.classList.add('hidden');
        }

        console.log(data.data)
    } catch (error) {
        console.error("에러 발생: " + error.message);
    }
}

// 클릭시 하트상태 변경만 가능. 데이터 연결 후에 하트 상태 저장하기
const likeButtons = document.querySelectorAll('.like');

likeButtons.forEach(function (button) {
    button.addEventListener('click', async function () {
        console.log("버튼 이벤트 시작");
        let returnedStatus = await heartStatus();
        console.log(returnedStatus);
        const likeBtn = document.querySelector('.heart');
        const likedBtn = document.querySelector('.heart-liked');

        if (returnedStatus === "N") {
            // 현재 하트가 비어 있는 상태일 때
            await love();
            likeBtn.style.display = "none"; // 빈 하트 숨기기
            likedBtn.style.display = "inline-block"; // 클릭된 하트 표시
        } else {
            // 현재 하트가 클릭된 상태일 때
            await unlove();
            likeBtn.style.display = "inline-block"; // 빈 하트 표시
            likedBtn.style.display = "none"; // 클릭된 하트 숨기기
        }
    });
});

function back() {
    var referrer = document.referrer;
    window.location.href = referrer;
    console.log(referrer);
}

// 페이지 로드 시 상세 정보 불러오기
window.addEventListener("DOMContentLoaded", async function () {
    await fetchReceipeDetails();
});
