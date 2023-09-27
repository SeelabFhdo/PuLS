package de.fhdo.puls.user_management_service.common.events;

import java.util.Date;

public class RoleCreatedEvent {
    private long id;
    private String name;
    private String description;
    private Date createdOn;
    private Date modifiedOn;

    public RoleCreatedEvent() {
    }

    public RoleCreatedEvent(long id, String name, String description, Date createdOn,
        Date modifiedOn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
