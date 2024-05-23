package academy.kata.utils;

import net.datafaker.Faker;

import java.util.Locale;

/**
 * Hello world!
 *
 */
public class FakerApp
{
    public static void main( String[] args )
    {
        Faker faker = new Faker(new Locale("ru"));

        System.out.println(faker.name().nameWithMiddle());
        System.out.println(faker.address().fullAddress());
        System.out.println(faker.date().birthday(18, 77));
    }
}
