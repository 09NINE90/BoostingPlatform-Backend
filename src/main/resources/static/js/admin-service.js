const token = $("meta[name='_csrf']").attr("content");
let currentPage = 1;
const pageSize = 10;
const servicesContainer = document.querySelector('.services');
const paginationContainer = document.querySelector(".pagination");
const pageName = document.querySelector('#page-name');
const filtersContent = document.querySelector('.filters');

document.addEventListener('DOMContentLoaded', () => {
    const searchBtn = document.getElementById('search-button');
    const sidebarItems = document.querySelectorAll("#sidebar-ul li a");

    loadContent('services', '/orders/getAllOrders', currentPage);
    setActiveSidebarItem("#sidebar-ul li:first-child");

    sidebarItems.forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const section = item.textContent.toLowerCase().replace(/\s/g, "-");
            let url;

            // Определение URL для разных разделов
            switch (section) {
                case 'games':
                    url = '/games/getAllGames';
                    break;
                case 'services':
                    url = '/orders/getAllOrders';
                    break;
                // case 'appeals':
                //     url = '/appeals/getAllAppeals';
                //     break;
                default:
                    console.error(`No URL defined for section: ${section}`);
                    return;
            }

            // Установка активного элемента
            document.querySelectorAll('#sidebar-ul li').forEach(li => li.classList.remove('active'));
            item.parentElement.classList.add('active');

            // Загрузка контента
            loadContent(section, url, currentPage);
        });
    });


});

function setActiveSidebarItem(selector) {
    document.querySelector(selector).classList.add('active');
}

function loadContent(section, url, pageNumber) {
    const mainContent = document.querySelector('.services');
    mainContent.innerHTML = `<p>Loading ${section}...</p>`; // Показать индикатор загрузки
    pageName.textContent = section.toUpperCase();
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify({ pageNumber, pageSize }), // Пример для постраничной загрузки
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            return response.json(); // Парсим JSON
        })
        .then(data => {
            mainContent.innerHTML = ''; // Очищаем основной контейнер
            if (section === 'services') {
                renderServices(data, section, url);
            } else if (section === 'games') {
                renderGames(data, section, url);
            }
        })
        .catch(error => {
            console.error(`Error loading ${section}:`, error);
            mainContent.innerHTML = `<p>Error loading ${section}</p>`;
        });
}

function renderGames(data, section, url) {
    servicesContainer.innerHTML = '';
    filtersContent.innerHTML = '';

    const addGameBtn = document.createElement('button');
    const textAddGameBtn = document.createElement('span');

    textAddGameBtn.textContent = '+';
    addGameBtn.appendChild(textAddGameBtn);
    addGameBtn.id ='add-game-btn';
    addGameBtn.classList.add('filter-button')

    filtersContent.appendChild(addGameBtn);
    data.games.forEach(game => {

        const serviceCard = document.createElement('div');
        serviceCard.classList.add('service-card');

        const serviceInfo = document.createElement('div');
        serviceInfo.classList.add('service-info');

        const title = document.createElement('h3');

        const maxLength = 30;
        let text = game.title;
        if (text.length > maxLength) {
            text = text.substring(0, maxLength) + '...';
        }
        title.textContent = text;

        const description = document.createElement('p');
        text = game.description;
        if (text.length > maxLength) {
            text = text.substring(0, maxLength) + '...';
        }
        description.textContent = text;


        serviceInfo.appendChild(title);
        serviceInfo.appendChild(description);

        // Собираем карточку
        serviceCard.appendChild(serviceInfo);
        // Добавляем карточку в контейнер
        servicesContainer.appendChild(serviceCard);

    });
    renderPagination(data.pageNumber, data.pageTotal, section, url);
}

function renderServices(data, section, url) {
    servicesContainer.innerHTML = '';
    filtersContent.innerHTML = '';
    const addOrderBtn = document.createElement('button');
    const textAddGameBtn = document.createElement('span');

    textAddGameBtn.textContent = '+';
    addOrderBtn.appendChild(textAddGameBtn);
    addOrderBtn.id ='add-order-btn'
    addOrderBtn.classList.add('filter-button')

    addOrderBtn.addEventListener('click', () => {
        openAddOrderModal();
    });

    filtersContent.appendChild(addOrderBtn);
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
    renderPagination(data.pageNumber, data.pageTotal, section, url);
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
    const categoryContainer = document.getElementById('category-select-container');
    const addBtn = document.getElementById("add-new-order-service-button");

    gameSelect.innerHTML = '<option value="" disabled selected>Select a game</option>';
    categoryContainer.innerHTML = '';

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


    gameSelect.addEventListener('change', () => {
        const selectedGameId = gameSelect.value;

        // Очищаем контейнер категорий
        categoryContainer.innerHTML = '';

        fetch(`/games/${selectedGameId}`)
            .then(response => response.json())
            .then(game => {
                if (game.categories && game.categories.length > 0) {
                    createCategorySelect(game.categories, categoryContainer);
                    console.log(JSON.stringify(game))
                }
            })
            .catch(error => console.error('Error loading categories:', error));
    });

    modal.classList.remove("hidden");
    modal.style.display = "flex";

    addBtn.addEventListener('click', () => {
        const selectedOption = gameSelect.selectedOptions[0];
        const selectedCategories = Array.from(categoryContainer.querySelectorAll('select'))
            .map(select => select.selectedOptions[0]?.text || '') // Извлекаем текст выбранной опции
            .filter(name => name) // Убираем пустые строки
            .join(',');

        console.log(selectedCategories)
        const newOrder = {
            title: titleInput.value,
            description: descriptionTextarea.value,
            basePrice: priceInput.value,
            categories: selectedCategories,
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
    })
}

function createCategorySelect(categories, container) {
    const select = document.createElement('select');
    select.innerHTML = '<option value="" disabled selected>Select a category</option>';

    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.name; // Используем имя категории как value
        option.textContent = category.name;
        select.appendChild(option);
    });

    // Обрабатываем выбор в текущем селекте
    select.addEventListener('change', () => {
        const selectedCategory = categories.find(cat => cat.name === select.value);

        // Удаляем все следующие селекты
        while (select.nextSibling) {
            select.nextSibling.remove();
        }

        // Если у выбранной категории есть подкатегории, создаем новый `<select>`
        if (selectedCategory && selectedCategory.subcategories && selectedCategory.subcategories.length > 0) {
            createCategorySelect(selectedCategory.subcategories, container);
        }
    });

    container.appendChild(select);
}
