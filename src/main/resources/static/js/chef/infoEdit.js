// 이곳에 사용자 아이디와 토큰을 설정하세요
const chefNameInput = document.getElementById("chefName"); // 현재 사용자의 아이디로 설정
const chefDetailTextArea = document.getElementById("chefInfo");
const chefImg = document.getElementById("profileImg");

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
        })
        .catch(error => {
            console.error('API 호출 중 오류 발생:', error);
            console.log(chefName);
        });
}
function deleteProfileImage() {
    // 프로필 사진 삭제 API 호출
    fetch('/api/user/my/delete/img', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token,
        },
        body: JSON.stringify({ chefImgUrl: chefImg.src }), // 프로필 이미지 URL 전달
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('프로필 사진 삭제 실패');
            }
            return response.json();
        })
        .then(data => {
            // 성공한 경우에 대한 처리
            console.log('프로필 사진이 삭제되었습니다.');
            // 이미지를 기본 이미지로 변경
            chefImg.src = '/static/img/login.jpg';
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// 프로필 사진 수정
function updateProfileImage() {
   /* const fileInput = document.getElementById('profileImg');
    const file = fileInput.files[0];
    if (file) {
        const formdata = new FormData();
        formdata.append('myImg', file);

        // 프로필 사진 수정 API 호출
        fetch('/api/user/my/update/img', {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            body: formdata,
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('프로필 사진 수정 실패');
                }
                return response.json();
            })
            .then(data => {
                // 성공한 경우에 대한 처리
                console.log('프로필 사진이 수정되었습니다.');
                // 이미지를 수정한 이미지로 변경
                chefImg.src = URL.createObjectURL(file);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    } else {
        console.log('파일을 선택해주세요.');
    }*/
    document.getElementById("modify").addEventListener("click", function(e) {
        e.preventDefault();

        let formData = new FormData();
        //json
        let jsonData = {
            profileImg: chefImg.value,
            userId : chefNameInput.value,
            info : chefDetailTextArea.value
        };
        formData.append("chef", new Blob([JSON.stringify(jsonData)], {type: "application/json"}));

        //이미지
        let imageFile = document.getElementById("chefImg").file[0];
        console.log(imageFile);
        if(imageFile) {
            formData.append("cImg", imageFile,imageFile.name);
        }

        //Ajax 통하여 데이터 전송
        sendData(formData);
    });

    async function sendData(data) {
        try {
            var myHeaders = new Headers();
            myHeaders.append("Authorization", "Bearer" + token);
            var requestOptions = {
                method: 'PUT',
                headers: myHeaders,
                body: data,
            };

            const response = await fetch("http://localhost:8080/api/user/my/update/details", requestOptions);

            if(response.ok) {
                alert("데이터 전송 완료!");
            } else {
                const errorData = await response.json();
                alert("데이터 전송 실패: " + JSON.stringify(errorData));
            }
        } catch (error) {
            alert("오류 발생: " + error.toString());
        }
    }
}