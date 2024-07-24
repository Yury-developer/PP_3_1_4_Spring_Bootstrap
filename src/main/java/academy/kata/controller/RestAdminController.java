package academy.kata.controller;

import academy.kata.dto.RoleDto;
import academy.kata.dto.UserDto;
import academy.kata.exeption_hendling.NoSuchUserExeption;
import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import academy.kata.service.UserService;
import academy.kata.utils.ModelTransfer;
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

    private final ModelTransfer modelTransfer;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public RestAdminController(ModelTransfer modelTransfer, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.modelTransfer = modelTransfer;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }









    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        System.out.println("***** ***** ***** ***** RestAdminController: getAll");   // ********** УДАЛИТЬ **********
        List<UserDto> userDtoList = modelTransfer.toUserDtosList(userService.findAll());
        userDtoList.forEach(System.out::println);   // ********** УДАЛИТЬ **********
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping("/getUsersByRoleName/{role_name}")
    public ResponseEntity<List<UserDto>> getUsersByRoleName(@PathVariable("role_name") String roleName) {
            System.out.println("***** ***** ***** ***** RestAdminController: getUsersByRole");   // ********** УДАЛИТЬ **********
            System.out.println("***** ***** ***** ***** role_name = " + roleName);   // ********** УДАЛИТЬ **********

        Role role = roleService.findByName(roleName);

            System.out.println("***** ***** ***** ***** role_name = " + roleName);   // ********** УДАЛИТЬ **********

        List<UserDto> userDtoList = modelTransfer.toUserDtosList(userService.findUsersByRole(role));

            userDtoList.forEach(System.out::println);   // ********** УДАЛИТЬ **********

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        System.out.println("***** ***** ***** ***** RestAdminController: get(id); id = " + id);   // ********** УДАЛИТЬ **********
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            System.out.println(userOptional.get());   // ********** УДАЛИТЬ **********
            return new ResponseEntity<>(
                    modelTransfer.toUserDto(userOptional.get()),
                    HttpStatus.OK);
        } else  {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден!" +
                    " // User with id = " + id + " not found!");
        }
    }

    @CrossOrigin(origins = "http://localhost:63343")
    @PostMapping("/")
    public ResponseEntity<UserDto> addNewUser(@RequestBody UserDto userDto){

        System.out.println("\n\n***** ***** *****\n");   // ********** УДАЛИТЬ **********
        System.out.println("*** RestAdminController: addNewUser ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** Incoming JSON ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** userDto = " + userDto);   // ********** УДАЛИТЬ **********

        User user = modelTransfer.toUser(userDto);

        System.out.println("*** Converted User ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** user = " + user);   // ********** УДАЛИТЬ **********
        System.out.println("\n*** user.getClass() = " + user.getClass().getSimpleName());   // ********** УДАЛИТЬ **********
        System.out.println("*** roles.getClass = " + user.getRoles().toArray()[0].getClass().getSimpleName());   // ********** УДАЛИТЬ **********
        System.out.println("\n***** ***** *****\n\n");   // ********** УДАЛИТЬ **********

        userService.saveUser(user);
        return new ResponseEntity<>(modelTransfer.toUserDto(user), HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping("/getRoles")
    public ResponseEntity<Set<RoleDto>> getAllExistingRoles() {
        Set<RoleDto> roleDtoSet = modelTransfer.toRoleDtosSet(new HashSet<>(roleService.findAll()));
        return new ResponseEntity<>(roleDtoSet, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:63343")
    @PutMapping("/")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto) {
        System.out.println("*** Incoming User: ***\n " + userDto);   // ********** УДАЛИТЬ **********
        List<Long> selectedRoles = userDto.getRoles().stream().map(roleDto -> roleDto.getId()).collect(Collectors.toList());

        User updateUser = modelTransfer.toUser(userDto);
        updateUser.setId(userDto.getId());

        userService.updateUser(updateUser, selectedRoles);
        UserDto updateUserDto = modelTransfer.toUserDto(userService.findById(userDto.getId()).get());
        System.out.println("*** Saved User: " + userDto);   // ********** УДАЛИТЬ **********
        return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:63343")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        System.out.println("RestAdminController: deleteUser(id); id = " + id);   // ********** УДАЛИТЬ **********
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            UserDto userDto = modelTransfer.toUserDto(userOptional.get());
            userService.deleteById(userDto.getId());
            System.out.println(userDto);   // ********** УДАЛИТЬ **********
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else  {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден!" +
                    " // User with id = " + id + " not found!");
        }
    }
}
