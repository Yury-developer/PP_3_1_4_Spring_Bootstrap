package academy.kata.exeption_hendling;


public class NoSuchUserExeption extends RuntimeException {

    public NoSuchUserExeption(String message) {
        super(message);
    }
}
