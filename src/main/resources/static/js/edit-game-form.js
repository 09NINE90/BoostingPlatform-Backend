const token = $("meta[name='_csrf']").attr("content");

document.addEventListener('DOMContentLoaded', () => {
    const categoriesContainer = document.getElementById('editCategoriesContainer');
    const addCategoryBtn = document.getElementById('editAddCategoryBtn');
    const saveGameBtn = document.getElementById('editSaveGameBtn');
    const gameId = document.getElementById('game-id').textContent; // ID игры

    // Загружаем данные игры
    fetch(`/games/${gameId}`)
        .then((response) => {
            if (!response.ok) throw new Error('Ошибка загрузки игры.');
            return response.json();
        })
        .then((game) => {
            populateForm(game);
        })
        .catch((error) => console.error('Ошибка загрузки данных:', error));

    addCategoryBtn.addEventListener('click', () => addCategory(categoriesContainer));
    saveGameBtn.addEventListener('click', saveEditedGame);

    // Заполняем форму данными игры
    function populateForm(game) {
        document.getElementById('editGameTitle').value = game.title || '';
        document.getElementById('editGameDescription').value = game.description || '';

        if (game.categories && Array.isArray(game.categories)) {
            game.categories.forEach((category) => {
                addCategory(categoriesContainer, category);
            });
        }
    }

    // Добавление категории
    function addCategory(container, categoryData = {}) {
        const categoryDiv = document.createElement('div');
        categoryDiv.classList.add('category');

        const categoryInput = document.createElement('input');
        categoryInput.type = 'text';
        categoryInput.placeholder = 'Название категории';
        categoryInput.value = categoryData.name || '';
        categoryInput.classList.add('category-name');

        // const categoryPercentInput = document.createElement('input');
        // categoryPercentInput.type = 'number';
        // categoryPercentInput.placeholder = 'Процент услуги (%)';
        // categoryPercentInput.value = categoryData.percent || 0;
        // categoryPercentInput.classList.add('category-percent');

        const addSubcategoryBtn = document.createElement('button');
        addSubcategoryBtn.type = 'button';
        addSubcategoryBtn.textContent = '+';
        addSubcategoryBtn.addEventListener('click', () => addSubcategory(categoryDiv));

        const deleteCategoryBtn = document.createElement('button');
        deleteCategoryBtn.type = 'button';
        deleteCategoryBtn.textContent = '-';
        deleteCategoryBtn.addEventListener('click', () => categoryDiv.remove());

        const subcategoriesContainer = document.createElement('div');
        subcategoriesContainer.classList.add('nested-subcategories');

        categoryDiv.append(categoryInput, addSubcategoryBtn, deleteCategoryBtn, subcategoriesContainer);
        container.appendChild(categoryDiv);

        // Добавляем подкатегории, если они есть
        if (categoryData.subcategories && Array.isArray(categoryData.subcategories)) {
            categoryData.subcategories.forEach((subcategory) => {
                addSubcategory(categoryDiv, subcategory);
            });
        }
    }

    // Добавление подкатегории
    function addSubcategory(parentContainer, subcategoryData = {}) {
        const subcategoryDiv = document.createElement('div');
        subcategoryDiv.classList.add('subcategory');

        const subcategoryInput = document.createElement('input');
        subcategoryInput.type = 'text';
        subcategoryInput.placeholder = 'Название подкатегории';
        subcategoryInput.value = subcategoryData.name || '';
        subcategoryInput.classList.add('subcategory-name');

        // const subcategoryPercentInput = document.createElement('input');
        // subcategoryPercentInput.type = 'number';
        // subcategoryPercentInput.placeholder = 'Процент услуги (%)';
        // subcategoryPercentInput.value = subcategoryData.percent || 0;
        // subcategoryPercentInput.classList.add('subcategory-percent');

        const addNestedSubcategoryBtn = document.createElement('button');
        addNestedSubcategoryBtn.type = 'button';
        addNestedSubcategoryBtn.textContent = '+';
        addNestedSubcategoryBtn.addEventListener('click', () => addSubcategory(subcategoryDiv));

        const deleteSubcategoryBtn = document.createElement('button');
        deleteSubcategoryBtn.type = 'button';
        deleteSubcategoryBtn.textContent = '-';
        deleteSubcategoryBtn.addEventListener('click', () => subcategoryDiv.remove());

        const nestedSubcategoriesContainer = document.createElement('div');
        nestedSubcategoriesContainer.classList.add('nested-subcategories');

        subcategoryDiv.append(subcategoryInput, addNestedSubcategoryBtn, deleteSubcategoryBtn, nestedSubcategoriesContainer);
        parentContainer.querySelector('.nested-subcategories').appendChild(subcategoryDiv);

        // Добавляем вложенные подкатегории, если они есть
        if (subcategoryData.subcategories && Array.isArray(subcategoryData.subcategories)) {
            subcategoryData.subcategories.forEach((nestedSubcategory) => {
                addSubcategory(subcategoryDiv, nestedSubcategory);
            });
        }
    }

    // Сохранение изменений
    function saveEditedGame() {
        const gameTitle = document.getElementById('editGameTitle').value;
        const gameDescription = document.getElementById('editGameDescription').value;

        const categories = Array.from(document.querySelectorAll('.category')).map((category) => ({
            name: category.querySelector('.category-name').value,
            subcategories: getSubcategories(category.querySelector('.nested-subcategories')),
        }));

        const gameData = { id: gameId, title: gameTitle, description: gameDescription, categories };

        sendDataToServerEdit(gameData)
    }

    function sendDataToServerEdit(data) {
        fetch(`/games/editGame`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/user/mainPage';
                } else {
                    alert('Ошибка при сохранении игры.');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Произошла ошибка при отправке данных.');
            });
    }

    // Получение подкатегорий
    function getSubcategories(container) {
        return Array.from(container.children).map((subcategory) => ({
            name: subcategory.querySelector('.subcategory-name').value,
            subcategories: getSubcategories(subcategory.querySelector('.nested-subcategories')),
        }));
    }
});