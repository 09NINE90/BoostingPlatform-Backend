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
            addPageNumber(currentPage);
        }
    });
    paginationContainer.appendChild(prevLink);

    const maxPages = 4;
    let startPage = Math.max(1, currentPage - Math.floor(maxPages / 2));
    let endPage = Math.min(totalPages, startPage + maxPages + 1);


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
                addPageNumber(currentPage);
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
            addPageNumber(currentPage);
        }
    });
    paginationContainer.appendChild(nextLink);
}