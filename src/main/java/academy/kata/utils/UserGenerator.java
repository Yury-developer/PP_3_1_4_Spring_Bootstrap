package academy.kata.utils;

import academy.kata.model.Role;
import academy.kata.model.User;
import com.ibm.icu.text.Transliterator;
import net.datafaker.Faker;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;


public class UserGenerator {


    public static User getDefaultUser() {

        String login = "userLogin";
        String name = "userName";
        LocalDate localDate = LocalDate.now();   // Получаем текущую дату как LocalDate
        Date sqlDate = Date.valueOf(localDate);   // Преобразуем LocalDate в java.sql.Date
        String address = "userAddress";
        User user =  new User(login, name, sqlDate, address);
//        User user = generateUsers(1)[0];

        return user;
    }

    public static User[] generateUsers(int count) {
        Faker faker = new Faker(new Locale("ru"));
        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");   // Создание транслитератора
        User[] users = new User[count];

        for (int i = 0; i < users.length; i++) {
            String name = faker.name().fullName();
            Date dateBirth = new Date(faker.date().birthday(18, 77).getDate());

            Scanner scanner = new Scanner(name);
            String login = transliterator.transliterate(scanner.next()
                            + faker.number().numberBetween(1, 10)
                            + scanner.next() + "."
                            + dateBirth.getYear()).toLowerCase();
            String address = faker.address().fullAddress();
            users[i] = new User(login, name, dateBirth, address);
            Set<Role> roleList = new HashSet<Role>();
//            roleList.add(new Role(0L, "USER"));
//            users[i].setRoleList(roleList);
        }
        return users;
    }

}
