/*
    Этот файл будет содержать логику переключения ролей и обновления списка пользователей
 */

document.addEventListener('DOMContentLoaded', () => {   // добавляет обработчик события, кот. выполнится, когда весь DOM будет загружен. Это гарантирует, что все элементы, с которыми мы будем взаимодействовать, уже существуют на странице.
    const roleLinks = document.querySelectorAll('.role-link');   // находит все элементы на странице с классом .role-link и сохраняет их в переменную roleLinks. Это NodeList, который похож на массив и содержит все ссылки ролей.
    const dataContainer = document.getElementById('dataContainer');

    roleLinks.forEach(link => {
        link.addEventListener('click', (event) => {   // Для каждого элемента role-link добавляется обработчик события click. Когда пользователь кликает на ссылку роли, выполняется функция, переданная в addEventListener.
            const roleName = event.target.getAttribute('data-role-name');   // извлекает значение атрибута data-role-name из элемента, на который кликнул пользователь. Это значение соответствует имени роли, связанной с этой ссылкой
            dataContainer.setAttribute('current_role_name', roleName);   // находит элемент с ID dataContainer и сохраняет его в переменную dataContainer. Этот элемент используется для хранения текущей выбранной роли.

            // Обновляем активный класс
            roleLinks.forEach(link => {   // перебирает все элементы в roleLinks и добавляет к каждому из них обработчик события клика.
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