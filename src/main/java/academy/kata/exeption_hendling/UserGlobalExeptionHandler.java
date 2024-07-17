package academy.kata.exeption_hendling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


// Будет отлавливать и обрабатывать исключения во всех классах.
@ControllerAdvice
public class UserGlobalExeptionHandler {

    // Реагирует на   NoSuchUserExeption
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleExeption (NoSuchUserExeption exeption){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exeption.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    // Реагирует на ЛЮБОЙ   Exeption
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleExeption (Exception exeption){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exeption.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
