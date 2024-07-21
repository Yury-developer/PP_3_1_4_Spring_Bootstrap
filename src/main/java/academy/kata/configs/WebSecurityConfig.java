package academy.kata.configs;

import academy.kata.security.UserDetailsImpl;
import academy.kata.security.UserDetailsServiceImpl;
import academy.kata.service.UserUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;


@Configuration // можно не указывать, т.к. по цепочке достанется
@EnableWebSecurity(debug = true) // 'debug = true' -в консоль высыпется вс цепочка фильтров   https://www.youtube.com/live/HvovW6Uh1yU?si=C4vl98El9oe2b89-&t=6108
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserDetailsServiceImpl userUtilService;
//    private final UserUtilService userUtilService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsServiceImpl userUtilService) {
//    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserUtilService userUtilService) {
        this.successUserHandler = successUserHandler;
        this.userUtilService = userUtilService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Отключаем CSRF для тестирования (отключил и заработал REST create...)
                .authorizeRequests() // настроим authorize requests
                .antMatchers("/css/**", "/index").permitAll() // Разрешить доступ к стилям и главной странице

                .antMatchers("/user/**").authenticated() // если пойдем в сторону "/user/**" то пустит только авторизированных пользователей.
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // в админку пускаем только С РОЛЯМИ 'ADMIN' и 'SUPERADMIN'
//                .antMatchers("/admin/**").hasAuthority("ONLY_REED") // а также в админку пустит С ПРАВАМИ 'ONLY_REED' (тут сравнивает один к одному)

//                .antMatchers("/api/**").permitAll()


                .and()
//                .httpBasic() // стандартная аунтефикация
                .formLogin() // для авторизации будет НАША красивая сверстанная форма/ либо по умолчанию Spring сгенерит, как в нашем случае.
//                .loginProcessingUrl("/hellologin") // логиниться будем по этому альтернативному URL
                .permitAll()  // Разрешение доступа к странице логина для всех
                .successHandler(successUserHandler) // после залогинивания перекинет на successUserHandler

                .and()
                .logout()
                .logoutSuccessUrl("/login") // при logout будет вести на корневую страницу нашего приложения.
                .permitAll();
    }



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
     Использую DebugFilter.
     Это позволит увидеть дополнительную информацию в логах, чтобы более точно определить причину проблем.
     После отладки удалю
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(true);
    }

    /*
     Задача AuthenticationProvider у UserDetailsService запросить User'а взять token и сравнить совпадают ли эти данные. Совпадают -кладем в контекст.
     UserDetailsService -источник User'ов
     AuthenticationProvider -сверщик
     */
}
