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
        addSubcategoryBtn.textContent = 'Добавить подкатегорию';
        addSubcategoryBtn.addEventListener('click', () => addSubcategory(categoryDiv));
        const subcategoriesContainer = document.createElement('div');
        subcategoriesContainer.classList.add('nested-subcategories');
        const deleteCategoryBtn = document.createElement('button');
        deleteCategoryBtn.type = 'button';
        deleteCategoryBtn.textContent = 'Удалить категорию';
        deleteCategoryBtn.addEventListener('click', () => categoryDiv.remove());
        categoryDiv.appendChild(categoryInput);
        categoryDiv.appendChild(categoryPercentInput);
        categoryDiv.appendChild(addSubcategoryBtn);
        categoryDiv.appendChild(subcategoriesContainer);
        categoryDiv.appendChild(deleteCategoryBtn);
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
        addNestedSubcategoryBtn.textContent = 'Добавить вложенную подкатегорию';
        addNestedSubcategoryBtn.addEventListener('click', () => addSubcategory(subcategoryDiv));
        const nestedSubcategoriesContainer = document.createElement('div');
        nestedSubcategoriesContainer.classList.add('nested-subcategories');
        const deleteSubcategoryBtn = document.createElement('button');
        deleteSubcategoryBtn.type = 'button';
        deleteSubcategoryBtn.textContent = 'Удалить подкатегорию';
        deleteSubcategoryBtn.addEventListener('click', () => subcategoryDiv.remove());
        subcategoryDiv.appendChild(subcategoryInput);
        subcategoryDiv.appendChild(subcategoryPercentInput);
        subcategoryDiv.appendChild(addNestedSubcategoryBtn);
        subcategoryDiv.appendChild(nestedSubcategoriesContainer);
        subcategoryDiv.appendChild(deleteSubcategoryBtn);
        parentContainer.querySelector('.nested-subcategories').appendChild(subcategoryDiv);
    }
    function saveGameToJson() {
        const gameTitle = document.getElementById('gameTitle').value;
        const gameDescription = document.getElementById('gameDescription').value;

        if (!gameTitle || !gameDescription) {
            alert('Пожалуйста, заполните название и описание игры.');
            return;
        }

        const categories = Array.from(document.querySelectorAll('.category')).map(category => {
            const categoryName = category.querySelector('.category-name').value;

            if (!categoryName) {
                alert('У каждой категории должно быть название.');
                throw new Error('Категория без названия');
            }

            const subcategories = getSubcategories(category);

            return {
                name: categoryName,
                subcategories: subcategories
            };
        });

        const gameData = {
            title: gameTitle,
            description: gameDescription,
            categories: categories
        };

        jsonOutput.textContent = JSON.stringify(gameData, null, 2);

        sendDataToServer(gameData);
    }

    function getSubcategories(categoryDiv) {
        return Array.from(categoryDiv.querySelectorAll(':scope > .nested-subcategories > .subcategory')).map(subcategory => {
            const subcategoryName = subcategory.querySelector('.subcategory-name').value;

            if (!subcategoryName) {
                alert('У каждой подкатегории должно быть название.');
                throw new Error('Подкатегория без названия');
            }

            const nestedSubcategories = getSubcategories(subcategory);

            return {
                name: subcategoryName,
                subcategories: nestedSubcategories
            };
        });
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
    function parseSubcategories(container) {
        return Array.from(container.children).filter(child => child.classList.contains('subcategory')).map(subcategoryDiv => {
            return {
                subcategoryName: subcategoryDiv.querySelector('.subcategory-name').value,
                subcategoryPercent: parseFloat(subcategoryDiv.querySelector('.subcategory-percent').value) || 0,
                subcategories: parseSubcategories(subcategoryDiv.querySelector('.nested-subcategories'))
            };
        });
    }
});