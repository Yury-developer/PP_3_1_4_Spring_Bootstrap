package academy.kata.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // при обращении 'http://localhost:8080/user' перебросит сразу на 'user.html' минуя контроллер
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user"); // переделать!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
}
