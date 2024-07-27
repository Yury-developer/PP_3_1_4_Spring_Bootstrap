const usersTableURL = 'http://localhost:8080/api/admin/getUsersByRoleName/';

document.addEventListener('DOMContentLoaded', getUsersByRoleName);

function getUsersByRoleName() {
    let currentRoleName = document.getElementById('dataContainer').getAttribute('current_role_name');
    if (currentRoleName == null) {
        document.getElementById('dataContainer').setAttribute('current_role_name', 'ROLE_USER');
        currentRoleName = document.getElementById('dataContainer').getAttribute('current_role_name');
        console.log('getUsersByRoleName() : current_role_name = ' + currentRoleName);
    }

    fetch(usersTableURL + currentRoleName) // отправляет HTTP GET запрос
        .then(response => response.json()) // ответ конвертируется из JSON-формата в JavaScript объек
        .then(function (users) {
            console.log('users = ' + users)   // *** *** *** УДАЛИТЬ
            users.forEach(user => console.log(user));   // *** *** *** УДАЛИТЬ

            let dataOfUsers = ''; // для хранения HTML-кода
            let rolesString = ''; // для хранения строковых представлений ролей пользователей.

            const tableUsers = document.getElementById('userTable'); // Получаем элемент таблицы, в который будут вставлены данные пользователей.

            document.getElementById('roleDescription').innerHTML =
                `All users, who have a role   '${currentRoleName.replace('ROLE_', '')}'`;


            // Заполняем таблицу данными
            for (let user of users) {
                // console.log('*-* userTable.js: user = ' + user.id);
                // console.log('*-* userTable.js: user = ' + user.name);
                // console.log('*-* userTable.js: user = ' + user);
                // console.log('*-* userTable.js: user = ' + user.toString());

                rolesString = rolesToString(user.roles);

                dataOfUsers += `<tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.fullName}</td>
                        <td>${user.dateBirth}</td>
                        <td>${user.address}</td>
                        <td>${user.email}</td>
                        <td>${rolesString}</td>
                        
                        

                        <td>
                            <button type="button" 
                                    class="btn btn-info" 
                                    data-bs-toggle="modal" 
                                    data-target="#editModal" 
                                    onclick="editModal(${user.id})">
                                Edit
                            </button>
                        </td>

                        <td>
                            <button type="button" 
                                    class="btn btn-danger" 
                                    data-bs-toggle="modal" 
                                    data-target="#deleteModal" 
                                    onclick="deleteModal(${user.id})">
                                Delete
                            </button>
                        </td>
                        
                    </tr>`;
                // <b>
                //     console.log("*** editModal( user.id = ")
                //     console.log(${user.id}))
                //     console.log(user.id)
                // </b>
            }
            tableUsers.innerHTML = dataOfUsers; // Заполненный HTML-код вставляется в таблицу.
        })
        .catch(error => console.error('*** *** *** Ошибка при загрузке списка пользователей:', error));
}

function rolesToString(roles) {
    // console.log('roles:', roles); //  для отладки -  ****** УДАЛИТЬ *******
    let rolesString = '';
    for (const element of roles) {
        rolesString += (element.name.toString().replace('ROLE_', '') + ', ');
    }
    rolesString = rolesString.substring(0, rolesString.length - 2);
    return rolesString;
}
