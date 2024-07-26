package academy.kata.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins( // Разрешает запросы только с этих источников
                        "http://localhost:5500", // VS-studio LiveServer
                        "http://127.0.0.1:5500", // VS-studio LiveServer
                        "http://127.0.0.1:63343", // JetBrains WebStorm
                        "http://localhost:63343") // JetBrains WebStorm
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
