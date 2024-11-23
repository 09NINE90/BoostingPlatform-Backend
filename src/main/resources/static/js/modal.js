const modal = document.getElementById("modal");
const closeButton = modal.querySelector(".close-button");

// Закрытие модального окна
closeButton.addEventListener("click", () => {
    modal.classList.add("hidden");
    modal.style.display = "none"; // Скрыть модальное окно
});

// Закрытие при клике вне окна
window.addEventListener("click", (event) => {
    if (event.target === modal) {
        modal.classList.add("hidden");
        modal.style.display = "none"; // Скрыть модальное окно
    }
});
