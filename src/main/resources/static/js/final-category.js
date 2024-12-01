let currentPage = 1; // Текущая страница (начинается с 1)
const pageSize = 20; // Размер страницы
const token = $("meta[name='_csrf']").attr("content");
const servicesContainer = document.querySelector('.services-container');


document.addEventListener("DOMContentLoaded", () => {
    const gameId = "b9ab10cc-ba00-45a0-b3c4-315c791dc8f1"; // Замените на реальный ID
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
        getOrders(category.name);
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

    getOrders('', game.title);

    const firstRow = document.createElement("div");
    firstRow.className = "category-row";
    container.appendChild(firstRow);

    // Добавляем кнопку "Show All"
    const showAllButton = document.createElement("button");
    showAllButton.className = "category-button show-all";
    showAllButton.textContent = "Show All";
    showAllButton.onclick = () => {
        getOrders('', game.title); // Загружаем все заказы
        while (firstRow.nextSibling) {
            firstRow.nextSibling.remove(); // Удаляем подкатегории, если они были
        }
    };

    firstRow.appendChild(showAllButton);

    // Добавляем категории
    game.categories.forEach((category) => {
        createCategoryButton(category, firstRow);
    });
}

function getOrders(categories = '', gameTitle = '') {
    const game = {
        title: gameTitle,
    };
    fetch('/orders/getAllOrders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify({game, categories, currentPage, pageSize }),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            createCards(data.baseOrder);
        });
}

function createCards(baseOrder) {
    servicesContainer.innerHTML = ''; // Очищаем контейнер перед добавлением карточек

    baseOrder.forEach(order => {
        // Создаем элементы для карточки
        const card = document.createElement('div');
        card.classList.add('card'); // Добавляем класс для стилей

        const title = document.createElement('h3');
        title.textContent = order.title;

        const description = document.createElement('p');
        description.textContent = order.description;

        const price = document.createElement('span');
        price.textContent = `${order.basePrice} $`;

        const gameTitle = document.createElement('p');
        gameTitle.textContent = `Game: ${order.game.title}`;

        // Добавляем элементы в карточку
        card.appendChild(title);
        card.appendChild(description);
        card.appendChild(price);
        card.appendChild(gameTitle);

        // Добавляем карточку в контейнер
        servicesContainer.appendChild(card);
    });
}