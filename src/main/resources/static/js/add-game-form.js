const token = $("meta[name='_csrf']").attr("content");
document.addEventListener('DOMContentLoaded', () => {
    const categoriesContainer = document.getElementById('categoriesContainer');
    const addCategoryBtn = document.getElementById('addCategoryBtn');
    const saveGameBtn = document.getElementById('saveGameBtn');
    const jsonOutput = document.getElementById('jsonOutput');

    addCategoryBtn.addEventListener('click', () => addCategory(categoriesContainer));
    saveGameBtn.addEventListener('click', saveGameToJson);

    function addCategory(container) {
        const categoryDiv = document.createElement('div');
        categoryDiv.classList.add('category');

        const categoryInput = document.createElement('input');
        categoryInput.type = 'text';
        categoryInput.placeholder = 'Название категории';
        categoryInput.classList.add('category-name');

        const categoryPercentInput = document.createElement('input');
        categoryPercentInput.type = 'number';
        categoryPercentInput.placeholder = 'Процент услуги (%)';
        categoryPercentInput.classList.add('category-percent');

        const addSubcategoryBtn = document.createElement('button');
        addSubcategoryBtn.type = 'button';
        addSubcategoryBtn.classList.add('add-btn');
        addSubcategoryBtn.textContent = '+';
        addSubcategoryBtn.addEventListener('click', () => addSubcategory(categoryDiv));

        const deleteCategoryBtn = document.createElement('button');
        deleteCategoryBtn.type = 'button';
        deleteCategoryBtn.classList.add('delete-btn');
        deleteCategoryBtn.textContent = '-';
        deleteCategoryBtn.addEventListener('click', () => categoryDiv.remove());

        const toggleCategoryBtn = document.createElement('button');
        toggleCategoryBtn.type = 'button';
        toggleCategoryBtn.classList.add('toggle-btn');
        toggleCategoryBtn.textContent = 'Свернуть';
        toggleCategoryBtn.addEventListener('click', () => {
            const subcategoriesContainer = categoryDiv.querySelector('.nested-subcategories');
            if (subcategoriesContainer.classList.contains('hidden')) {
                subcategoriesContainer.classList.remove('hidden');
                toggleCategoryBtn.textContent = 'Свернуть';
            } else {
                subcategoriesContainer.classList.add('hidden');
                toggleCategoryBtn.textContent = 'Развернуть';
            }
        });

        const subcategoriesContainer = document.createElement('div');
        subcategoriesContainer.classList.add('nested-subcategories');

        categoryDiv.appendChild(categoryInput);
        categoryDiv.appendChild(categoryPercentInput);
        categoryDiv.appendChild(addSubcategoryBtn);
        categoryDiv.appendChild(deleteCategoryBtn);
        categoryDiv.appendChild(toggleCategoryBtn);
        categoryDiv.appendChild(subcategoriesContainer);
        container.appendChild(categoryDiv);
    }

    function addSubcategory(parentContainer) {
        const subcategoryDiv = document.createElement('div');
        subcategoryDiv.classList.add('subcategory');

        const subcategoryInput = document.createElement('input');
        subcategoryInput.type = 'text';
        subcategoryInput.placeholder = 'Название подкатегории';
        subcategoryInput.classList.add('subcategory-name');

        const subcategoryPercentInput = document.createElement('input');
        subcategoryPercentInput.type = 'number';
        subcategoryPercentInput.placeholder = 'Процент услуги (%)';
        subcategoryPercentInput.classList.add('subcategory-percent');

        const addNestedSubcategoryBtn = document.createElement('button');
        addNestedSubcategoryBtn.type = 'button';
        addNestedSubcategoryBtn.classList.add('add-btn');
        addNestedSubcategoryBtn.textContent = '+';
        addNestedSubcategoryBtn.addEventListener('click', () => addSubcategory(subcategoryDiv));

        const deleteSubcategoryBtn = document.createElement('button');
        deleteSubcategoryBtn.type = 'button';
        deleteSubcategoryBtn.classList.add('delete-btn');
        deleteSubcategoryBtn.textContent = '-';
        deleteSubcategoryBtn.addEventListener('click', () => subcategoryDiv.remove());

        const nestedSubcategoriesContainer = document.createElement('div');
        nestedSubcategoriesContainer.classList.add('nested-subcategories');

        subcategoryDiv.appendChild(subcategoryInput);
        subcategoryDiv.appendChild(subcategoryPercentInput);
        subcategoryDiv.appendChild(addNestedSubcategoryBtn);
        subcategoryDiv.appendChild(deleteSubcategoryBtn);
        subcategoryDiv.appendChild(nestedSubcategoriesContainer);
        parentContainer.querySelector('.nested-subcategories').appendChild(subcategoryDiv);
    }

    function saveGameToJson() {
        const gameTitle = document.getElementById('gameTitle').value;
        const gameDescription = document.getElementById('gameDescription').value;

        if (!gameTitle || !gameDescription) {
            alert('Пожалуйста, заполните название и описание игры.');
            return;
        }

        const categories = Array.from(document.querySelectorAll('.category')).map(category => ({
            name: category.querySelector('.category-name').value,
            percent: parseFloat(category.querySelector('.category-percent').value) || 0,
            subcategories: getSubcategories(category.querySelector('.nested-subcategories'))
        }));

        const gameData = { title: gameTitle, description: gameDescription, categories };
        jsonOutput.textContent = JSON.stringify(gameData, null, 2);

        sendDataToServer(gameData);
    }

    function getSubcategories(container) {
        return Array.from(container.children).map(subcategory => ({
            name: subcategory.querySelector('.subcategory-name').value,
            percent: parseFloat(subcategory.querySelector('.subcategory-percent').value) || 0,
            subcategories: getSubcategories(subcategory.querySelector('.nested-subcategories'))
        }));
    }

    function sendDataToServer(data) {
        fetch('/games/addNewGame', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    alert('Игра успешно сохранена!');
                } else {
                    alert('Ошибка при сохранении игры.');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert('Произошла ошибка при отправке данных.');
            });
    }
});