package academy.kata.service;

import academy.kata.model.Role;

import java.util.List;


public interface RoleService {

    Role findByName(String name);

    List<Role> findAll();

}
