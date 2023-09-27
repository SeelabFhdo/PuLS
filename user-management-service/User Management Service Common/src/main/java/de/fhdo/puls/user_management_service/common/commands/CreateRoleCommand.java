package de.fhdo.puls.user_management_service.common.commands;

public class CreateRoleCommand {
    private String roleName;
    private String description;

    public CreateRoleCommand() {
    }

    public CreateRoleCommand(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
