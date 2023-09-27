package de.fhdo.puls.user_management_service.common.events;

import java.util.Date;
import java.util.List;

public class UserCreatedEvent {
    private long id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;
    private UserStatus currentStatus;
    private List<String> roles;

    public UserCreatedEvent() {
        // NOOP
    }

    public UserCreatedEvent(long id, String email, String firstname, String lastname,
        String password, Date creationDate, Date lastModifiedDate, UserStatus currentStatus,
        List<String> roles) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.createdDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.currentStatus = currentStatus;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(UserStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
