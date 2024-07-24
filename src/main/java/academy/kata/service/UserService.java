package academy.kata.service;

import academy.kata.model.Role;
import academy.kata.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    void saveUser(User user);

    Optional<User> findById(Long id);

    User findByUsername(String userName);   //

    List<User> findUsersByRole(Role role);

    List<User> findAll();

    void updateUser(User user, List<Long> selectedRoles);

    void deleteById(Long id);

    void deleteAll();

    User[] generateNewUsers(int count);

    void generateTestData(Integer count);
}
