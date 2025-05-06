const navbar = document.querySelector('.navbar');
const toggleBtn = document.querySelector('.menu-toggle');
const menu = document.querySelector('nav ul');

// Thêm hiệu ứng blur cho navbar khi cuộn
window.addEventListener('scroll', () => {
  if (window.scrollY > 10) {
    navbar.classList.add('blur');
  } else {
    navbar.classList.remove('blur');
  }
});

// Hiện/ẩn menu khi nhấn vào nút ba gạch
toggleBtn.addEventListener('click', (e) => {
  e.stopPropagation(); // Ngăn sự kiện lan ra ngoài
  menu.classList.toggle('show');
});

// Ẩn menu khi nhấn ra ngoài khu vực menu
document.addEventListener('click', (e) => {
  if (!menu.contains(e.target) && !toggleBtn.contains(e.target)) {
    menu.classList.remove('show');
  }
});
