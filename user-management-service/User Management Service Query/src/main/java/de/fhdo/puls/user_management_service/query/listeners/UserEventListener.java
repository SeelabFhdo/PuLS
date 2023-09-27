package de.fhdo.puls.user_management_service.query.listeners;

import de.fhdo.puls.user_management_service.common.events.UserCreatedEvent;
import de.fhdo.puls.user_management_service.common.events.UserUpdatedEvent;
import de.fhdo.puls.user_management_service.query.domain.user.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    private UserDomainService userDomainService;

    @Autowired
    public UserEventListener(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @KafkaListener(topics = "${kafka.topic.userevent.created}", groupId = "${kafka.group.id}",
        containerFactory = "userCreatedEventKafkaListenerContainerFactory")
    public void userCreatedEventListener(UserCreatedEvent userCreatedEvent) {
        userDomainService.handleUserCreatedEvent(userCreatedEvent);
    }

    @KafkaListener(topics = "${kafka.topic.userevent.updated}", groupId = "${kafka.group.id}",
            containerFactory = "userUpdatedEventKafkaListenerContainerFactory")
    public void userUpdatedEventListener(UserUpdatedEvent userUpdatedEvent) {
        userDomainService.handleUserUpdatedEvent(userUpdatedEvent);
    }
}
