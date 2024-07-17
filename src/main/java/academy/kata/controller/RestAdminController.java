package academy.kata.controller;


import academy.kata.dto.RoleDto;
import academy.kata.dto.UserDto;
import academy.kata.exeption_hendling.NoSuchUserExeption;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import academy.kata.service.UserService;
import academy.kata.utils.ModelTransfer;
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


    @GetMapping
    public List<UserDto> getAll() {
        System.out.println("RestAdminController: getAll");   // ********** УДАЛИТЬ **********
        List<UserDto> userDtoList = modelTransfer.toUserDtosList(userService.findAll());
        return userDtoList;
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        System.out.println("RestAdminController: get(id); id = " + id);   // ********** УДАЛИТЬ **********
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            System.out.println(userOptional.get());   // ********** УДАЛИТЬ **********
            return modelTransfer.toUserDto(userOptional.get());
        } else  {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден!" +
                    " // User with id = " + id + " not found!");
        }
    }

    @PostMapping("/")
    public UserDto addNewUser(@RequestBody UserDto userDto){

        System.out.println("*** Incoming JSON ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** userDto = " + userDto);   // ********** УДАЛИТЬ **********

        User user = modelTransfer.toUser(userDto);

        System.out.println("*** Converted User ***");   // ********** УДАЛИТЬ **********
        System.out.println("*** user = " + user);   // ********** УДАЛИТЬ **********

        userService.saveUser(user);
        return modelTransfer.toUserDto(user);
    }


    @GetMapping("/getRoles")
    public Set<RoleDto> getAllExistingRoles() {
        Set<RoleDto> roleDtoSet = modelTransfer.toRoleDtosSet(new HashSet<>(roleService.findAll()));
        return roleDtoSet;
    }

    @PutMapping("/")
    public UserDto editUser(@RequestBody UserDto userDto) {
        System.out.println("*** Incoming User: ***\n " + userDto);   // ********** УДАЛИТЬ **********
        List<Long> selectedRoles = userDto.getRoles().stream().map(roleDto -> roleDto.getId()).collect(Collectors.toList());

        User updateUser = modelTransfer.toUser(userDto);
        updateUser.setId(userDto.getId());

        userService.updateUser(updateUser, selectedRoles);
        UserDto updateUserDto = modelTransfer.toUserDto(userService.findById(userDto.getId()).get());
        System.out.println("*** Saved User: " + userDto);   // ********** УДАЛИТЬ **********
        return updateUserDto;
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id) {
        System.out.println("RestAdminController: deleteUser(id); id = " + id);   // ********** УДАЛИТЬ **********
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            UserDto userDto = modelTransfer.toUserDto(userOptional.get());
            userService.deleteById(userDto.getId());
            System.out.println(userDto);   // ********** УДАЛИТЬ **********
            return userDto;
        } else  {
            throw new NoSuchUserExeption("Пользователь с id = " + id + " не найден!" +
                    " // User with id = " + id + " not found!");
        }
    }

}
