const passwordInput = document.getElementById("reg-password");
const confirmPasswordInput = document.getElementById("reg-confirmPassword");
const phoneInput = document.getElementById("reg-phone");
const usernameInput = document.getElementById("reg-userName");
const emailInput = document.getElementById("reg-email");
const authInput = document.getElementById("reg-authNumber");

/* 정규 표현식 입력값 유효 체크 */
document.addEventListener("DOMContentLoaded", function () {
    const passwordError = document.getElementById("password-error");
    const confirmPasswordError = document.getElementById("confirm-password-error");
    const phoneError = document.getElementById("phone-error");
    const usernameError = document.getElementById("userName-error");
    const emailError = document.getElementById("email-error")
    const authError = document.getElementById("authNumber-error")

    function showErrorMessage(element, message) {
        element.textContent = message;
        element.style.display = "block";
    }

    function clearErrorMessage(element) {
        element.textContent = "";
        element.style.display = "none";
    }

    function validatePassword() {
        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,20}$/;
        if (!passwordInput.value) {
            showErrorMessage(passwordError, "비밀번호를 입력해주세요.");
        } else if (!passwordRegex.test(passwordInput.value)) {
            showErrorMessage(passwordError, "유효한 비밀번호를 입력해주세요.");
        } else {
            clearErrorMessage(passwordError);
        }
    }

    function validateConfirmPassword() {
        if (!confirmPasswordInput.value) {
            showErrorMessage(confirmPasswordError, "비밀번호 확인을 입력해주세요.");
        } else if (passwordInput.value !== confirmPasswordInput.value) {
            showErrorMessage(confirmPasswordError, "비밀번호가 일치하지 않습니다.");
        } else {
            clearErrorMessage(confirmPasswordError);
        }
    }

    function validatePhone() {
        const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
        if (!phoneInput.value) {
            showErrorMessage(phoneError, "전화번호를 입력해주세요.");
        } else if (!phoneRegex.test(phoneInput.value)) {
            showErrorMessage(phoneError, "유효한 전화번호를 입력해주세요. (010-1111-1111)");
        } else {
            clearErrorMessage(phoneError);
        }
    }

    function validateUsername() {
        const usernameRegex = /^[a-zA-Z0-9]{6,20}$/;
        if (!usernameInput.value) {
            showErrorMessage(usernameError, "아이디를 입력해주세요.");
        } else if (!usernameRegex.test(usernameInput.value)) {
            showErrorMessage(usernameError, "6-20자의 영문 대소문자와 숫자만 입력 가능합니다.");
        } else {
            clearErrorMessage(usernameError);
        }
    }

    function validateEmail() {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailInput.value) {
            showErrorMessage(emailError, "이메일을 입력해주세요.");
        } else if (!emailRegex.test(emailInput.value)) {
            showErrorMessage(emailError, "유효한 이메일을 입력해주세요. (jache@jache)");
        } else {
            clearErrorMessage(emailError);
        }
    }

    function validateAuth() {
        const authRegex= /^\d{4}$/;
        if (!authInput.value) {
            showErrorMessage(authError, "인증번호를 입력해주세요.");
        } else if (!authRegex.test(authInput.value)) {
            showErrorMessage(authError, "유효한 인증번호를 입력해주세요.");
        } else {
            clearErrorMessage(authError);
        }
    }

    passwordInput.addEventListener("input", validatePassword);
    confirmPasswordInput.addEventListener("input", validateConfirmPassword);
    phoneInput.addEventListener("input", validatePhone);
    usernameInput.addEventListener("input", validateUsername);
    emailInput.addEventListener("input", validateEmail);
    authInput.addEventListener("input", validateAuth);
});