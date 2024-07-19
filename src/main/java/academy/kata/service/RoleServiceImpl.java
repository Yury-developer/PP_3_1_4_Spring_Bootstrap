package academy.kata.service;

import academy.kata.model.Role;
import academy.kata.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get(); // NoSuchElementException, если по данному id отсутствует Role.
    }

    @Override
    public List<Role> findAll() {
        List<Role> roleList = roleRepository.findAll();
        roleList.sort(Comparator.comparing(Role::getName)); // отсортируем для красивого вывода в браузере
        return roleList;
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }
}
