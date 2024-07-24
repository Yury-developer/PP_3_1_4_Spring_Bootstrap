package academy.kata.repository;

import academy.kata.model.Role;
import academy.kata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> { // <(класс для кот. реализуем), (тип id)>

    // JPA репозиторий НЕ предоставляет этот метод из коробки, до-пропишеем его
    @Query("Select u from User u left join fetch u.roles where u.name=:username") // Без этого отказывалось работать FetchType.LAZY в классе User, (type=Internal Server Error, status=500).
    User findByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles r WHERE :role MEMBER OF u.roles")
    List<User> findUsersByRole(Role role);
}
