function back() { //이전 페이지로 돌아가기
    var referrer = document.referrer;
    window.location.href = referrer;
    console.log(referrer);
}