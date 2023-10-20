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

var settings = {
    "url": "http://localhost:8080/api/user/getUserInfo",
    "method": "POST",
    "timeout": 0,
};

$.ajax(settings).done(function (response) {
    console.log(response);
});
