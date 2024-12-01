const signUpBtn = document.querySelector('#signup-btn');
const passwordInput = document.querySelector('#password');
const confirmPasswordInput = document.querySelector('#confirm-password');
const token = $("meta[name='_csrf']").attr("content");

passwordInput.addEventListener("input", checkPassword);
confirmPasswordInput.addEventListener("input", checkPassword);

signUpBtn.addEventListener('click', ()=> {
    const nickname = document.querySelector('#nickname');
    const email = document.querySelector('#email');
    const password = document.querySelector('#password');
    if (
        nickname.value &&
        email.value &&
        passwordInput.value
    ){
        const requestData = {
            nickname: nickname.value,
            username: email.value,
            password: passwordInput.value
        };
        fetch('/user/createUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => {
                if (response.ok){
                    window.location.href = '/user/mainPage';
                }
            })
            .catch((error) => {
                console.error('Ошибка при регистрации:', error);
            });
    }
})

function checkPassword() {
    const passwordMatch = passwordInput.value === confirmPasswordInput.value;
    const passwordNotEmpty = passwordInput.value.trim() !== '';
    if (passwordNotEmpty) {
        if (passwordMatch) {
            updateInputStyle(passwordInput, "valid");
            updateInputStyle(confirmPasswordInput, "valid");
            signUpBtn.disabled = false;
        } else {
            updateInputStyle(passwordInput, "invalid");
            updateInputStyle(confirmPasswordInput, "invalid");
            signUpBtn.disabled = true;
        }
    } else {
        updateInputStyle(passwordInput, "neutral");
        updateInputStyle(confirmPasswordInput, "neutral");
        signUpBtn.disabled = true;
    }
}
function updateInputStyle(input, status) {
    input.classList.remove("valid", "invalid", "neutral");
    input.classList.add(status);
}