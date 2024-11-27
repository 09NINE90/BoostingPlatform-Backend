document.addEventListener("DOMContentLoaded", () => {
    const gameId = "1449e918-24aa-42d2-aa52-c3944a47c7c4"; // Замените на реальный ID
    const endpoint = `/games/${gameId}`;

    fetch(endpoint)
        .then((response) => response.json())
        .then((game) => {
            console.log("Полученные данные:", JSON.stringify(game));
            initCategories(game);
        })
        .catch((error) => console.error("Ошибка загрузки данных:", error));
});

// Контейнер для категорий
const container = document.getElementById("categoryContainer");

// Функция для создания кнопок категорий
function createCategoryButton(category, parentRow) {
    const button = document.createElement("button");
    button.className = "category-button";
    button.textContent = category.name;

    button.onclick = () => {
        // Удаляем старые подкатегории
        while (parentRow.nextSibling) {
            parentRow.nextSibling.remove();
        }

        // Если есть подкатегории, создаем их
        if (category.subcategories && category.subcategories.length > 0) {
            createCategoryRow(category.subcategories, parentRow);
        }
    };

    parentRow.appendChild(button);
}

// Функция для создания строки категорий
function createCategoryRow(categories, parentRow) {
    const row = document.createElement("div");
    row.className = "category-row";
    parentRow.parentNode.insertBefore(row, parentRow.nextSibling);

    categories.forEach((category) => {
        createCategoryButton(category, row);
    });
}

// Инициализация категорий
function initCategories(game) {
    const serviceHeader = document.querySelector('.game-header');
    serviceHeader.querySelector('h1').textContent = `${game.title || "No Title"} Boosting Services`;
    serviceHeader.querySelector('p').textContent = game.description || "No description available.";

    const firstRow = document.createElement("div");
    firstRow.className = "category-row";
    container.appendChild(firstRow);

    game.categories.forEach((category) => {
        createCategoryButton(category, firstRow);
    });
}

