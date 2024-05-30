package academy.kata.service;

import academy.kata.model.User;

import java.util.List;


public interface UserService {

    void saveUser(User user);

    User findById(Long id);

    User findByUsername(String nsername);   //

    List<User> findAll();

    void updateUser(User user);

    void deleteById(Long id);

    void deleteAll();


    User[] generateNewUsers(int count);

    void generateTestData(Integer count);
}
