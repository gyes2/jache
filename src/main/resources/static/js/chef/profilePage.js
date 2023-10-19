function getUserInfo() {
    fetch('/api/user/getUserInfo', {
        method: 'GET',
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
                var chefInfo = data.data; // 예시로 받은 데이터
                console.log('서버에서 받은 사용자 정보:', chefInfo);

                // 원하는 동작을 여기에 추가
            } else {
                // 응답이 실패인 경우
                console.error('API 요청 실패:', data.message);
            }
        })
        .catch(error => {
            console.error('API 호출 중 오류 발생:', error);
        });
}

// getUserInfo 함수 호출
getUserInfo();