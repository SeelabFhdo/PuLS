package de.fhdo.puls.user_management_service.command.domain.user;

public class UserAlreadyExistsException extends Exception {
    private String email;

    public UserAlreadyExistsException(String email) {
        super("User with e-mail address " + email + " already exists");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}