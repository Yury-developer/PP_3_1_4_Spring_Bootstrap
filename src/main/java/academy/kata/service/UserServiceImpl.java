package academy.kata.service;

import academy.kata.constants.Constants;
import academy.kata.model.Role;
import academy.kata.repository.*;
import academy.kata.model.User;
import academy.kata.utils.UserGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Service
public class UserServiceImpl implements UserService, Constants {


    private final UserRepository userRepository;
    private final UserGenerator userGenerator;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,
                           UserGenerator userGenerator,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userGenerator = userGenerator;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.getById(id);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }



    @Override
    public User[] generateNewUsers(int count) {
        User[] users;
        if (count == 0) {
            users = userGenerator.generateUsers(1);
            users[0].setUsername("userLogin");
            users[0].setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            users[0].setFullName("userName");
            users[0].setAddress("userAddress");
            Set<Role> roles = new HashSet<>(roleService.findAll());
            users[0].setRoles(roles);
        } else {
            users = userGenerator.generateUsers(count);
        }
        return users;
    }


    @Override
    @Transactional
    public void generateTestData(Integer count) {
        userRepository.saveAll(Arrays.asList(generateNewUsers(count)));
    }
}
