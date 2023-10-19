function getUserInfo() {
    var form = document.querySelector('.chefInfo');
    var chefName = form.getAttribute('data-chef-name');
    var chefDetailElement = document.querySelector('[data-chef-detail]');
    var chefDetail = chefDetailElement.getAttribute('data-chef-detail');

    fetch('/api/user/getUserInfo', {
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 상태가 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            // API 응답 데이터 처리
            if (data.success) {
                // 응답이 성공인 경우
                var chefInfo = data.data;
                console.log('서버에서 받은 사용자 정보:', chefInfo);

                // chefName과 chefDetail을 필요한 대로 사용할 수 있습니다.
                console.log('Chef Name:', chefName);
                console.log('Chef Detail:', chefDetail);

                // 필요한 대로 chefName과 chefDetail을 사용할 수 있습니다.
            } else {
                console.error('API 요청 실패:', data.message);
            }
        })
        .catch(error => {
            console.error('API 호출 중 오류 발생:', error);
        });
}

// getUserInfo 함수 호출
getUserInfo();
