// document.addEventListener("DOMContentLoaded", async () => {
//     try {
//         const navbar = document.getElementById('navbar');
//         const footer = document.getElementById('footer');
//
//         // Загружаем контент для навбара
//         const navbarResponse = await fetch('components/navbar.html');
//         if (navbarResponse.ok) {
//             navbar.innerHTML = await navbarResponse.text();
//         } else {
//             console.error(`Ошибка загрузки navbar.html: ${navbarResponse.status}`);
//         }
//
//         // Загружаем контент для футера
//         const footerResponse = await fetch('components/footer.html');
//         if (footerResponse.ok) {
//             footer.innerHTML = await footerResponse.text();
//         } else {
//             console.error(`Ошибка загрузки footer.html: ${footerResponse.status}`);
//         }
//     } catch (error) {
//         console.error('Ошибка при загрузке компонентов:', error);
//     }
// });
