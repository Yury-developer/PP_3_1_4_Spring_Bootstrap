package academy.kata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;


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

    @Column(name = "user_name", length = 50, nullable = false) // login
    private String name;

//    @JsonIgnore // Поле игнорируется при сериализации
    @Column(name = "user_password", length = 128, nullable = false) // password
    private String password;

    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Column(name = "date_birth")
    private Date dateBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) //* при LAZY выдает ошибку "org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: academy.kata.model.User.roles, could not initialize proxy - no Session ..."
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")) // указываем внешний ключ в таблице ролей
    private Set<Role> roles;


    public User(String login, String password, Set<Role> roles, String fullName, Date dateBirth, String address, String email) {
        this(login, password, fullName, dateBirth, address, email);
        this.roles = roles;
        this.email = email;
    }

    public User(String login, String password, String fullName, Date dateBirth, String address, String email) {
        this.name = login;
        this.password = password;
        this.fullName = fullName;
        this.dateBirth = dateBirth;
        this.address = address;
        this.email = email;
    }

    public User(User user) {
        this(
                new String(user.name),
                new String(user.password),
                new String(user.fullName),
                new Date(user.dateBirth.getTime()),
                new String(user.address),
                new String(user.email)
        );
        this.id = user.id;
        this.roles = user.roles.stream()
                .map(role -> new Role(role.getId(), new String(role.getName())))
                .collect(Collectors.toSet());
    }


    @Override
    public String toString() {
        return "User{"  + "\n\t" +
                "id = " + id + " ,\n\t" +
                " userName = '" + name + " ,\n\t" +
                " password = '" + password + " ,\n\t" +
                " fullName = '" + fullName + " ,\n\t" +
                " dateBirth = " + dateBirth + " ,\n\t" +
                " address = '" + address + " ,\n\t" +
                " email = " + email + " ,\n\t" +
                " roles = " + roles + ":\n\t" +
                '}';
    }
}
