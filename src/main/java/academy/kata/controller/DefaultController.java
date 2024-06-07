package academy.kata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/")
public class DefaultController {

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
