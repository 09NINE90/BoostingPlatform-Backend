const gameListElement = document.getElementById("game-list");
const servicesContainer = document.querySelector(".services");
const paginationContainer = document.querySelector(".pagination");
const token = $("meta[name='_csrf']").attr("content");
let currentPage = 1; // Текущая страница (начинается с 1)
const pageSize = 20; // Размер страницы

document.addEventListener("DOMContentLoaded", () => {

    // Выполняем GET-запрос к эндпоинту Spring Boot
    fetch(`/games/getAllGames`)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            return response.json(); // Парсим JSON из ответа
        })
        .then((data) => {
            // Проходим по каждому элементу из списка игр
            data.forEach((game) => {
                // Создаем новый элемент <li>
                const listItem = document.createElement("li");

                // Создаем ссылку <a>
                const link = document.createElement("a");
                link.href = "#";
                link.textContent = game.title;

                // Добавляем класс "active" для первого элемента
                link.classList.add("active");


                // Вставляем ссылку внутрь <li>, а <li> внутрь <ul>
                listItem.appendChild(link);
                gameListElement.appendChild(listItem);
            });
        })
        .catch((error) => {
            console.error("Error fetching games:", error);
        });

    fetchOrders(currentPage)

    function fetchOrders(pageNumber) {
        fetch('/orders/getAllOrders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify({ pageNumber, pageSize }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                return response.json(); // Парсим JSON из ответа
            })
            .then((data) => {
                servicesContainer.innerHTML = '';
                data.baseOrder.forEach((order) => {
                    const serviceCard = document.createElement("div");
                    serviceCard.classList.add("service-card");

                    // Создаем элементы внутри карточки
                    const img = document.createElement("img");
                    img.src = "/images/slider.PNG";
                    img.alt = "Order Icon";

                    const details = document.createElement("div");
                    details.classList.add("service-details");

                    const title = document.createElement("h3");
                    title.textContent = order.title;

                    const description = document.createElement("p");
                    description.textContent = order.description;

                    const priceContainer = document.createElement("div");
                    priceContainer.classList.add("service-price");

                    const price = document.createElement("p");
                    price.textContent = `${order.basePrice} $`;

                    // Собираем карточку
                    details.appendChild(title);
                    details.appendChild(description);
                    priceContainer.appendChild(price);

                    serviceCard.appendChild(img);
                    serviceCard.appendChild(details);
                    serviceCard.appendChild(priceContainer);

                    // Добавляем карточку в контейнер
                    servicesContainer.appendChild(serviceCard);
                });

                renderPagination(data.pageNumber, data.pageTotal);
            })
            .catch(error => {
                console.error('Error fetching service data:', error);
            });
    }

    function renderPagination(currentPage, totalPages) {
        paginationContainer.innerHTML = ''; // Очищаем пагинацию

        // Кнопка "Prev"
        const prevLink = document.createElement('a');
        prevLink.href = '#';
        prevLink.classList.add('prev');
        prevLink.textContent = '« Prev';
        prevLink.classList.toggle('disabled', currentPage === 1); // Отключаем, если на первой странице
        prevLink.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage > 1) {
                currentPage--;
                fetchOrders(currentPage);
            }
        });
        paginationContainer.appendChild(prevLink);

        const maxPages = 4;
        let startPage = Math.max(1, currentPage - Math.floor(maxPages / 2));
        console.log(startPage)
        let endPage = Math.min(totalPages, startPage + maxPages + 1);
        console.log(endPage)


        // Номера страниц
        for (let i = startPage; i <= endPage; i++) {
            const pageLink = document.createElement('a');
            pageLink.href = '#';
            pageLink.textContent = i;
            pageLink.classList.toggle('active', currentPage === i); // Активируем текущую страницу
            pageLink.addEventListener('click', (e) => {
                e.preventDefault();
                if (currentPage !== i) {
                    currentPage = i;
                    fetchOrders(currentPage);
                }
            });
            paginationContainer.appendChild(pageLink);
        }

        // Кнопка "Next"
        const nextLink = document.createElement('a');
        nextLink.href = '#';
        nextLink.classList.add('next');
        nextLink.textContent = 'Next »';
        nextLink.classList.toggle('disabled', currentPage === totalPages); // Отключаем, если на последней странице
        nextLink.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage < totalPages) {
                currentPage++;
                fetchOrders(currentPage);
            }
        });
        paginationContainer.appendChild(nextLink);
    }
});
