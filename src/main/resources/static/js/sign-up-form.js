const signUpBtn = document.querySelector('#signup-btn');
const passwordInputS= document.querySelectorAll('#password');
const confirmPasswordInputS= document.querySelectorAll('#confirm-password');
const token = $("meta[name='_csrf']").attr("content");

for (const elem of confirmPasswordInputS) {
    elem.addEventListener("input", checkPassword);
    elem.addEventListener("focus", checkPassword);
    elem.addEventListener("blur", checkPassword);
}

for (const elem of passwordInputS) {
    elem.addEventListener("input", checkPassword);
    elem.addEventListener("focus", checkPassword);
    elem.addEventListener("blur", checkPassword);
}

signUpBtn.addEventListener('click', ()=>{
    const nickname = document.querySelector('#nickname')
    const email = document.querySelector('#email')
    const password = document.querySelector('#password')
    if (
        nickname.value &&
        email.value &&
        password.value
    ){
        const requestData = {
            nickname: nickname.value,
            username: email.value,
            password: password.value
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
                console.log(response);
                if (response.ok){
                    window.location.href = '/user/mainPage';
                }
            })
            .catch((error) => {
                console.error('Ошибка при регистрации:', error);
            });
    }
})

function checkPassword(){
    const passwordInput= document.querySelector('#password');
    const confirmPasswordInput= document.querySelector('#confirm-password');
    if (passwordInput.value !== ''){
        if (passwordInput.value !== confirmPasswordInput.value){
            signUpBtn.disabled = true;
            passwordInput.style.background = '#9a1400';
            confirmPasswordInput.style.background = '#9a1400';
        }else {
            signUpBtn.disabled = false;
            passwordInput.style.background = '#138f00';
            confirmPasswordInput.style.background = '#138f00';
        }
    }else {
        signUpBtn.disabled = true;
        passwordInput.style.background = '#c5c5c5';
        confirmPasswordInput.style.background = '#c5c5c5';
    }
}