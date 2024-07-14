package academy.kata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthorizationController {

    @GetMapping("/login")
    public String login() {

        System.out.println("\n\n*********************\n\nLOGIN\n\n*****\n\n");
        System.out.println("\n\n********************* *********************\n\n");
        System.out.println("AuthorizationController: login");

        return "login";
    }
}
