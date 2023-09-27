package de.fhdo.puls.user_management_service.command.domain.user;

import de.fhdo.puls.user_management_service.command.domain.role.RoleAggregate;
import de.fhdo.puls.user_management_service.common.events.UserStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserAggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "E-mail address must not be empty")
    @Column(unique = true)
    @Email(message = "Invalid e-mail address")
    private String email;
    @NotBlank(message = "Firstname must not be empty")
    private String firstname;
    @NotBlank(message = "Lastname must not be empty")
    private String lastname;
    @NotBlank(message = "Password must not be empty")
    private String password;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedDate;
    private UserStatus currentStatus;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<RoleAggregate> roleAggregates;

    public UserAggregate() {
        // NOOP
    }

    public UserAggregate(String email, String firstname, String lastname, String password,
        UserStatus currentStatus, Set<RoleAggregate> roleAggregates) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.currentStatus = currentStatus;
        this.roleAggregates = roleAggregates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setCreatedDate(Date creationDate) {
        this.createdDate = creationDate;
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

    public Set<RoleAggregate> getRoleAggregates() {
        return roleAggregates;
    }

    public void setRoleAggregates(Set<RoleAggregate> roleAggregates) {
        this.roleAggregates = roleAggregates;
    }
}