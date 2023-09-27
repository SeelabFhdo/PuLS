package de.fhdo.puls.user_management_service.common.commands;

import java.util.List;

public class CreateUserCommand {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private List<String> roles;

    public CreateUserCommand() {
        // NOOP
    }

    public CreateUserCommand(String email, String firstname, String lastname, String password,
        List<String> roles) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
