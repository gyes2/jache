document.addEventListener("DOMContentLoaded", function() {
    // header를 포함한 모든 요소에 대해 반복합니다.
    const elementsToInclude = document.querySelectorAll('[data-include]');
    elementsToInclude.forEach(element => {
        const fileName = element.getAttribute('data-include');
        
        // 파일을 가져와서 해당 요소에 삽입합니다.
        fetch(fileName)
            .then(response => response.text())
            .then(data => {
                element.innerHTML = data;
            });
    });
});