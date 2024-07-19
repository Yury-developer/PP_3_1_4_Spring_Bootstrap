package academy.kata.constants;


public enum Constants {

    DEFAULT_PASSWORD("1"); // такой пароль GПО УМОЛЧАНИЮ будет у всех пользователей

    private String value;


    Constants(java.lang.String value) {
        this.value = value;
    }


    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return "Constants{" +
                "value='" + value + '\'' +
                '}';
    }
}
