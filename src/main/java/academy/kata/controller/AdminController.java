package academy.kata.controller;

import academy.kata.model.User;
import academy.kata.service.UserService;
import academy.kata.utils.UserGenerator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * @Author: Yury Lapitski
 * 2024-05-29
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    private static Logger LOGGER;

    static {
        try {
            Resource resource = new ClassPathResource("adminController_loggerConfig.properties");
            InputStream ins = resource.getInputStream();
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(AdminController.class.getName());
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER = null;
        }
    }


    public AdminController(UserService userService) {
        this.userService = userService;
    }



    // просто возвращает страницу приветствия (с нее можно сгенерировать тестовые данные во все таблицы)
    @RequestMapping(method = RequestMethod.HEAD)
    public String ping() {
        LOGGER.fine("AdminController: ping");
        return "redirect:/";
    }



    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        LOGGER.fine("AdminController: showCreateUserForm");
        model.addAttribute("createdUser", UserGenerator.getDefaultUser());
        return "admin/create-user";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("createdUser") User user) {
        LOGGER.fine("AdminController: addUser, user = " + user);
        userService.saveUser(user);
        return "redirect:/admin";
    }



    @GetMapping("/view")
    public String showUserDetailsForm(@RequestParam(defaultValue = "0", required = false, name = "user_id") Long userId,
                                      Model model) {
        LOGGER.fine("AdminController: showUserDetailsForm, user_id = " + userId);
        User user = userService.findById(userId);
        model.addAttribute("viewUser", user);
        return "admin/view-user";
    }



    @GetMapping("")
    public String showAllUsersForm(Model model) {
        LOGGER.fine("AdminController: showAllUsersForm");
        List<User> userList = userService.findAll();
        model.addAttribute("viewAllUsers", userList);
        return "admin/all-users";
    }



    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam(name = "user_id") Long userId,
                                   Model model) {
        LOGGER.fine("AdminController: showEditUserForm, user_id = " + userId);
        User user = userService.findById(userId);
        model.addAttribute("editUser", user);
        return "admin/edit-user";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(name = "id") Long userId,
                           @ModelAttribute("editUser") User user) {
        LOGGER.fine("AdminController: editUser, user_id = " + userId + "\n user = " + user);
        userService.updateUser(user);
        return "redirect:/admin";
    }



    @GetMapping("/delete")
    public String deleteUser(@RequestParam(name = "user_id") Long userId) {
        LOGGER.fine("AdminController: deleteUser, user_id = " + userId);
        userService.deleteById(userId);
        return "redirect:/admin";
    }

    @PostMapping("/delete-all")
    public String deleteAllUsers() {
        LOGGER.fine("AdminController: deleteAllUsers");
        userService.deleteAll();
        return "redirect:/admin";
    }
}
