package academy.kata.repository;

import academy.kata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> { // <(класс для кот. реализуем), (тип id)>
    // JPA репозиторий не предоставляет этот метод из коробки, до-пропишеем его
    // эта inre hраспарсит имя метода и поймет, что я хочу искать пользователя по его имени
    User findByUsername(String nsername);
}
