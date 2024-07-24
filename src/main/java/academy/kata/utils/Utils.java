package academy.kata.utils;

import academy.kata.model.User;
import academy.kata.security.UserDetailsImpl;

public class Utils {

    public static UserDetailsImpl userToUserDetails(User currentUser) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(currentUser.getId());
        userDetails.setName(currentUser.getName());
        userDetails.setPassword(currentUser.getPassword());
        userDetails.setRoles(currentUser.getRoles());
        userDetails.setFullName(currentUser.getFullName());
        userDetails.setDateBirth(currentUser.getDateBirth());
        userDetails.setAddress(currentUser.getAddress());
        userDetails.setEmail(currentUser.getEmail());
        return userDetails;
    }
}
