package de.fhdo.puls.user_management_service.command.domain.role;

import java.util.List;

public class UnknownRoleException extends Exception {
    List<String> unknownRoles;

    public UnknownRoleException(List<String> unknownRoles) {
        super("Roles with names " + unknownRoles.toString() + " not exists");
        this.unknownRoles = unknownRoles;
    }

    public List<String> getUnknownRoles() {
        return unknownRoles;
    }
}