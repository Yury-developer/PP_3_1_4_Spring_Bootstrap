package academy.kata.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


//@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


//    @PostConstruct
    private void init() {
        String sqlSrc = "PP_3_1_4_spring-bootstrap_FullTablesScrypt.sql";
//        String sqlSrc = "PP_3_1_4_spring-bootstrap_FullTablesScrypt_SQLs.sql";
        try {
            executeSqlScript(sqlSrc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void executeSqlScript(String scriptPath) throws IOException {
        InputStream resource = new ClassPathResource(scriptPath).getInputStream();
        String sql = new BufferedReader(new InputStreamReader(resource))
                .lines()
                .collect(Collectors.joining("\n"));

        // Разделение на отдельные SQL-запросы по ';'
        String[] queries = sql.split(";");
        for (String query : queries) {
            if (!query.trim().isEmpty()) {
                System.out.println(query + ";");
                jdbcTemplate.execute(query + ";");
            }
        }
    }
}

