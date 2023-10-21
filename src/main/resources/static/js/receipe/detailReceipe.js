const token = localStorage.getItem('token');
console.log(token);
const receipeCheckNum = 21; //상세조회할 레시피 아이디 불러오기

const urlParams = new URLSearchParams(window.location.search);
const receipeId = urlParams.get('receipeId');
console.log(receipeId);

window.addEventListener("DOMContentLoaded", async function () {
    await fetchReceipeDetails();
    let returnedStatus = await heartStatus();
    if (returnedStatus === "N") {
        //좋아요안한 상태
        likeBtn.style.display = "inline-block"; // 빈 하트 표시
        likedBtn.style.display = "none"; // 클릭된 하트 숨기기
    }
    else {
        //현재 좋아요한 상태니까
        likeBtn.style.display = "none"; // 빈 하트 숨기기
        likedBtn.style.display = "inline-block"; // 클릭된 하트 표시
    }
});

async function fetchReceipeDetails() {
    // API URL을 설정합니다. 실제 API 주소로 수정해주세요.
    const apiUrl = "http://localhost:8080/api/user/receipe/read/detail/"+receipeId;

    var myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer "+token);

    var requestOptions = {
        method: 'GET',
        headers : myHeaders,
        redirect: 'follow'
    };

    let response = await fetch(apiUrl, requestOptions);


    if (!response.ok) {
        const errorData = await response.json();
        alert("데이터 조회 실패: " + JSON.stringify(errorData));
    }

    let data = await response.json();

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
    // let ingredientsText = '';
    // if (data.data.ingredients) {
    //     data.data.ingredients.forEach(ingredient => {
    //         ingredientsText += `${ingredient.ingredientName} (${ingredient.weight})\n`;
    //     });
    //     document.querySelector('.ingredient textarea').textContent = ingredientsText;
    // }

    let ingredientContainer = document.querySelector('.form-container-ingredientitems');
    console.log(data.data.ingredients);

    data.data.ingredients.forEach((ingredient, index) => {

        let ingredientDiv = document.createElement("div");
        ingredientDiv.className = "form-container-items";

        ingredientDiv.dataset.ingredientId = ingredient.ingredientId;

        let labelIng = document.createElement("label");
        labelIng.setAttribute("for", "");
        labelIng.id = "ingredient" + (index + 1);
        labelIng.textContent = "재료 " + (index + 1);

        let inputIng = document.createElement("input");
        inputIng.type = "text";
        inputIng.className = "receipe-form-material";
        inputIng.placeholder = "재료명";
        inputIng.value = ingredient.ingredientName;
        inputIng.disabled = true;

        let inputUnit = document.createElement("input");
        inputUnit.type = "text";
        inputUnit.className = "receipe-form-material-unit";
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
        sequenceDiv.className = "form-container-items";

        sequenceDiv.dataset.orderId = order.orderId;

        let label = document.createElement("label");
        label.textContent = "요리 순서 " + (index + 1);

        let textarea = document.createElement("textarea");
        textarea.className = "receipe-detail-sequence";
        textarea.rows = "10";
        textarea.cols = "10";
        textarea.disabled = true;
        textarea.value = order.content;

        let img = document.createElement("img");
        img.className = "receipe-detail-sequence-img";
        console.log(order.contentUrl);
        img.src = order.contentUrl;

        sequenceDiv.appendChild(label);
        sequenceDiv.appendChild(textarea);
        sequenceDiv.appendChild(img);

        orderContainer.appendChild(sequenceDiv);

    });


}




//클릭시 하트상태 변경만 가능. 데이터 연결 후에 하트 상태 저장하기
const likeButtons = document.querySelectorAll('.like');
const likeBtn = document.querySelector('.heart');
const likedBtn = document.querySelector('.heart-liked');

likeButtons.forEach(function (button) {
    button.addEventListener('click', async function () {
        console.log("버튼 이벤트 시작");
        let returnedStatus = await heartStatus();
        console.log(returnedStatus);
        if (returnedStatus === "N"){
            // 현재 하트가 비어 있는 상태일 때
            await love();
            likeBtn.style.display = "none"; // 빈 하트 숨기기
            likedBtn.style.display = "inline-block"; // 클릭된 하트 표시
        }
        else{
            // 현재 하트가 클릭된 상태일 때
            await unlove();
            likeBtn.style.display = "inline-block"; // 빈 하트 표시
            likedBtn.style.display = "none"; // 클릭된 하트 숨기기
        }


        // if (likedBtn.style.display === "none") {
        //     // 현재 하트가 비어 있는 상태일 때
        //     likeBtn.style.display = "none"; // 빈 하트 숨기기
        //     likedBtn.style.display = "inline-block"; // 클릭된 하트 표시
        // } else {
        //     // 현재 하트가 클릭된 상태일 때
        //     likeBtn.style.display = "inline-block"; // 빈 하트 표시
        //     likedBtn.style.display = "none"; // 클릭된 하트 숨기기
        // }
    });
});

function back() {
    var referrer = document.referrer;
    window.location.href = referrer;
    console.log(referrer);
}


async function heartStatus(){

    var myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer "+token);

    var requestOptions = {
        method: 'GET',
        headers : myHeaders,
        redirect: 'follow'
    };

    let response = await fetch("http://localhost:8080/api/user/love/check/status/"+receipeId, requestOptions);


    if (!response.ok) {
        const errorData = await response.json();
        alert("데이터 조회 실패: " + JSON.stringify(errorData));
    }

    let data = await response.json();

    let status = data.data.status;

    return status;
}

async function love(){
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Bearer "+token);

    const raw = JSON.stringify({
        "receipeId": receipeId
    });

    const requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    let response = await fetch("http://localhost:8080/api/user/love", requestOptions);

    if (!response.ok) {
        const errorData = await response.json();
        alert("데이터 조회 실패: " + JSON.stringify(errorData));
    }
}

async function unlove() {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Bearer " + token);

    var requestOptions = {
        method: 'DELETE',
        headers: myHeaders,
        redirect: 'follow'
    };

    let response = await fetch("http://localhost:8080/api/user/unlove/"+receipeId, requestOptions)


    if (!response.ok) {
        const errorData = await response.json();
        alert("데이터 조회 실패: " + JSON.stringify(errorData));
    }
}