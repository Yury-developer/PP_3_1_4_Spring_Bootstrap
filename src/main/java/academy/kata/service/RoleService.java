package academy.kata.service;

import academy.kata.model.Role;

import java.util.List;


public interface RoleService {

    Role findByName(String name);

    Role findById(Long id);

    List<Role> findAll();

    void addRole(Role role);

    void deleteAll();
}
