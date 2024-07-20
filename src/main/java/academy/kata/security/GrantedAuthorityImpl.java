package academy.kata.security;

import academy.kata.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


@Component
public class GrantedAuthorityImpl implements GrantedAuthority {

    private Role role;

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
