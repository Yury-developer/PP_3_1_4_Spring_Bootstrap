package academy.kata.controller;

import academy.kata.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private final UserService userService;

    public DefaultController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String homePage() {
        /*
        Как узнать залогиненый пользователь зашел или нет? Ложим в параметры 'Principal', потом проверяем на null ...
            public String homePage(Principal principal) {
                if (principal != null) {
                System.out.println(((Authentication)principal).getAuthorities());
            }
         */
        return "index";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage() {
        return "user/user";
    }
}
