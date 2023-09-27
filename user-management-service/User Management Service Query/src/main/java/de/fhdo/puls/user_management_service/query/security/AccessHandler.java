package de.fhdo.puls.user_management_service.query.security;

import de.fhdo.puls.user_management_service.query.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessHandler {
    private final UserRepository userRepository;

    @Autowired
    public AccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateAccessOnResource(String principleName, String requestedEmail) {
        if (principleName.equals(requestedEmail)) {
            return true;
        } else if (userRepository.findByEmail(principleName).getUserRoles()
            .contains("ADMINISTRATOR")) {
            return true;
        }
        return false;
    }
}
