package academy.kata.configs;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/* =Временный класс для задания: "Практическая задача 3.1.5 Rest controllers."
Ошибка CORS (Cross-Origin Resource Sharing) возникает, когда браузер блокирует запрос из-за отсутствия соответствующих заголовков на сервере.
Для разрешения этой проблемы, нужно настроить ваш сервер на localhost:8080 для поддержки CORS.
Для глобальной настройки CORS вводим данный класс конфигурации, в котором добавляю следующие методы для настройки CORS:
(Второй проверенный и рабочий вариант - добавить над методами контроллера '@CrossOrigin(origins = "http://localhost:63343")'
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:63343")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
