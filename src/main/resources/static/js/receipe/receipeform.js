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
}