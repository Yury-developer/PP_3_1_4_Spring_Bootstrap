package academy.kata.controller;

import academy.kata.security.UserDetailsImpl;
import academy.kata.service.UserService;
import academy.kata.utils.Utils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    private static Logger logger;

    static {
        try {
            Resource resource = new ClassPathResource("userController_loggerConfig.properties");
            InputStream ins = resource.getInputStream();
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(UserController.class.getName());
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
            logger = null;
        }
    }


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String userPage(Model model, Principal principal) {
        logger.fine("UserController: userPage");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        UserDetailsImpl currentUser = Utils.userToUserDetails(userService.findByUsername(currentUserName));
        model.addAttribute("info_user", currentUser);
        return "user/user";
    }
}
