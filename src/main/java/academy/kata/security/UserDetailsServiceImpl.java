package academy.kata.security;

import academy.kata.model.User;
import academy.kata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // перегоним наших User'ов в User'ов которые понимает SpringSequrity
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getName(), // login
                        user.getPassword(), // password
                        user.getRoles().stream().map(
                                role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()));
        return userDetails;
    }
}
