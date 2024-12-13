function setNavbar() {
    const dropdownMenu = document.getElementById('dropdown-menu');

    const dropdownToggle = document.getElementById('dropdown-toggle');

    const homePage = document.getElementById('home-page');

    homePage.addEventListener('click', () => {
        window.location.href = '/user/mainPage';
    })

    dropdownToggle.addEventListener('click', () => {
        // window.location.href = '/games/getServicesPage';
    });

    fetch('/games/getAllGames')
        .then(response => response.json())
        .then(games => {
            games.forEach(game => {
                const subDropdownMenu = document.createElement('li');
                subDropdownMenu.className = 'dropdown-submenu';

                const gameLink = document.createElement('a');

                gameLink.text = game.title;

                subDropdownMenu.appendChild(gameLink);
                dropdownMenu.appendChild(subDropdownMenu);

                subDropdownMenu.addEventListener('click', () => {
                    window.location.href = '/games/getServicesPage/' + game.id;
                })
            });


        })
        .catch(error => console.error('Error loading games:', error));
}