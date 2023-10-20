const token = localStorage.getItem('token');
console.log(token);
const receipeCheckNum = 1; //상세조회할 레시피 아이디 불러오기

window.addEventListener("DOMContentLoaded", function() {
    fetchReceipeDetails();
});

async function fetchReceipeDetails() {
    // API URL을 설정합니다. 실제 API 주소로 수정해주세요.
    const apiUrl = "http://localhost:8080/api/user/receipe/read/detail/"+receipeCheckNum;

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
    document.querySelector(".count").value = data.data.loveCount;

    //document.querySelector('.receipe-detail-img').src = data.data.receipeImgUrl;
    let receipeImg = document.getElementById('"receipe-img"');
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
    button.addEventListener('click', function () {
        if (likedBtn.style.display === "none") {
            // 현재 하트가 비어 있는 상태일 때
            likeBtn.style.display = "none"; // 빈 하트 숨기기
            likedBtn.style.display = "inline-block"; // 클릭된 하트 표시
        } else {
            // 현재 하트가 클릭된 상태일 때
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

function initializeReceipeDetail(){

}