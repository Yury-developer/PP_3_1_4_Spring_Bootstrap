package academy.kata.dto;

import academy.kata.model.Role;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDto {

    private Long id;

    private String name;

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDto(Role role) {
        this(role.getId(), role.getName());
    }

}

