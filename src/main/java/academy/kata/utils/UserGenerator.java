package academy.kata.utils;

import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import com.ibm.icu.text.Transliterator;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;



@Component
public class UserGenerator {

    private static final String DEFAULT_PASSWORD = "$2a$10$XtQgyP.gzuElotzb/lZDtORvqquxzcuZvifWc8NsCOFR6Lml1zpQS"; // defaultPassword = "1"

    private final RoleService roleService;


    @Autowired
    public UserGenerator(RoleService roleService) {
        this.roleService = roleService;
    }


    public User[] generateUsers(int count) {
        Faker faker = new Faker(new Locale("ru"));
        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");   // Создание транслитератора
        User[] users = new User[count];
        final Role role = roleService.findByName("ROLE_USER");

        final LocalDate startDate = LocalDate.of(1970, 1, 1);
        final LocalDate endDate = LocalDate.of(2024, 05, 31);

        for (int i = 0; i < users.length; i++) {
            String name = faker.name().fullName();
            Date dateBirth = Date.valueOf(generateRandomDate(startDate, endDate));

            Scanner scanner = new Scanner(name);
            String login = transliterator.transliterate(scanner.next()
                            + faker.number().numberBetween(1, 10)
                            + scanner.next() + "."
                            + dateBirth.getYear()).toLowerCase();
            String address = faker.address().fullAddress();
            users[i] = new User(login, DEFAULT_PASSWORD, name, dateBirth, address);

            Set<Role> roles = new HashSet<>();
            roles.add(role);
            users[i].setRoles(roles);
        }
        return users;
    }

    private static LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
}
// страница генерации хэш/паролей:   https://www.browserling.com/tools/bcrypt