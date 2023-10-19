function chat() {
    location.href="../chat/chat.html";
}

// 글쓰기 버튼 클릭 시
document.querySelector('button[th:onclick="|location.href=\'@{/receipe/main-receipe-form}\'|"]').addEventListener('click', function () {
    // 원하는 receipeId를 설정
    const receipeId = 123; // 예시로 123으로 설정

    // 글쓰기 페이지로 이동 및 receipeId 전달
    location.href = '/receipe/main-receipe-form/' + receipeId;
});

//  "data": {
//     "chefName": "test2",
//     "receipeId": 1
//   },