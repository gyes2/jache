// const likeButtons = document.querySelectorAll('.like');
// const likeCount = document.querySelector('.likeCount');
// const likeBtn = document.querySelector('.heart');
// const likedBtn = document.querySelector('.heart-liked');

// likeButtons.forEach(function (button) {
//     button.addEventListener('click', function () {
//         if (data.like !== 0) {
//             likeCount.textContent = Number(likeCount.textContent) + 1;
//             likeBtn.hidden = true;
//             likedBtn.hidden = false;
//         } else {
//             likeCount.textContent = Number(likeCount.textContent) - 1;
//             likeBtn.hidden = false;
//             likedBtn.hidden = true;
//         }
//     });
// });

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