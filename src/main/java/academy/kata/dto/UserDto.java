package academy.kata.dto;

import lombok.*;

import java.sql.Date;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private Long id;

    private String name;

    private String password;

    private String fullName;

    private Date dateBirth;

    private String address;

    private String email;

    private Set<RoleDto> roles;


    public UserDto(Long id, String name, String password, String fullName, Date dateBirth, String address, String email, Set<RoleDto> rolesDto) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.fullName = fullName;
        this.dateBirth = dateBirth;
        this.address = address;
        this.email = email;
        this.roles = rolesDto;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                " id = " + id + ",\n" +
                " name = '" + name + "',\n" +
                " password = '" + password + "',\n" +
                " fullName = '" + fullName + "',\n" +
                " dateBirth = " + dateBirth + ",\n" +
                " address = '" + address + "',\n" +
                " email = '" + email + "',\n" +
                " roles = " + roles +
                '}';
    }
}
