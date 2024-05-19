package academy.kata.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "users") // (name = "users", schema = "your_schema", catalog = "your_catalog", indexes = {}, uniqueConstraints = {}, options = "ENGINE=MyISAM")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false) // поле не может быть обновлено при выполнении операции обновления (UPDATE) в базе данных; не может содержать значение NULL
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "dateBirth")
    private Date dateBirth;

    @Column(name = "address")
    private String address;


    public User(String name, Date dateBirth, String address) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateBirth=" + dateBirth +
                ", address='" + address + '\'' +
                '}';
    }
}
