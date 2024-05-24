package academy.kata.utils;

import academy.kata.model.Role;
import academy.kata.model.User;
import net.datafaker.Faker;

import java.sql.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class UserFaker {

    public static User[] generateUsers(int count) {
        Faker faker = new Faker(new Locale("ru"));
        User[] users = new User[count];

        for (int i = 0; i < users.length; i++) {
            String name = faker.name().fullName();
            Date dateBirth = new Date(faker.date().birthday(18, 77).getDate());
            String address = faker.address().fullAddress();
            users[i] = new User(name, dateBirth, address);
            Set<Role> roleList = new HashSet<Role>();
            roleList.add(new Role(0L, "USER"));
            users[i].setRoleList(roleList);
        }
        return users;
    }

}
