package academy.kata.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;


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

    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "dateBirth")
    private Date dateBirth;

    @Column(name = "address")
    private String address;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id") // указываем внешний ключ в таблице ролей
    @ManyToMany
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")) // указываем внешний ключ в таблице ролей
    private Collection<Role> roles;


    public User(String login, String name, Date dateBirth, String address) {
        this.login = login;
        this.name = name;
        this.dateBirth = dateBirth;
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", dateBirth=" + dateBirth +
                ", address='" + address + '\'' +
                '}';
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
