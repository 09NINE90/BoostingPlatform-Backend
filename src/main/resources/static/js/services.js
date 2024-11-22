const gameListElement = document.getElementById("game-list");
const servicesContainer = document.querySelector(".services");

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


    fetch('/orders/getAllOrders')  // Укажите ваш правильный эндпоинт
        .then((response) => {
        if (!response.ok) {
            throw new Error("Network response was not ok " + response.statusText);
        }
        return response.json(); // Парсим JSON из ответа
        })
        .then((data) => {
            console.log(data)
            data.forEach((order) => {
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
            })
        })
        .catch(error => {
            console.error('Error fetching service data:', error);
        });

});