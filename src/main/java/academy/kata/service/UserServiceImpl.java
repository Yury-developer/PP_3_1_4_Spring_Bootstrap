package academy.kata.service;

import academy.kata.constants.Constants;
import academy.kata.model.Role;
import academy.kata.repository.*;
import academy.kata.model.User;
import academy.kata.utils.UserGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


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

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<User> findUsersByRole(Role role) {
        return userRepository.findUsersByRole(role);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUser(User user, List<Long> selectedRoles) {

        Set<Role> roleSet = selectedRoles.stream()
                .map(roleService::findById)
                .collect(Collectors.toSet());

        user.setRoles(roleSet);

        /*
         Если пароль введен не был - то используем пароль от существующего пользователя (старый)
         Если введен новый пароль - то используем его (новый)
         */
        String encryptedPassword;
        if (user.getPassword().isEmpty()) {
            encryptedPassword = userRepository.getById(user.getId()).getPassword();
        } else {
            encryptedPassword = passwordEncoder.encode(user.getPassword());
        }
        user.setPassword(encryptedPassword);

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
            users[0].setName("userLogin");
            users[0].setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD.get()));
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
    public void generateTestData(Integer count) {
        userRepository.saveAll(Arrays.asList(generateNewUsers(count)));
    }
}
