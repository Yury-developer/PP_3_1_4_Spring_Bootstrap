package academy.kata.security;

import academy.kata.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.sql.Date;
import java.util.stream.Collectors;


@NoArgsConstructor
@Component
public class UserDetailsImpl extends User implements UserDetails {

    public Long geiId() {
        return super.getId();
    }
    public String getName() {
        return super.getName();
    }
    public String getFullName() {
        return super.getFullName();
    }
    public Date getDateBirth() {
        return super.getDateBirth();
    }
    public String getAddress() {
        return super.getAddress();
    }
    public String getEmail() {
        return super.getEmail();
    }


    // Возвращает коллекцию прав (или ролей), предоставленных пользователю. Возвращаемый тип — Collection<? extends GrantedAuthority>.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // Возвращает имя пользователя, используемое для аутентификации.
    @Override
    public String getUsername() {
        return super.getName();
    } // Возможно просто null вернуть нужно/ правильно будет

    //  Возвращает пароль пользователя.
    @Override
    public String getPassword() {
        return super.getPassword();
    } // Возможно просто null вернуть нужно/ правильно будет

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
