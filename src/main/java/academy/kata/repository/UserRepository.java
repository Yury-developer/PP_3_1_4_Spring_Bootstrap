package academy.kata.repository;

import academy.kata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> { // <(класс для кот. реализуем), (тип id)>
    //
}
