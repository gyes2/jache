function fn_checkByte(obj) {
    const maxByte = 20; // 최대 20바이트
    const text_val = obj.value; // 입력한 문자
    let totalByte = 0;

    for (let i = 0; i < text_val.length; i++) {
        const each_char = text_val.charAt(i);
        const uni_char = escape(each_char); // 유니코드 형식으로 변환
        if (uni_char.length > 4) {
            // 한글 : 2Byte
            totalByte += 2;
        } else {
            // 영문, 숫자, 특수문자 : 1Byte
            totalByte += 1;
        }

        // 만약 20바이트를 넘으면 입력 중단
        if (totalByte > maxByte) {
            obj.value = obj.value.slice(0, i);
            alert('최대 20Byte까지만 입력 가능합니다.');
            break;
        }
    }

    // 현재 입력된 바이트 수를 업데이트
    document.getElementById("nowByte").innerText = totalByte;

    if (totalByte > maxByte) {
        document.getElementById("nowByte").style.color = "red";
    } else {
        document.getElementById("nowByte").style.color = "green";
    }
}

// 프로필 이미지 삭제
document.querySelector('button[th:onclick="|location.href=\'@{/api/user/my/delete/img}\'"]').addEventListener('click', function () {
    // 이미지 삭제 API 호출

    var raw = "{\r\n    \"chefImgUrl\":\"https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/userInitial.jpg\"\r\n}";

    var requestOptions = {
        method: 'DELETE',
        body: raw,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/api/user/my/delete/img", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
});

    /*fetch('/api/user/my/delete/img', {
        method: 'DELETE',
        body: JSON.stringify({ /!* 삭제에 필요한 데이터 *!/ }),
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('이미지 삭제 실패');
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                // 삭제 성공한 경우
                // 원하는 동작을 수행
                console.log("삭제 성공");
            } else {
                console.error('이미지 삭제 실패:', data.message);
            }
        })
        .catch(error => {
            console.error('이미지 삭제 중 오류 발생:', error);
        });
});*/

// 사용자 정보 수정
document.querySelector('form.th:action="@{/api/user/my/update/details}"').addEventListener('submit', function (event) {
    event.preventDefault();
    var formdata = new FormData();
    formdata.append("myImg", fileInput.files[0], "file");

    var requestOptions = {
        method: 'PUT',
        body: formdata,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/api/user/my/update/img", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
});

