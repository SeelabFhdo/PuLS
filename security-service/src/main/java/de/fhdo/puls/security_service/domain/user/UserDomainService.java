package de.fhdo.puls.security_service.domain.user;

import de.fhdo.puls.security_service.domain.role.RoleRepository;
import de.fhdo.puls.user_management_service.common.events.UserCreatedEvent;
import de.fhdo.puls.user_management_service.common.events.UserUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class UserDomainService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDomainService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        var userAggregate = aggregateFromCreatedEvent(userCreatedEvent);
        userRepository.save(userAggregate);
    }

    public void handleUserUpdatedEvent(UserUpdatedEvent userUpdatedEvent) {
        var userAggregate = userRepository.findByEmail(userUpdatedEvent.getEmail());
        if (userAggregate != null) {
            userRepository.save(this.aggregateFromUpdatedEvent(userAggregate, userUpdatedEvent));
        }
    }

    private UserAggregate aggregateFromCreatedEvent(UserCreatedEvent event) {
        return new UserAggregate(event.getId(), event.getEmail(), event.getFirstname(),
            event.getLastname(), event.getPassword(), event.getCreatedDate(),
            event.getLastModifiedDate(), event.getCurrentStatus(),
            roleRepository.find(event.getRoles()));
    }

    private UserAggregate aggregateFromUpdatedEvent(UserAggregate user, UserUpdatedEvent event) {
        user.setEmail(event.getEmail());
        user.setFirstname(event.getFirstname());
        user.setLastname(event.getLastname());
        user.setPassword(event.getPassword());
        user.setLastModifiedDate(new Date());
        return user;
    }
}
