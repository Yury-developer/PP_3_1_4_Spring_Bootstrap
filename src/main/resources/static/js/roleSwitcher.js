/*
    Этот файл будет содержать логику переключения ролей и обновления списка пользователей
 */

document.addEventListener('DOMContentLoaded', () => {
    const roleLinks = document.querySelectorAll('.role-link');
    const dataContainer = document.getElementById('dataContainer');

    roleLinks.forEach(link => {
        link.addEventListener('click', (event) => {
            const roleName = event.target.getAttribute('data-role-name');
            dataContainer.setAttribute('current_role_name', roleName);

            // Обновляем активный класс
            roleLinks.forEach(link => {
                if (link.getAttribute('data-role-name') === roleName) {
                    link.classList.add('bg-primary', 'text-white', 'active');
                    link.classList.remove('bg-white', 'text-primary');
                } else {
                    link.classList.remove('bg-primary', 'text-white', 'active');
                    link.classList.add('bg-white', 'text-primary');
                }
            });

            // Загружаем новый список пользователей
            getUsersByRoleName();
        });
    });
});