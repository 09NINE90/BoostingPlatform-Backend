const token = $("meta[name='_csrf']").attr("content");
let currentPage = 1;
const pageSize = 10;
const servicesContainer = document.querySelector('.services');
const paginationContainer = document.querySelector(".pagination");


document.addEventListener('DOMContentLoaded', () => {
    const addOrderBtn = document.getElementById('add-order-btn');
    const searchBtn = document.getElementById('search-button');
    const searchInput = document.getElementById('search-input');

    addOrderBtn.addEventListener('click', openAddOrderModal);
    searchBtn.addEventListener('click', () => searchOrder(searchInput));

    addPageNumber(currentPage)

});

function searchOrder (searchInput){
    const request = searchInput.value;
    console.log(request);
}

function addPageNumber(pageNumber) {
    const game = {
        title: 'Колда',
    };
    fetch('/orders/getAllOrders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify({game, pageNumber, pageSize }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            return response.json(); // Парсим JSON
        })
        .then(data => {
            servicesContainer.innerHTML = '';
            data.baseOrder.forEach(order => {
                // Создаем карточку
                const serviceCard = document.createElement('div');
                serviceCard.classList.add('service-card');

                // Создаем изображение
                // const img = document.createElement('img');
                // img.src = order.image || 'images/default.jpg'; // Укажите путь по умолчанию
                // img.alt = order.title || 'Service Image';

                // Создаем блок информации
                const serviceInfo = document.createElement('div');
                serviceInfo.classList.add('service-info');

                const secondId = document.createElement('p');
                secondId.textContent = order.secondId;

                const title = document.createElement('h3');
                const maxLength = 30;
                let text = order.title;
                if (text.length > maxLength) {
                    text = text.substring(0, maxLength) + '...';
                }
                title.textContent = text;

                const description = document.createElement('p');
                text = order.description;
                if (text.length > maxLength) {
                    text = text.substring(0, maxLength) + '...';
                }
                description.textContent = text;

                const price = document.createElement('span');
                price.classList.add('price');
                price.textContent = `${order.basePrice.toFixed(2)}$`;

                // Добавляем элементы в блок информации
                serviceInfo.appendChild(secondId);
                serviceInfo.appendChild(title);
                serviceInfo.appendChild(description);
                serviceInfo.appendChild(price);

                // Создаем блок действий
                const serviceActions = document.createElement('div');
                serviceActions.classList.add('service-actions');

                const editButton = document.createElement('button');
                editButton.classList.add('edit-button', 'settings-button');
                editButton.textContent = '⚙️';

                // Сохраняем данные из карточки в атрибутах кнопки
                editButton.dataset.orderTitle = order.title;
                editButton.dataset.orderDescription = order.description;
                editButton.dataset.orderPrice = order.basePrice;

                const deleteButton = document.createElement('button');
                deleteButton.classList.add('delete-button');
                deleteButton.textContent = '🗑️';

                // Добавляем кнопки в блок действий
                serviceActions.appendChild(editButton);
                serviceActions.appendChild(deleteButton);

                // Собираем карточку
                serviceCard.appendChild(serviceInfo);
                serviceCard.appendChild(serviceActions);
                // Добавляем карточку в контейнер
                servicesContainer.appendChild(serviceCard);

                // Обработчик клика на кнопку редактирования
                editButton.addEventListener('click', () => {
                    openEditModal(order);
                });
                deleteButton.addEventListener('click', () => {
                    deleteOrder(order)
                });
            });
            renderPagination(data.pageNumber, data.pageTotal);
        })
        .catch(error => {
            console.error("Error fetching services:", error);
        });
}

function deleteOrder(order){
    fetch(`/orders/deleteBaseOrder`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify(order.id),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            location.reload()
            return response.json(); // Парсим JSON
        })
}

function openEditModal(order) {
    const modal = document.getElementById("modal");
    const titleInput = modal.querySelector("input[type='text']");
    const descriptionTextarea = modal.querySelector("textarea");
    const priceInput = modal.querySelector("input[type='number']");
    const saveBtn = modal.querySelector(".save-button");
    const modalTitle = modal.querySelector('h2');
    // Заполняем поля модального окна данными
    titleInput.value = order.title;
    descriptionTextarea.value = order.description;
    priceInput.value = order.basePrice;
    modalTitle.textContent = 'Edit Order Card #' + order.secondId;

    // Показываем модальное окно
    modal.classList.remove("hidden");
    modal.style.display = "flex";

    saveBtn.addEventListener('click',() => {
        order.title = titleInput.value
        order.description = descriptionTextarea.value
        order.basePrice = priceInput.value
        fetch(`/orders/saveEditingBaseOrder`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify(order),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                location.reload()
                return response.json();
            })
    })
}

function openAddOrderModal(){
    const modal = document.getElementById("add-card-modal");
    const titleInput = modal.querySelector("input[type='text']");
    const descriptionTextarea = modal.querySelector("textarea");
    const priceInput = modal.querySelector("input[type='number']");
    const gameSelect = document.getElementById('new-service-game');
    const addBtn = document.getElementById("add-new-order-service-button");

    fetch('/games/getAllGames')
        .then(response => response.json())
        .then(games => {
            games.forEach(game => {
                const option = document.createElement('option');
                option.value = game.id; // или другое уникальное значение
                option.text = game.title; // или другое отображаемое поле
                gameSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading games:', error));


    modal.classList.remove("hidden");
    modal.style.display = "flex";

    addBtn.addEventListener('click', () => {
        const selectedOption = gameSelect.selectedOptions[0];
        const newOrder = {
            title: titleInput.value,
            description: descriptionTextarea.value,
            basePrice: priceInput.value,
            game: {
                id: selectedOption.value,
                title: selectedOption.text
            }
        }

        fetch(`/orders/addNewOrder`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify(newOrder),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                location.reload()
                return response.json();
            })

        console.log(newOrder)
    })
}