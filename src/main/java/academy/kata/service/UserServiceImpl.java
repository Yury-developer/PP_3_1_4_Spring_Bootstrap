package academy.kata.service;

import academy.kata.model.Role;
import academy.kata.repository.*;
import academy.kata.model.User;
import academy.kata.utils.UserGenerator;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserGenerator userGenerator;
    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, UserGenerator userGenerator, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userGenerator = userGenerator;
        this.roleRepository = roleRepository;
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
    public User findByUsername(String username) {   //
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
    @Secured("ROLE_ADMIN")
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN") // предварительно включить '@EnableGlobalMethodSecurity' в классе 'WebSecurityConfig' // так можно точечно защитить на уровне методов (если злоумыш_ обошел все-же все защиты)
    public void deleteAll() {
        List<User> userList = findAll();
        userRepository.deleteAll();
    }



    @Override
    @Secured("ROLE_ADMIN")
    public User[] generateNewUsers(int count) {
        User[] users;
        if (count == 0) {
            users = userGenerator.generateUsers(1);
            users[0].setUsername("userLogin");
            users[0].setFullName("userName");
            users[0].setAddress("userAddress");
            Set<Role> roles = new HashSet<>(roleRepository.findAll());

            roles.stream().forEach(i -> System.out.println(i.getName())); ///////////////////////////////////////////////

            users[0].setRoles(roles);
        } else {
            users = userGenerator.generateUsers(count);
        }
        return users;
    }


    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void generateTestData(Integer count) {
        userRepository.saveAll(Arrays.asList(generateNewUsers(count)));
    }
}
