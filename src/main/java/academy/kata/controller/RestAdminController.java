package academy.kata.controller;


import academy.kata.exeption_hendling.NoSuchUserExeption;
import academy.kata.exeption_hendling.UserIncorrectData;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import academy.kata.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class RestAdminController {


    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public RestAdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    public List<User> getAll() {
        System.out.println("RestAdminController: getAll");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        System.out.println("RestAdminController: get(id); id = " + id);

        User user = userService.findById(id);
        if (user == null) {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден! \n" +
                    "User with id = " + id + " not found!");
        };
        return user;
    }

    // hреагирует на   NoSuchUserExeption
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleExeption (NoSuchUserExeption exeption){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exeption.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
