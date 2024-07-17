package academy.kata.utils;

import academy.kata.dto.RoleDto;
import academy.kata.dto.UserDto;
import academy.kata.model.Role;
import academy.kata.model.User;
import academy.kata.service.RoleService;
import academy.kata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Component
public class ModelTransfer {

    private final UserService userService;
    private final RoleService roleService;



    @Autowired
    public ModelTransfer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }



    public Role toRole(RoleDto roleDto) {
        return roleService.findById(roleDto.getId());
    }

    public  Set<Role> toRolesSet(Set<RoleDto> roleDtoSet) {
        Set<Role> roleSet = roleDtoSet.stream().map(roleDto -> toRole(roleDto)).collect(Collectors.toSet());
        return roleSet;
    }


    public  RoleDto toRoleDto(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }

    public  Set<RoleDto> toRoleDtosSet(Set<Role> roleSet) {
        Set<RoleDto> roleDtoSet = roleSet.stream().map(role -> toRoleDto(role)).collect(Collectors.toSet());
        return roleDtoSet;
    }


    public User toUser(UserDto userDto) {
        User user = new User(
                userDto.getName(),
                userDto.getPassword(),
                toRolesSet(userDto.getRoles()),
                userDto.getFullName(),
                userDto.getDateBirth(),
                userDto.getAddress(),
                userDto.getEmail());
        return user;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getFullName(),
                user.getDateBirth(),
                user.getAddress(),
                user.getEmail(),
                toRoleDtosSet(user.getRoles()));
        return userDto;
    }

    public List<UserDto> toUserDtosList(List<User> userList) {
        List<UserDto> userDtoList = userList.stream().map(user -> toUserDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

}
