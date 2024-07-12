package academy.kata.controller;

import academy.kata.constants.Constants;
import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import academy.kata.service.RoleServiceImpl;
import academy.kata.service.UserService;
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
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * @Author: Yury Lapitski
 * 2024-06-07
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController implements Constants {

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



    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        logger.fine("AdminController: showCreateUserForm");
        User defaultUser = userService.generateNewUsers(0)[0];
        model.addAttribute("createdUser", defaultUser);
        model.addAttribute("default_password", DEFAULT_PASSWORD);
        return "admin/create-user";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("createdUser") User user) {
        System.out.println("AdminController: createUser. user = " + user);
        System.out.println("user.getUsername() = " + user.getName());
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        logger.fine("AdminController: addUser, user = " + user);
        userService.saveUser(user);
        return "redirect:/admin";
    }



    @GetMapping("/view")
    public String showUserDetailsForm(@RequestParam(defaultValue = "0", required = false, name = "user_id") Long userId,
                                      Model model) {
        logger.fine("AdminController: showUserDetailsForm, user_id = " + userId);
        User user = userService.findById(userId);
        model.addAttribute("viewUser", user);
        return "admin/view-user";
    }



    @GetMapping
    public String showAllUsersForm(Model model) {
        logger.fine("AdminController: showAllUsersForm");
        List<User> userList = userService.findAll();
        model.addAttribute("viewAllUsers", userList);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findByUsername(currentUserName);
        model.addAttribute("current_user", currentUser);

        List<Role> roleList = roleService.findAll();
        model.addAttribute("role_list", roleList);

        User defaultUser = userService.generateNewUsers(0)[0];
        model.addAttribute("createdUser", defaultUser);

        model.addAttribute("currentUser", currentUser);
        return "admin/all-users";
    }



    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam(name = "user_id") Long userId,
                                   Model model) {
        logger.fine("AdminController: showEditUserForm, user_id = " + userId);
        User user = userService.findById(userId);
        model.addAttribute("editUser", user);
        User rolesSource = userService.generateNewUsers(0)[0]; // это нужно просто чтобы вытащить все возможные роли
        model.addAttribute("availableRoles", rolesSource.getRoles());
        return "admin/edit-user";
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
        System.out.println("\n***\n DELETE \n***\n");
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



    // отдает страницу при входе  admin'a
    @GetMapping("/admin")
    public String greetingPage(Model model) {
        logger.fine("AdminController: greetingPage");
        Integer userCountDefault = 15;   // По умолчанию будет предложено создать такое количество пользователей.
        model.addAttribute("greeting", "Hello!");
        model.addAttribute("greetingMessage", "Практическая задача 3.1.3 Java pre-project. Задача 3.1.2. Spring Boot, Security.");
        model.addAttribute("author", "Выполнил: Лапицкий Юрий   //   Performed by: Yury Lapitski");
        model.addAttribute("user_count_default", userCountDefault);
        model.addAttribute("default_password", DEFAULT_PASSWORD);
        return "admin/admin";
    }

    @PostMapping(value = "/generate")
    public String generateTestData(@RequestParam(name = "user_count") Integer userCount) {
        logger.fine("AdminController: generateTestData");
        userService.generateTestData(userCount);
        return "redirect:/admin";
    }
}
