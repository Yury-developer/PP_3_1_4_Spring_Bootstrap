package academy.kata.utils;

import net.datafaker.Faker;
import com.ibm.icu.text.Transliterator;

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

        // Создание транслитератора
        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");


        System.out.println(faker.name().nameWithMiddle());
        System.out.println(faker.address().fullAddress());
        System.out.println(faker.date().birthday(18, 77));

        System.out.println(transliterator.transliterate(faker.name().fullName()));
    }
}
