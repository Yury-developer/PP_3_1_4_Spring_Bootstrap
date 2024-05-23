package academy.kata.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration // можно не указывать, т.к. по цепочке достанется
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
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






    // аутентификация inMemory
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}