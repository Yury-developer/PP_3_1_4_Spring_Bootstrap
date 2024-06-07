package academy.kata.configs;

import academy.kata.service.UserUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.sql.DataSource;


@Configuration // можно не указывать, т.к. по цепочке достанется
//@EnableWebSecurity
@EnableWebSecurity(debug = true) // 'debug = true' -в консоль высыпется вс цепочка фильтров   https://www.youtube.com/live/HvovW6Uh1yU?si=C4vl98El9oe2b89-&t=6108
@EnableGlobalMethodSecurity(securedEnabled = true)   // Включаем доп. защиту на уровне методдов
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private UserUtilService userUtilService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserUtilService userUtilService) {
        this.successUserHandler = successUserHandler;
        this.userUtilService = userUtilService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // настроим authorize requests
                .antMatchers("/css/**", "/index").permitAll() // Разрешить доступ к стилям и главной странице

                .antMatchers("/user/**").authenticated() // если пойдем в сторону "/user/**" то пустит только авторизированных пользователей.
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // в админку пускаем только С РОЛЯМИ 'ADMIN' и 'SUPERADMIN'
//                .antMatchers("/admin/**").hasAuthority("ONLY_REED") // а также в админку пустит С ПРАВАМИ 'ONLY_REED' (тут сравнивает один к одному)

                .and()
//                .httpBasic() // стандартная аунтефикация
                .formLogin() // для авторизации будет НАША красивая сверстанная форма/ либо по умолчанию Spring сгенерит, как в нашем случае.
//                .loginProcessingUrl("/hellologin") // логиниться будем по этому альтернативному URL
                .permitAll()  // Разрешение доступа к странице логина для всех
                .successHandler(successUserHandler) // после залогинивания перекинет на successUserHandler

                .and()
                .logout().logoutSuccessUrl("/") // при logout будет вести на корневую страницу нашего приложения.
                .permitAll();
    }





//    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder() // минимальная информация о пользователе
//                .username("user")
////                .password("{bcrypt}$2a$10$4sqSaa4di9Y511hsKxoe3u9Ri85EQOxqbwfFV0RAAAnxUMKYll542") // password=user
//                .password("{bcrypt}$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC") // password=u
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder() // минимальная информация о пользователе
//                .username("admin")
////                .password("{bcrypt}$2a$10$yRrs63ZIWDxNIB6LmmwVu.h243nBteNei2Gs4Llt7bymuJZQOoRYG") // password=admin
//                .password("{bcrypt}$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK") // password=a
//                .roles("ADMIN")
//                .build();
//
//// Так было  в базовом варианте, пароли хранились в незашифрованном виде
////        UserDetails user =
////                User.withDefaultPasswordEncoder()
////                        .username("user")
////                        .password("u")
////                        .roles("USER")
////                        .build();
////
////        UserDetails admin =
////                User.withDefaultPasswordEncoder()
////                        .username("admin")
////                        .password("a")
////                        .roles("ADMIN")
////                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }





//    // аутентификация jdbcAuthenticator
//    @Bean
//     // DaoAuthenticationProvider является и UserDetails (знает как из стандартных таблиц дергнуть User'ов и AuthenticationProvider, т.к. он умее делать сверку.
//    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) { // при старте будем засовывать в базу этих пользователей.
//        UserDetails user = User.builder() // минимальная информация о пользователе
//                .username("user")
////                .password("{bcrypt}$2a$10$4sqSaa4di9Y511hsKxoe3u9Ri85EQOxqbwfFV0RAAAnxUMKYll542") // password=user
//                .password("{bcrypt}$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC") // password=u
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder() // минимальная информация о пользователе
//                .username("admin")
////                .password("{bcrypt}$2a$10$yRrs63ZIWDxNIB6LmmwVu.h243nBteNei2Gs4Llt7bymuJZQOoRYG") // password=admin
//                .password("{bcrypt}$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK") // password=a
//                .roles("ADMIN")
//                .build();
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//
//        if (manager.userExists(user.getUsername())) {
//            manager.deleteUser(user.getUsername());
//        }
//        if (manager.userExists(admin.getUsername())) {
//            manager.deleteUser(admin.getUsername());
//        }
//
//        manager.createUser(user);
//        manager.createUser(admin);
//
//        return manager;
//    }





    // для преобразования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    // аутентификация jdbcAuthenticator
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userUtilService);
        return authenticationProvider;
    }


    /*
    Spring Boot автоматически добавляет HiddenHttpMethodFilter, который позволяет использовать скрытые методы.
    Если фильтр по какой-то причине не активен, его можно добавить вручную.
    В моем случае это именно так и случилось! Убираю этот бин - ошибка 405.
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }



    /*
     Задача AuthenticationProvider у UserDetailsService запросить User'а взять token и сравнить совпадают ли эти данные. Совпадают -кладем в контекст.
     UserDetailsService -источник User'ов
     AuthenticationProvider -сверщик
     */
}
