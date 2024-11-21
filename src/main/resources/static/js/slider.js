document.addEventListener("DOMContentLoaded", () => {
    const sliderContainer = document.querySelector(".slider-container");
    const slides = document.querySelectorAll(".slide");
    let currentIndex = 0;

    setInterval(() => {
        currentIndex = (currentIndex + 1) % slides.length;
        sliderContainer.style.transform = `translateX(-${currentIndex * 100}%)`;
    }, 5000);
});
