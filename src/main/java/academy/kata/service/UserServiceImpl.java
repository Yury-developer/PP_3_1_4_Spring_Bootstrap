package academy.kata.service;

import academy.kata.repository.*;
import academy.kata.model.User;
import academy.kata.utils.UserFactory;
import academy.kata.utils.UserFaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        List<User> userList = findAll();
        userRepository.deleteAll();
    }



    @Override
    @Transactional
    public void generateTestData(Integer count) {
        userRepository.saveAll(Arrays.asList(UserFactory.generateUsers(count)));
//        userRepository.saveAll(Arrays.asList(UserFaker.generateUsers(count)));
    }
}
