package de.fhdo.puls.security_service.domain.user;

import de.fhdo.puls.user_management_service.common.events.UserStatus;

import java.util.List;

public class UserValueObject {
    String email;
    String firstname;
    String lastname;
    UserStatus currentStatus;
    List<String> userRoles;

    public UserValueObject() {
        // NOOP
    }

    public UserValueObject(String email, String firstname, String lastname,
        UserStatus currentStatus, List<String> userRoles) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.currentStatus = currentStatus;
        this.userRoles = userRoles;
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

    public UserStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(UserStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }
}
