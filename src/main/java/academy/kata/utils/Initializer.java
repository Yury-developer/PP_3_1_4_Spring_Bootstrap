package academy.kata.utils;

import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import academy.kata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Configuration
public class Initializer {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public Initializer(UserService userService,
                       RoleService roleService,
                       PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreDestroy
    private void destroy() {
        userService.deleteAll();
        roleService.deleteAll();
    }

    @PostConstruct
    private void init() {
        Role userRole = new Role(1L, "ROLE_USER"); // самый низкий приоритет
        roleService.addRole(userRole);

        Role adminRole = new Role(2L, "ROLE_ADMIN");
        roleService.addRole(adminRole);

        Role superAdminRole = new Role(3L, "ROLE_SUPERADMIN"); // самый высокий приоритет
        roleService.addRole(superAdminRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findByName(userRole.getName()));
        User userUsr = new User(
                "user",
                passwordEncoder.encode("u"), // "$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC"
                userRoles,
                "my name is User",
                new java.sql.Date(100, 01, 21),
                "User Address",
                "user@mail.ru"
        );
        userUsr.setId(1L);
        userService.saveUser(userUsr);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleService.findByName(userRole.getName()));
        adminRoles.add(roleService.findByName(adminRole.getName()));
        User adminUsr = new User(
                "admin",
                passwordEncoder.encode("a"), // "$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK",
                adminRoles,
                "my name is Admin",
                new Date(90, 02, 22),
                "Admin Address",
                "admin@ya.ru"
        );
        adminUsr.setId(2L);
        userService.saveUser(adminUsr);

        Set<Role> superAdminRoles = new HashSet<>();
        superAdminRoles.add(roleService.findByName(userRole.getName()));
        superAdminRoles.add(roleService.findByName(adminRole.getName()));
        superAdminRoles.add(roleService.findByName(superAdminRole.getName()));
        User superAdminUsr = new User(
                "superadmin",
                passwordEncoder.encode("s"), // "$2a$10$BpTDSlU4gBqocZFn7/t7BuqoFww6wBaqMO5Tot7bvzarZDJJ9xwM2"
                superAdminRoles,
                "my name is SUPER-Admin",
                new Date(80, 03, 23),
                "SUPER-Admin Address",
                "superAdmin@gmail.com"
        );
        superAdminUsr.setId(3L);
        userService.saveUser(superAdminUsr);


        // *** *** ***   Дополнительные пользователи   *** *** ***
        Set<Role> user2Roles = new HashSet<>();
        user2Roles.add(roleService.findByName(userRole.getName()));
        User user2Usr = new User(
                "user2",
                passwordEncoder.encode("u"), // "$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC"
                user2Roles,
                "my name is User2",
                new java.sql.Date(101, 11, 21),
                "User2 Address",
                "user2@mail.ru"
        );
        user2Usr.setId(4L);
        userService.saveUser(user2Usr);

        Set<Role> admin2Roles = new HashSet<>();
        admin2Roles.add(roleService.findByName(adminRole.getName()));
        User admin2Usr = new User(
                "admin2",
                passwordEncoder.encode("a"), // "$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK",
                admin2Roles,
                "my name is Admin2",
                new Date(93, 02, 25),
                "Admin2 Address",
                "admin2@ya.ru"
        );
        admin2Usr.setId(5L);
        userService.saveUser(admin2Usr);

        Set<Role> superAdmin2Roles = new HashSet<>();
        superAdmin2Roles.add(roleService.findByName(userRole.getName()));
        superAdmin2Roles.add(roleService.findByName(superAdminRole.getName()));
        User superAdmin2Usr = new User(
                "superadmin2",
                passwordEncoder.encode("s"), // "$2a$10$BpTDSlU4gBqocZFn7/t7BuqoFww6wBaqMO5Tot7bvzarZDJJ9xwM2"
                superAdmin2Roles,
                "my name is SUPER-Admin2",
                new Date(70, 03, 23),
                "SUPER-Admin2 Address",
                "superAdmin2@gmail.com"
        );
        superAdmin2Usr.setId(6L);
        userService.saveUser(superAdmin2Usr);
    }
}
