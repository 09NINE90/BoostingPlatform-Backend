const gameListElement = document.getElementById("game-list");
const servicesContainer = document.querySelector(".services");
const paginationContainer = document.querySelector(".pagination");
const token = $("meta[name='_csrf']").attr("content");
let currentPage = 1; // Текущая страница (начинается с 1)
const pageSize = 20; // Размер страницы
const filterPanel = document.getElementById("filter-panel");
const gameOptions = document.getElementById("game-options");
const hideButton = document.getElementById("hide-filters-button");
const showButton= document.getElementById("show-filters-button");
const getButton = document.getElementById("get-filters-button");

let gameTitle = '';
const game = {
    title: gameTitle,
};
hideButton.addEventListener("click", () => {
    filterPanel.classList.remove("flex");
    filterPanel.classList.add("hidden");
});

showButton.addEventListener("click", () => {
    filterPanel.classList.remove("hidden");
    filterPanel.classList.add("flex");
});
document.addEventListener("DOMContentLoaded", () => {

    fetch(`/games/getAllGames`)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            gameOptions.innerHTML = "";
            data.forEach((games) => {
                const label = document.createElement("label");
                const input = document.createElement("input");

                input.type = "radio";
                input.name = "game";
                input.id = 'radio-game';
                input.checked = true;
                input.value = games.title;

                label.appendChild(input);
                label.appendChild(document.createTextNode(games.title));
                gameOptions.appendChild(label);

                const listItem = document.createElement("li");

                const link = document.createElement("a");
                link.href = "#";
                link.textContent = games.title;

                link.classList.add("active");

                listItem.appendChild(link);
                gameListElement.appendChild(listItem);

                getButton.addEventListener('click', () => {
                    const gameRadios = document.querySelectorAll('input[name="game"]');
                    gameRadios.forEach(radio => {
                        if (radio.checked === true){
                            gameTitle = radio.value;
                            const game = {
                                title: gameTitle,
                            };
                            addPageNumber(currentPage, game);
                        }
                    })
                })

            });
        })
        .catch((error) => {
            console.error("Error fetching games:", error);
        });

    addPageNumber(currentPage)


});

function addPageNumber(pageNumber, game = {}) {
    fetch('/orders/getAllOrders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify({game, pageNumber, pageSize }),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            servicesContainer.innerHTML = '';
            data.baseOrder.forEach((order) => {
                const serviceCard = document.createElement("div");
                serviceCard.classList.add("service-card");

                const img = document.createElement("img");
                img.src = "/images/slider.PNG";
                img.alt = "Order Icon";

                const details = document.createElement("div");
                details.classList.add("service-details");

                const title = document.createElement("h3");
                title.textContent = order.title;

                const description = document.createElement("p");
                description.textContent = order.description;

                const gameTitle = document.createElement("p");
                gameTitle.textContent = order.game?.title;

                const priceContainer = document.createElement("div");
                priceContainer.classList.add("service-price");

                const price = document.createElement("p");
                price.textContent = `${order.basePrice} $`;

                details.appendChild(title);
                details.appendChild(description);
                details.appendChild(gameTitle);
                priceContainer.appendChild(price);

                serviceCard.appendChild(img);
                serviceCard.appendChild(details);
                serviceCard.appendChild(priceContainer);

                servicesContainer.appendChild(serviceCard);
            });

            renderPagination(data.pageNumber, data.pageTotal);
        })
        .catch(error => {
            console.error('Error fetching service data:', error);
        });
}
