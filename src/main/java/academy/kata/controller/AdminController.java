package academy.kata.controller;

import academy.kata.constants.Constants;
import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.security.UserDetailsImpl;
import academy.kata.service.RoleService;
import academy.kata.service.UserService;
import academy.kata.utils.RoleUtils;
import academy.kata.utils.Utils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * @Author: Yury Lapitski
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private static Logger logger;

    static {
        try {
            Resource resource = new ClassPathResource("adminController_loggerConfig.properties");
            InputStream ins = resource.getInputStream();
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(AdminController.class.getName());
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
            logger = null;
        }
    }


    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }



    // просто возвращает страницу приветствия (с нее можно сгенерировать тестовые данные во все таблицы)
    @RequestMapping(method = RequestMethod.HEAD)
    public String ping() {
        logger.fine("AdminController: ping");
        return "redirect:/";
    }


    @PostMapping("/create")
    public String createUser(@ModelAttribute("createdUser") User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        logger.fine("AdminController: addUser, user = " + user);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping
    public String showAllUsersForm(
            Model model,
            @RequestParam(name = "current_displayed_role", required = false) Role currentDisplayedRole) {
        logger.fine("AdminController: showAllUsersForm");

       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // у меня аутентификация именно по имени пользователя
        UserDetailsImpl currentUser = Utils.userToUserDetails(userService.findByUsername(currentUserName));
        model.addAttribute("current_user", currentUser);

        List<Role> existingRoles = roleService.findAll();
        model.addAttribute("all_existing_roles", existingRoles);

        model.addAttribute("current_max_role", currentUser.getRoles().stream().max(Comparator.comparing(Role::getId)).get()); // тут мы возмем ту роль текущего пользователя, которая имеет максимальный id// т.е. максимальные права.

        User defaultUser = userService.generateNewUsers(0)[0];
        model.addAttribute("created_user", defaultUser); // шаблон пользователя по умолчанию.
        model.addAttribute("default_password", Constants.DEFAULT_PASSWORD.get()); // пароль по умолчанию для создаваемых пользователей.

        if (currentDisplayedRole == null) { // текущая роль, ниже которой отображаем пользователей (включительно)
            currentDisplayedRole = RoleUtils.getRoleWithMaxPriority(existingRoles);
        }
//        currentDisplayedRole = roleService.findByName("ROLE_ADMIN"); // вывести всех с ролью "ROLE_ADMIN" и ниже

        model.addAttribute("current_displayed_role", currentDisplayedRole);

        Role finalCurrentDisplayedRole = currentDisplayedRole;
        List<User> userSelection = userService
                .findAll()
                .stream()
                .filter(user -> user
                        .getRoles()
                        .stream()
                        .filter(role -> role.getId() <= finalCurrentDisplayedRole.getId())
                        .findAny()
                        .isPresent()).collect(Collectors.toList());
        model.addAttribute("users_selection", userSelection);

//        userSelection.stream().forEach(System.out::println);


        return "admin/all-users";
    }


    @PutMapping("/edit")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam("selected_roles[]") List<Long> selectedRoles) {

        logger.fine("AdminController: editUser, " +
                "user_id = " + user.getId() +
                "\n user = " + user +
                "\n roles = " + Arrays.toString(selectedRoles.toArray()));

        userService.updateUser(user, selectedRoles);
        return "redirect:/admin";
    }


    @PostMapping("/delete")
    public String deleteUser(@RequestParam(name = "user_id") Long userId) {
        logger.fine("AdminController: deleteUser, user_id = " + userId);
        userService.deleteById(userId);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete-all")
    public String deleteAllUsers() {
        logger.fine("AdminController: deleteAllUsers");
        userService.deleteAll();
        return "redirect:/admin";
    }


    @PostMapping(value = "/generate")
    public String generateTestData(@RequestParam(name = "user_count") Integer userCount) {
        logger.fine("AdminController: generateTestData");
        userService.generateTestData(userCount);
        return "redirect:/admin";
    }
}
