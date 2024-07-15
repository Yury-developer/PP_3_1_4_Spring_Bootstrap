package academy.kata.exeption_hendling;

public class UserIncorrectData {

    private String info;

    public UserIncorrectData() {
        // NOP
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UserIncorrectData{" +
                "info='" + info + '\'' +
                '}';
    }
}
