package de.fhdo.puls.security_service.listener;

import de.fhdo.puls.security_service.domain.user.UserDomainService;
import de.fhdo.puls.user_management_service.common.events.UserCreatedEvent;

import de.fhdo.puls.user_management_service.common.events.UserUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedEventListener {
    private final UserDomainService userDomainService;

    @Autowired
    public UserCreatedEventListener(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @KafkaListener(topics = "${kafka.topic.userevent.created}", groupId = "${kafka.group.id}",
        containerFactory = "userCreatedEventKafkaListenerContainerFactory")
    public void userCreatedEventListener(UserCreatedEvent userCreatedEvent) {
        userDomainService.handleUserCreatedEvent(userCreatedEvent);
    }

    @KafkaListener(topics = "${kafka.topic.userevent.updated}", groupId = "${kafka.group.id}",
            containerFactory = "userUpdatedEventKafkaListenerContainerFactory")
    public void userUpdateEventListener(UserUpdatedEvent userUpdatedEvent) {
        userDomainService.handleUserUpdatedEvent(userUpdatedEvent);
    }
}
