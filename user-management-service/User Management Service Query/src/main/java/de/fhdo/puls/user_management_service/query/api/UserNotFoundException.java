package de.fhdo.puls.user_management_service.query.api;

public class UserNotFoundException extends Exception {
    private String email;

    public UserNotFoundException(String email) {
        super("User with e-mail address " + email + " not found");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}