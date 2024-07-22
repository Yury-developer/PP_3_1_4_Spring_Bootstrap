package academy.kata.utils;

import academy.kata.model.Role;
import academy.kata.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class RoleUtils {

    public static boolean hasRole(User user, Role role) {
        return user.getRoles().stream().anyMatch(r -> r.getId() == role.getId());
    }


    public static Role getRoleWithMaxPriority(List<Role> roles) {
        return getRoleWithMaxPriority(roles.stream().collect(Collectors.toSet()));
    }

    public static Role getRoleWithMaxPriority(Set<Role> roles) {
        return roles.stream().max(Comparator.comparing(Role::getId)).get();
    }
}

