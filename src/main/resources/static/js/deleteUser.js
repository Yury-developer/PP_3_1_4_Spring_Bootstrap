let formDelete = document.forms["formDelete"]
deleteUser();

async function deleteModal(id) {
    const modalDelete = new bootstrap.Modal(document.querySelector('#deleteModal'));
    await open_fill_modal(formDelete, modalDelete, id);
    loadRolesForDelete();
}

function deleteUser() {
    formDelete.addEventListener("submit", ev => {
        ev.preventDefault();   // Обработчик события submit формы formDelete добавляется с вызовом ev.preventDefault() для предотвращения стандартного поведения формы (отправка и перезагрузка страницы).

        let rolesForDelete = [];
        for (let i = 0; i < formDelete.roles.options.length; i++) {
            if (formDelete.roles.options[i].selected) rolesForDelete.push({
                id: formDelete.roles.options[i].value,
                role: "ROLE_" + formDelete.roles.options[i].text
            });
        }

        fetch("http://localhost:8080/api/admin/" + formDelete.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: formDelete.id.value,
                name: formDelete.name.value,
                fullName: formDelete.fullName.value,
                dateBirth: formDelete.dateBirth.value,
                address: formDelete.address.value,
                email: formDelete.email.value,
                roles: rolesForDelete
            })
        }).then(() => {
            $('#deleteClose').click();
            // getAllUsers();
            getUsersByRoleName();
        });
    });
}

function loadRolesForDelete() {
    let selectDelete = document.getElementById("delete-roles");
    selectDelete.innerHTML = "";

    fetch("http://localhost:8080/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name.toString().replace('ROLE_', '');
                selectDelete.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForDelete);