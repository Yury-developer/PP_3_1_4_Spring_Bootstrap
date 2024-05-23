package academy.kata.controller;

import academy.kata.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private final UserService userService;

    public DefaultController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage() {
        return "user";
    }
}
