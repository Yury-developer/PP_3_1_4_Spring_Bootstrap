package academy.kata.repository;

import academy.kata.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Optional<Role> findById(Long id);

    List<Role> findAll();

}
