package de.fhdo.puls.user_management_service.query.domain.user;

import de.fhdo.puls.user_management_service.common.events.UserCreatedEvent;
import de.fhdo.puls.user_management_service.common.events.UserUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserDomainService {
    private final UserRepository userRepository;

    @Autowired
    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        var userAggregate = aggregateFromEvent(userCreatedEvent);
        userRepository.save(userAggregate);
    }

    private UserAggregate aggregateFromEvent(UserCreatedEvent event) {
        return new UserAggregate(event.getId(), event.getEmail(), event.getFirstname(),
            event.getLastname(), event.getPassword(), event.getCreatedDate(),
            event.getLastModifiedDate(), event.getCurrentStatus(), event.getRoles());
    }

    public UserValueObject valueObjectFromAggregate(UserAggregate userAggregate) {
        return new UserValueObject(userAggregate.getEmail(), userAggregate.getFirstname(),
            userAggregate.getLastname(), userAggregate.getCurrentStatus(),
                userAggregate.getUserRoles());
    }

    public void handleUserUpdatedEvent(UserUpdatedEvent userUpdatedEvent) {
        var userAggregate = userRepository.findByEmail(userUpdatedEvent.getEmail());
        if (userAggregate != null) {
            userRepository.save(this.aggregateFromUpdatedEvent(userAggregate, userUpdatedEvent));
        }
    }

    public List<UserValueObject> getAllUser() {
        var userValueObjectList = new LinkedList<UserValueObject>();
        userRepository.findAll().forEach(userAggregate ->
            userValueObjectList.add(this.valueObjectFromAggregate(userAggregate)));
        return userValueObjectList;
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