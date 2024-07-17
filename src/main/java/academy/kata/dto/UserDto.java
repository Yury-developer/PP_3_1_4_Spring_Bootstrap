package academy.kata.dto;

import academy.kata.model.Role;
import academy.kata.model.User;
import lombok.*;

import java.sql.Date;
import java.util.Set;


@NoArgsConstructor
//@AllArgsConstructor
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

//    public UserDto(User user) {
//        this(user.getId(), user.getName(), user.getPassword(), user.getFullName(), user.getDateBirth(), user.getAddress(), user.getEmail(), user.getRoles());
//    }
}
