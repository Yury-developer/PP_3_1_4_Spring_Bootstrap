package academy.kata.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users") // (name = "users", schema = "your_schema", catalog = "your_catalog", indexes = {}, uniqueConstraints = {}, options = "ENGINE=MyISAM")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false) // поле не может быть обновлено при выполнении операции обновления (UPDATE) в базе данных; не может содержать значение NULL
    private Long id;

    @Column(name = "username", length = 50, nullable = false) // login
    private String username;

    @Column(name = "password", length = 128, nullable = false) // password
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
        this.username = login;
        this.password = password;
        this.fullName = fullName;
        this.dateBirth = dateBirth;
        this.address = address;
        this.email = email;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateBirth=" + dateBirth +
                ", address='" + address + '\'' +
                ", email=" + email +
                ", roles=" + roles +
                '}';
    }


    // Возвращает коллекцию прав (или ролей), предоставленных пользователю. Возвращаемый тип — Collection<? extends GrantedAuthority>.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // Возвращает имя пользователя, используемое для аутентификации.
    @Override
    public String getUsername() {
        return username;
    }

    //  Возвращает пароль пользователя.
    @Override
    public String getPassword() {
        return password;
    }

    // Указывает, не истёк ли срок действия аккаунта пользователя. Если истёк, то пользователю запрещается аутентификация.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Указывает, не заблокирован ли аккаунт пользователя. Если аккаунт заблокирован, то пользователю запрещается аутентификация.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Указывает, не истёк ли срок действия учетных данных пользователя (пароля). Если истёк, то пользователю запрещается аутентификация.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //  Указывает, включён ли пользователь. Отключённый пользователь не может быть аутентифицирован.
    @Override
    public boolean isEnabled() {
        return true;
    }
}
