package academy.kata.repository;

import academy.kata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> { // <(класс для кот. реализуем), (тип id)>

    // JPA репозиторий НЕ предоставляет этот метод из коробки, до-пропишеем его
    User findByUsername(String username);

}
