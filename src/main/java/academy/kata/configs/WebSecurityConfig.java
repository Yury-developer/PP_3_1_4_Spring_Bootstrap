package academy.kata.configs;

import academy.kata.service.UserUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

import javax.sql.DataSource;


@Configuration // можно не указывать, т.к. по цепочке достанется
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private UserUtilService userUtilService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserUtilService userUtilService) {
        this.successUserHandler = successUserHandler;
        this.userUtilService = userUtilService;
    }

//    // это было в базовом конфиге  взадании
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/index").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(successUserHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // настроим фauthorize reqwests
                .antMatchers("/users/**").authenticated() // если пойдем в сторону "/users/**" то пустит только авторизированных пользователей.
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // в админку пускаем только админов
                .antMatchers("/profile/**").authenticated() // в страницу профиля могут заходить только аутенцированные пользователи, у кого есть учетка

                .and()
//                .httpBasic() // cстандартная уунтефикация
                .formLogin() // для авторизации будет наша красивая сверстанная форма/ kлибо по умолччанию как в нашем случае.
//                .loginProcessingUrl("/hellologin") // логиниться будем по этому URL
                .successHandler(successUserHandler) // после залогинивания перекинет на successUserHandler

                .and()
                .logout().logoutSuccessUrl("/"); // при дlogout будет вести на корневую страницу нашего приложения.

//                .antMatchers("/", "/index").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(successUserHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
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
}
