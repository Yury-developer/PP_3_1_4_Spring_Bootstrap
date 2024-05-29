package academy.kata.utils;

import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.repository.RoleRepository;
import com.ibm.icu.text.Transliterator;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class UserGenerator {

    private static final String DEFAULT_PASSWORD = "$2a$10$XtQgyP.gzuElotzb/lZDtORvqquxzcuZvifWc8NsCOFR6Lml1zpQS"; // defaultPassword = "1"




    public static User getDefaultUser() {

        String login = "userLogin";
        String name = "userName";
        LocalDate localDate = LocalDate.now();   // Получаем текущую дату как LocalDate
        Date sqlDate = Date.valueOf(localDate);   // Преобразуем LocalDate в java.sql.Date
        String address = "userAddress";
//        Role role = ro
        User user =  new User(login, DEFAULT_PASSWORD, name, sqlDate, address);
//        User user = generateUsers(1)[0];

        return user;
    }

    public static User[] generateUsers(int count) {
        Faker faker = new Faker(new Locale("ru"));
        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");   // Создание транслитератора
        User[] users = new User[count];

        LocalDate startDate = LocalDate.of(1974, 05, 31);
        LocalDate endDate = LocalDate.of(2024, 05, 31);

        LocalDate randomDate = generateRandomDate(startDate, endDate);


        for (int i = 0; i < users.length; i++) {
            String name = faker.name().fullName();
            Date dateBirth = Date.valueOf(generateRandomDate(startDate, endDate));
//            Date dateBirth = new Date(faker.date().birthday(18, 63).getDate());
            System.out.println(dateBirth);

            Scanner scanner = new Scanner(name);
            String login = transliterator.transliterate(scanner.next()
                            + faker.number().numberBetween(1, 10)
                            + scanner.next() + "."
                            + dateBirth.getYear()).toLowerCase();
            String address = faker.address().fullAddress();
//            Collection<Role> roles = new HashSet<>();
//            roles.add(USER);
            users[i] = new User(login, DEFAULT_PASSWORD, name, dateBirth, address);
//            users[i] = new User(login, DEFAULT_PASSWORD, roles, name, dateBirth, address);
//            roleList.add(new Role(0L, "USER"));
//            users[i].setRoleList(roleList);
        }
        return users;
    }

    public static LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        return LocalDate.ofEpochDay(randomEpochDay);
    }
}
// страница генерации хэш/паролей:   https://www.browserling.com/tools/bcrypt