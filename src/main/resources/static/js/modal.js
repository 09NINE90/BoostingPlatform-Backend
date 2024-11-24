const editModal = document.getElementById("modal");
const addModal = document.getElementById("add-card-modal");

const closeEditButton = editModal.querySelector(".close-button");
const closeAddButton = addModal.querySelector(".close-button");


closeAddButton.addEventListener("click", () => {
    addModal.classList.add("hidden");
    addModal.style.display = "none";
})
// Закрытие модального окна
closeEditButton.addEventListener("click", () => {
    editModal.classList.add("hidden");
    editModal.style.display = "none"; // Скрыть модальное окно
});

// Закрытие при клике вне окна
window.addEventListener("click", (event) => {
    if (event.target === editModal || event.target === addModal) {
        editModal.classList.add("hidden");
        editModal.style.display = "none";
        addModal.classList.add("hidden");
        addModal.style.display = "none";
    }
});
