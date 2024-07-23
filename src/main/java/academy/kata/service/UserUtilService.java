package academy.kata.service;

import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


// pзадача - по имени пользователя предоставить самого User'a
@Service
public class UserUtilService {

    private final UserRepository userRepository;


    @Autowired
    public UserUtilService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // метод получает из коллекции ролей коллекцию прав доступа ((с точно такими-же строками.
    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles) {
        return roles.stream().map(
                        role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
