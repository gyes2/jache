// 이곳에 사용자 아이디와 토큰을 설정하세요
const chefNameInput = document.getElementById("chefName"); // 현재 사용자의 아이디로 설정
const chefDetailTextArea = document.getElementById("chefInfo");
const chefImg = document.getElementById('chefImgUrl');

const token = localStorage.getItem('token');
console.log(token);

window.addEventListener("DOMContentLoaded", async function () {
    await getInfo();
});

async function getInfo(){
    // 사용자 정보 가져오기
    if (token) {
        let response = await fetch('http://localhost:8080/api/user/getUserInfo', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
        if (!response.ok) {
            const errorData = await response.json();
            alert("데이터 조회 실패: " + JSON.stringify(errorData));
        }

        let data = await response.json();

        // 데이터에서 값을 추출합니다.
        const apiChefName = data.data.chefName;
        const chefDetail = data.data.chefDetial;
        const chefImgUrl = data.data.chefImgUrl;

        // 값들을 DOM 요소에 할당합니다.
        chefNameInput.textContent = apiChefName;
        chefDetailTextArea.textContent = chefDetail;
        chefImg.src = chefImgUrl;

    }
}
document.getElementById("deleteImg").addEventListener("click", async function(){
    await deleteProfileImage();
});
async function deleteProfileImage() {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Bearer "+token);

    var raw = JSON.stringify({
        "chefImgUrl": chefImg.src
    });

    var requestOptions = {
        method: 'DELETE',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };
    // 프로필 사진 삭제 API 호출
    let response = await fetch('http://localhost:8080/api/user/my/delete/img', requestOptions);

    if (!response.ok) {
        const errorData = await response.json();
        alert("데이터 삭제 실패: " + JSON.stringify(errorData));
    }

    let data = await response.json();

    chefImg.src = data.data.chefImgUrl;
}

// 프로필 사진 수정
document.getElementById("modifyImg").addEventListener("click", async function (e) {
    e.preventDefault();

    var myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer " + token);

    var formData = new FormData();
    //이미지
    let imageFile = document.getElementById('img').files[0];

    if (imageFile) {
        formData.append("myImg", imageFile, imageFile.name);
    }


    var requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        body: formData,
        redirect: 'follow'
    };

    let response = await fetch("http://localhost:8080/api/user/my/update/img", requestOptions);

    if(!response.ok) {

        const errorData = await response.json();
        alert("데이터 수정 실패: " + JSON.stringify(errorData));
    }
    let data = await response.json();
    console.log(data.data.updateImgUrl);

    chefImg.src = data.data.updateImgUrl;

});

document.getElementById('modify').addEventListener('click', async function(){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Bearer "+token);

    console.log(chefDetailTextArea.value);
    var raw = JSON.stringify({
        "chefDetails": chefDetailTextArea.value
    });

    var requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    let response = await fetch("http://localhost:8080/api/user/my/update/details", requestOptions);

    if(!response.ok){
        const errorData = await response.json();
        alert("데이터 수정 실패: " + JSON.stringify(errorData));
    }
    let data = await response.json();
    console.log(data);

    chefDetailTextArea.value = data.data.chefDetails;
});