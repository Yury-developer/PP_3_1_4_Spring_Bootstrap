package academy.kata.controller;


import academy.kata.exeption_hendling.NoSuchUserExeption;
import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import academy.kata.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


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









//    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        System.out.println("***** ***** ***** ***** RestAdminController: getAll");   // ********** УДАЛИТЬ **********
        List<User> dtosList = userService.findAll();
        dtosList.forEach(System.out::println);   // ********** УДАЛИТЬ **********
        return new ResponseEntity<>(dtosList, HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping("/getUsersByRoleName/{role_name}")
    public ResponseEntity<List<User>> getUsersByRoleName(@PathVariable("role_name") String roleName) {
            System.out.println("***** ***** ***** ***** RestAdminController: getUsersByRoleName");   // ********** УДАЛИТЬ **********
            System.out.println("***** ***** ***** ***** role_name = " + roleName);   // ********** УДАЛИТЬ **********

        Role role = roleService.findByName(roleName);
        System.out.println("***** ***** ***** ***** role = " + role);   // ********** УДАЛИТЬ **********

        List<User> userList = userService.findUsersByRole(role);

            userList.forEach(System.out::println);   // ********** УДАЛИТЬ **********

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


//    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        System.out.println("***** ***** ***** ***** RestAdminController: get(id); id = " + id);   // ********** УДАЛИТЬ **********
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            System.out.println(userOptional.get());   // ********** УДАЛИТЬ **********
            return new ResponseEntity<>(
                    userOptional.get(),
                    HttpStatus.OK);
        } else  {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден!" +
                    " // User with id = " + id + " not found!");
        }
    }

//    @CrossOrigin(origins = "http://localhost:63343")
    @PostMapping("/")
    public ResponseEntity<User> addNewUser(@RequestBody User user){

        System.out.println("\n\n***** ***** *****\n");   // ********** УДАЛИТЬ **********
        System.out.println("*** RestAdminController: addNewUser ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** Incoming JSON ***");   // ********** УДАЛИТЬ **********

        System.out.println("*** Converted User ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** user = " + user);   // ********** УДАЛИТЬ **********
        System.out.println("\n*** user.getClass() = " + user.getClass().getSimpleName());   // ********** УДАЛИТЬ **********
        System.out.println("*** roles.getClass = " + user.getRoles().toArray()[0].getClass().getSimpleName());   // ********** УДАЛИТЬ **********
        System.out.println("\n***** ***** *****\n\n");   // ********** УДАЛИТЬ **********

        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.findById(role.getId()))
                .collect(Collectors.toSet()));
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


//    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping("/getRoles")
    public ResponseEntity<Set<Role>> getAllExistingRoles() {
        Set<Role> roleDtoSet = new HashSet<>(roleService.findAll());
        return new ResponseEntity<>(roleDtoSet, HttpStatus.OK);
    }














//    @CrossOrigin(origins = "http://localhost:63343")
    @PutMapping("/")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        System.out.println("\n\n***** ***** *****\n");   // ********** УДАЛИТЬ **********
        System.out.println("*** RestAdminController: editUser ***\nuser = " + user);   // ********** УДАЛИТЬ **********System.out.println("*** Incoming User: ***\n " + user);   // ********** УДАЛИТЬ **********
        List<Long> selectedRoles = user.getRoles().stream()
                .map(role -> role.getId())
                .collect(Collectors.toList());

        userService.updateUser(user, selectedRoles);
        User updateUser = userService.findById(user.getId()).get();
        System.out.println("*** Saved User: " + user);   // ********** УДАЛИТЬ **********
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }















//    @CrossOrigin(origins = "http://localhost:63343")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        System.out.println("\n\n***** ***** *****\n");   // ********** УДАЛИТЬ **********
        System.out.println("*** RestAdminController: deleteUser ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** RestAdminController: deleteUser(id); id = " + id + " ***");   // ********** УДАЛИТЬ **********
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            User deletedUser = new User(user);
            userService.deleteById(user.getId());
            System.out.println("*** Удален пользователь:\n" + deletedUser);   // ********** УДАЛИТЬ **********
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } else  {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден!" +
                    " // User with id = " + id + " not found!");
        }
    }
}
