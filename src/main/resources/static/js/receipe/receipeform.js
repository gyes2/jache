const token = localStorage.getItem('token');
const receipeCheckNum = "1";
document.addEventListener("DOMContentLoaded", function() {
    let includes = document.querySelectorAll("[data-include]");
    let includeCount = includes.length;

    initializeReceipeForm();

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

function initializeReceipeForm() {
    let addButton = document.getElementById("receipe-form-material-button");
    let originalItem = document.querySelector(
        ".receipe-form-material"
    ).parentElement;
    let originalInput = document.querySelector(".receipe-form-material");
    let originalUnitInput = document.querySelector(".receipe-form-material-unit");

    if (addButton) {
        addButton.addEventListener("click", function(e) {
            e.preventDefault();
            let currentItems = document.querySelectorAll(".receipe-form-material");

            if (currentItems.length < 9) {
                let inputValue = originalInput.value;
                let unitValue = originalUnitInput.value;

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
                input.value = inputValue;

                let inputUnit = document.createElement("input");
                inputUnit.type = "text";
                inputUnit.className = "receipe-form-material-unit";
                inputUnit.placeholder = "단위";
                inputUnit.value = unitValue;

                let deleteButton = document.createElement("button");
                deleteButton.textContent = "삭제";
                deleteButton.addEventListener("click", function(ev) {
                    ev.preventDefault();
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
        .addEventListener("click", function(e) {
            e.preventDefault();

            // 요리 순서와 이미지 항목 추가
            addRecipeSequence();

            // 모달을 닫습니다.
            let receipeModal = document.querySelector(".sequence-modal");
            receipeModal.style.display = "none";
        });

    function addRecipeSequence() {
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
        textarea.value = document.getElementById("sequence-modal-content").value;

        let imageInput = document.createElement("input");
        imageInput.type = "hidden";
        imageInput.className = "receipe-form-sequence-img-input";

        let img = document.createElement("img");
        img.className = "receipe-form-sequence-img";
        img.src = "";

        let deleteButton = document.createElement("button");
        deleteButton.textContent = "삭제";
        deleteButton.addEventListener("click", function(ev) {
            ev.preventDefault();
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



    // 전송 버튼 클릭 이벤트 (Put 전송 이벤트)
    //     document.getElementById("submit-button").addEventListener("click", function(e) {
    //         e.preventDefault();
    //
    //         let jsonData = {
    //             receipeId: receipeCheckNum,
    //             category: document.getElementById("receipe-form-option-select").value,
    //             title: document.getElementById("receipe-form-title-id").value,
    //             introduce: document.getElementById("receipe-form-overview-id").value,
    //             receipeImg: document.getElementById("receipe-form-media-id").value
    //         };
    //
    //         // 재료와 단위 가져오기
    //         let materials = document.querySelectorAll(".receipe-form-material");
    //         let materialUnits = document.querySelectorAll(".receipe-form-material-unit");
    //         for (let i = 0; i < materials.length; i++) {
    //             jsonData.materials.push({
    //                 material: materials[i].value,
    //                 unit: materialUnits[i].value
    //             });
    //         }
    //
    //         // 요리 순서와 이미지 가져오기
    //         let sequences = document.querySelectorAll(".receipe-form-sequence");
    //         let sequenceImgs = document.querySelectorAll(".receipe-form-sequence-img");
    //         for (let i = 0; i < sequences.length; i++) {
    //             jsonData.sequences.push({
    //                 step: sequences[i].value,
    //                 image: sequenceImgs[i].value
    //             });
    //         }
    //
    //         // Ajax를 통해 JSON 데이터 전송
    //         sendData(jsonData);
    //     });


    document.getElementById("submit-button").addEventListener("click", async function(e) {
        e.preventDefault();

        let formdata = new FormData();

        // JSON 데이터 첨부
        let jsonData = {
            receipeId: receipeCheckNum,
            theme: document.getElementById("receipe-form-option-select").value,
            title: document.getElementById("receipe-form-title-id").value,
            introduce: document.getElementById("receipe-form-overview-id").value
        };
        formdata.append("receipe", JSON.stringify(jsonData));

        // 이미지 첨부
        let imageFile = document.getElementById("receipe-form-media-id").files[0];

        // 이미지를 Blob으로 변환
        const blob = await fileToBlob(imageFile);
        formdata.append("receipeImg", blob, imageFile.name);

        // Ajax를 통해 데이터 전송
        sendData(formdata);
    });

    async function fileToBlob(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = event => resolve(new Blob([event.target.result]));
            reader.onerror = error => reject(error);
            reader.readAsArrayBuffer(file);
        });
    }

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

