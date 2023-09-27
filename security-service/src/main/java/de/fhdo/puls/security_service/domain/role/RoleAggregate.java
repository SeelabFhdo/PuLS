package de.fhdo.puls.security_service.domain.role;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ROLES")
public class RoleAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private RoleTyp name;
    private String description;
    private Date createdOn;
    private Date modifiedOn;

    public RoleAggregate() {
    }

    public RoleAggregate (RoleTyp name, String description, Date createdOn, Date modifiedOn) {
        this.name = name;
        this.description = description;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    public RoleTyp getName() {
        return name;
    }

    public void setName(RoleTyp name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}

