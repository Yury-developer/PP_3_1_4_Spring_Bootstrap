package academy.kata.controller;


import academy.kata.dto.UserDto;
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
import java.util.Optional;

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
    public UserDto get(@PathVariable Long id) {
        System.out.println("RestAdminController: get(id); id = " + id);   // *** УДАЛИТЬ ***

        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {

            System.out.println(userOptional.get());   // *** УДАЛИТЬ ***
            return new UserDto(userOptional.get());
        } else  {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден!" +
                    " // User with id = " + id + " not found!");
        }
    }

    // Реагирует на   NoSuchUserExeption
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleExeption (NoSuchUserExeption exeption){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exeption.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    // Реагирует на ЛЮБОЙ   Exeption
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleExeption (Exception exeption){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exeption.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
