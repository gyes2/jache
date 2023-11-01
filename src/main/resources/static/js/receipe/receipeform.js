const token = localStorage.getItem('token');
console.log(token);

let materialreturn = 0;
let orderreturn = 0;
const urlParams = new URLSearchParams(window.location.search);
const receipeId = urlParams.get('receipeId');

// 수정된 코드
const parts = window.location.pathname.split('/'); // 현재 페이지 URL을 슬래시('/')로 분할하여 배열로 만듭니다.
const lastPart = parts[parts.length - 1]; // 배열의 마지막 요소를 가져옵니다.

console.log('receipeId:', receipeId);
console.log('lastPart:', lastPart);

const receipeCheckNum = lastPart;
document.addEventListener("DOMContentLoaded", function() {
    let includes = document.querySelectorAll("[data-include]");
    let includeCount = includes.length;

    initializeReceipeForm();
    if (token) {
        let loginChefName = document.getElementById("main-aside-login-username");
        let loginChefImg = document.getElementById("main-aside-login-img-id");
        let loginChefText = document.getElementById("main-aside-login-overview");

        toggleItems();

        fetch('http://localhost:8080/api/user/getUserInfo', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                // 유효하지 않은 토큰에 대한 서버 응답을 확인
                if (!response.ok) {
                    throw new Error('Invalid Token');
                }
                return response.json();
            })
            .then(data => {
                // 데이터에서 값을 추출합니다.
                let chefName = data.data.chefName;
                let chefDetial = data.data.chefDetial;
                let chefImgUrl = data.data.chefImgUrl;

                // 값들을 DOM 요소에 할당합니다.
                loginChefName.textContent = chefName;
                loginChefText.textContent = chefDetial;
                loginChefImg.src = chefImgUrl;
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    } else{
        toggleItems();
    }

    Array.prototype.forEach.call(includes, function(element) {
        let path = element.getAttribute("data-include");
        fetch(path)
            .then((response) => response.text())
            .then((data) => {
                element.innerHTML = data;
                includeCount--;

                    // `initializeOtherFeatures` 함수가 정의되지 않았으므로 삭제했습니다.

            });
    });



});

function toggleItems() {
    let mainAsideLogin = document.getElementById('main-aside-login-html');
    let mainAside = document.getElementById('main-aside-html');

    if (token) { // 로그인 되어 있을 때
        if (mainAsideLogin && mainAside) {
            mainAsideLogin.classList.remove('hidden'); // 로그인 창 숨기기
            mainAside.classList.add('hidden'); // 로그아웃 창 보여주기
        }
    } else { // 로그인 되어 있지 않을 때
        containers.forEach((container, index) => {
            if (index >= 4) { // 첫 3개 요소 이후부터는
                container.classList.add('hidden'); // 숨기기
            }
        });

        if (mainAsideLogin && mainAside) {
            mainAsideLogin.classList.add('hidden'); // 로그인 창 보여주기
            mainAside.classList.remove('hidden'); // 로그아웃 창 숨기기
        }
    }
}

function initializeReceipeForm() {
    let addButton = document.getElementById("receipe-form-material-button");
    let originalItem = document.querySelector(
        ".receipe-form-material"
    ).parentElement;
    let originalInput = document.querySelector(".receipe-form-material");
    let originalUnitInput = document.querySelector(".receipe-form-material-unit");

    if (addButton) {
        addButton.addEventListener("click", async function (e) {
            e.preventDefault();
            let currentItems = document.querySelectorAll(".receipe-form-material");

            if (currentItems.length < 9) {
                let inputValue = originalInput.value;
                let unitValue = originalUnitInput.value;

                let materialJson = {
                    receipeId: receipeCheckNum,
                    ingredientName: originalInput.value,
                    weight: originalUnitInput.value
                }

                let responseData = await materialSendData(materialJson);
                console.log(responseData + "5");

                let inputDiv = document.createElement("div");
                inputDiv.className = "form-container-items";

                let label = document.createElement("label");
                label.setAttribute("for", "");
                label.id = "receipe-form-material-label-" + (currentItems.length + 1);
                label.textContent = "재료 " + (currentItems.length + 1);

                let input = document.createElement("input");
                input.type = "text";
                input.className = "receipe-form-material";
                input.placeholder = "재료명";
                input.id = "receipe-form-material-" + (currentItems.length + 1);
                let attrName = "materialDataValue" + (currentItems.length + 1);
                input.dataset[attrName] = responseData;  // 응답 값을 dataset에 저장
                input.value = inputValue;

                let inputUnit = document.createElement("input");
                inputUnit.type = "text";
                inputUnit.className = "receipe-form-material-unit";
                inputUnit.placeholder = "단위";
                inputUnit.id = "receipe-form-material-unit-" + (currentItems.length + 1);
                inputUnit.value = unitValue;

                let deleteButton = document.createElement("button");
                deleteButton.textContent = "삭제";
                deleteButton.addEventListener("click", function (ev) {
                    ev.preventDefault();

                    let attrName = "materialDataValue" + (currentItems.length + 1);
                    let dataValue = input.dataset[attrName];
                    console.log("Deleting data value:", dataValue);
                    materialDeleteData(dataValue)

                    inputDiv.remove();
                    renumberItems();
                });

                inputDiv.appendChild(label);
                inputDiv.appendChild(input);
                inputDiv.appendChild(inputUnit);
                inputDiv.appendChild(deleteButton);

                if (currentItems.length === 0) {
                    originalItem.insertAdjacentElement("afterend", inputDiv);
                } else {
                    currentItems[
                    currentItems.length - 1
                        ].parentElement.insertAdjacentElement("afterend", inputDiv);
                }

                // 재료 데이터 함수


                originalInput.value = "";
                originalUnitInput.value = "";
                renumberItems();
            }
        });
    }

    function renumberItems() {
        let items = document.querySelectorAll(".receipe-form-material");
        for (let i = 0; i < items.length; i++) {
            let parent = items[i].parentElement;
            let label = parent.querySelector("label");
            label.textContent = "재료 " + (i + 1);
        }
    }

    let receipeSequenceButton = document.getElementById(
        "receipe-form-sequence-add-button"
    );

    if (receipeSequenceButton) {
        receipeSequenceButton.addEventListener("click", function(e) {
            e.preventDefault();
            let receipeModal = document.querySelector(".sequence-modal");
            receipeModal.style.display = "block";
        });
    }

    function renumberRecipeSequences() {
        let sequences = document.querySelectorAll(".receipe-form-sequence");
        for (let i = 0; i < sequences.length; i++) {
            let parent = sequences[i].parentElement;
            let label = parent.querySelector("label");
            label.textContent = "요리 순서 " + (i + 1);
        }
    }

    document
        .getElementById("sequence-modal-add")
        .addEventListener("click", async function(e) {
            e.preventDefault();

            let formdata = new FormData();

            // JSON 데이터 첨부
            let jsonData = {
                receipeId: receipeCheckNum,
                content: document.getElementById("sequence-modal-content").value,
            };

            formdata.append("ordersReqDto", new Blob([JSON.stringify(jsonData)],{type: "application/json"}));

            // 이미지 첨부
            let imageFile = document.getElementById("sequence-modal-image").files[0];
            console.log(imageFile);

            if(imageFile){
                formdata.append("ordersImg", imageFile,imageFile.name);
            }

            // Ajax를 통해 데이터 전송
            let sequenceResponseData = await sequenceSendData(formdata);

            // 요리 순서와 이미지 항목 추가
            addRecipeSequence(sequenceResponseData);


            // 모달을 닫습니다.
            let receipeModal = document.querySelector(".sequence-modal");
            receipeModal.style.display = "none";
        });

    function addRecipeSequence(responsedata) {
        let currentSequences = document.querySelectorAll(".receipe-form-sequence");

        let sequenceDiv = document.createElement("div");
        sequenceDiv.className = "form-container-items";

        let label = document.createElement("label");
        label.textContent = "요리 순서 " + (currentSequences.length + 1);

        let textarea = document.createElement("textarea");
        textarea.className = "receipe-form-sequence";
        textarea.rows = "10";
        textarea.cols = "10";
        textarea.placeholder = "요리 순서를 입력하세요...";
        let attrName = "textareaDataValue" + (currentSequences.length + 1);
        textarea.dataset[attrName] = responsedata;
        textarea.value = document.getElementById("sequence-modal-content").value;

        let imageInput = document.createElement("input");
        imageInput.type = "hidden";
        imageInput.className = "receipe-form-sequence-img-input";

        let img = document.createElement("img");
        img.className = "receipe-form-sequence-img";
        img.src = "";

        let deleteButton = document.createElement("button");
        deleteButton.textContent = "삭제";
        deleteButton.addEventListener("click", async function (ev) {
            ev.preventDefault();

            let attrName = "textareaDataValue" + (currentSequences.length + 1);
            let dataValue = textarea.dataset[attrName];
            console.log("Deleting data value:", dataValue);
            await sequenceDeleteSendData(dataValue);

            sequenceDiv.remove();
            renumberRecipeSequences();
        });

        sequenceDiv.appendChild(label);
        sequenceDiv.appendChild(textarea);
        sequenceDiv.appendChild(imageInput);
        sequenceDiv.appendChild(img);
        sequenceDiv.appendChild(deleteButton);

        let form = document.getElementById("receipe-form");
        let sequenceContainer = document.querySelector(
            ".receipe-form-sequence"
        ).parentElement; // 요리 순서의 부모 div 요소를 참조
        form.insertBefore(sequenceDiv, sequenceContainer.nextElementSibling); // 요리 순서 바로 다음 요소에 삽입

        renumberRecipeSequences();
        // 이미지 파일을 처리하고 이미지 태그에 표시합니다.
        let fileInput = document.getElementById("sequence-modal-image");
        if (fileInput.files && fileInput.files[0]) {
            let reader = new FileReader();
            reader.onload = function(e) {
                img.src = e.target.result;
                imageInput.value = e.target.result; // base64로 인코딩된 이미지 데이터를 input 태그의 value 속성에 저장
            };
            reader.readAsDataURL(fileInput.files[0]);
        }

        // 모달창의 내용을 초기화 합니다.
        document.getElementById("sequence-modal-content").value = "";
        document.getElementById("sequence-modal-image").value = "";
    }

    // 요리 순서 추가 버튼 클릭 이벤트
    document
        .getElementById("receipe-form-sequence-add-button")
        .addEventListener("click", function(e) {
            e.preventDefault();

            // 모달창을 보여줍니다.
            let sequenceModal = document.getElementById("sequence-modal");
            sequenceModal.style.display = "block";
        });

    // 모달창 닫기 버튼 클릭 이벤트
    document
        .getElementById("sequence-modal-close")
        .addEventListener("click", function(e) {
            e.preventDefault();
            let sequenceModal = document.getElementById("sequence-modal");
            sequenceModal.style.display = "none";
        });

    // 대표 이미지 보여주기
    document.getElementById("receipe-form-media-id").addEventListener("change", function() {
        if (this.files && this.files[0]) {
            let reader = new FileReader();

            reader.onload = function(e) {
                document.querySelector(".receipe-form-media-img").src = e.target.result;
            }

            reader.readAsDataURL(this.files[0]);
        }
    });

    document.getElementById("submit-button").addEventListener("click", function(e) {
        e.preventDefault();

        let formdata = new FormData();

        // JSON 데이터 첨부
        let jsonData = {
            receipeId: receipeCheckNum,
            theme: document.getElementById("receipe-form-option-select").value,
            title: document.getElementById("receipe-form-title-id").value,
            introduce: document.getElementById("receipe-form-overview-id").value
        };

        formdata.append("receipe", new Blob([JSON.stringify(jsonData)],{type: "application/json"}));

        // 이미지 첨부
        let imageFile = document.getElementById("receipe-form-media-id").files[0];
        console.log(imageFile);

        if(imageFile){
            formdata.append("receipeImg", imageFile,imageFile.name);
        }


        // Ajax를 통해 데이터 전송
        sendData(formdata);
    });

    // 재료 추가 전송
    async function materialSendData(data) {
        try {
            const response = await fetch('http://localhost:8080/api/user/ingredient/add', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            });

            const responseData = await response.json();

            if (responseData.success) {
                return responseData.data.ingredientId;
            } else {
                alert('재료 추가 실패: ' + responseData.message);
                return null;
            }
        } catch (error) {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
            return null;
        }
    }

    // 재료 삭제 전송
    async function materialDeleteData(dataValue) {
        try {
            const response = await fetch(`http://localhost:8080/api/user/ingredient/delete/${dataValue}`, {
                method: 'DELETE',  // 여기에서 메서드를 DELETE로 설정
                headers: {
                    'Authorization': 'Bearer ' + token,  // 필요하다면 토큰을 포함
                    'Content-Type': 'application/json'
                },
            });

            const data = await response.json();
            console.log(data);

            if (data.isSuccess) {
            } else {
                alert('재료 삭제 실패');
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }


    // 요리 순서 전송

    async function sequenceSendData(data) {
        try {
            var myHeaders = new Headers();
            myHeaders.append("Authorization", "Bearer "+ token);
            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: data,
            };

            const response = await fetch("http://localhost:8080/api/user/orders/add", requestOptions);

            if (response.ok) {
                const responseData = await response.json(); // JSON 변환

                console.log(responseData.data.orderId); // orderId 출력
                return responseData.data.orderId; // orderId 리턴
            } else {
                const errorData = await response.json();
                alert("데이터 전송 실패: " + JSON.stringify(errorData));
            }
        } catch (error) {
            alert("오류 발생: " + error.toString());
        }
    }


    // 요리 순서 삭제
    async function sequenceDeleteSendData(dataValue) {
        try {
            const response = await fetch(`http://localhost:8080/api/user/orders/delete/${dataValue}`, {
                method: 'DELETE',  // 여기에서 메서드를 DELETE로 설정
                headers: {
                    'Authorization': 'Bearer ' + token,  // 필요하다면 토큰을 포함
                    'Content-Type': 'application/json'
                },
            });

            const data = await response.json();
            console.log(data);

            if (data.isSuccess) {
            } else {
                alert('요리 순서 삭제 실패');
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }


    // 최종 전송
    async function sendData(data) {
        try {
            var myHeaders = new Headers();
            myHeaders.append("Authorization", "Bearer "+ token);
            var requestOptions = {
                method: 'PUT',
                headers: myHeaders,
                body: data,
            };

            const response = await fetch("http://localhost:8080/api/user/receipe/create", requestOptions);

            if (response.ok) {
                window.location.href = '/main';
            } else {
                const errorData = await response.json();
                alert("데이터 전송 실패: " + JSON.stringify(errorData));
            }
        } catch (error) {
            alert("오류 발생: " + error.toString());
        }
    }

}

