package de.fhdo.puls.user_management_service.command.domain.user;

import de.fhdo.puls.user_management_service.command.configuration.DefaultTopicKafkaTemplate;
import de.fhdo.puls.user_management_service.command.domain.role.RoleRepository;
import de.fhdo.puls.user_management_service.command.domain.role.UnknownRoleException;
import de.fhdo.puls.user_management_service.common.commands.CreateUserCommand;
import de.fhdo.puls.user_management_service.common.commands.UpdateUserCommand;
import de.fhdo.puls.user_management_service.common.events.UserCreatedEvent;
import de.fhdo.puls.user_management_service.common.events.UserStatus;
import de.fhdo.puls.user_management_service.common.events.UserUpdatedEvent;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDomainService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final DefaultTopicKafkaTemplate<String, UserCreatedEvent> userCreatedEventProducer;
    private final DefaultTopicKafkaTemplate<String, UserUpdatedEvent> userUpdatedEventProducer;

    @Autowired
    public UserDomainService(UserRepository userRepository,
        DefaultTopicKafkaTemplate<String, UserCreatedEvent> userCreatedEventProducer,
        RoleRepository roleRepository,
        DefaultTopicKafkaTemplate<String, UserUpdatedEvent> userUpdatedEventProducer) {
        this.userRepository = userRepository;
        this.userCreatedEventProducer = userCreatedEventProducer;
        this.roleRepository = roleRepository;
        this.userUpdatedEventProducer = userUpdatedEventProducer;
    }

    public void handleCreateUserCommand(CreateUserCommand createUserCommand) throws Exception {
        var userAggregate = aggregateFromCreateCommand(createUserCommand);
        validateEmail(userAggregate);
        validateRoles(userAggregate, createUserCommand.getRoles());
        userRepository.save(userAggregate);
        var userCreatedEvent = createEventFromAggregate(userAggregate);
        userCreatedEventProducer.send("UserCreatedTopic", userCreatedEvent);
    }

    public void handleUpdateUserCommand(UpdateUserCommand updateUserCommand) throws Exception {
        var userAggregate = aggregateFromUpdateCommand(updateUserCommand);
        userRepository.save(userAggregate);
        var userUpdatedEvent = updateEventFromAggregate(userAggregate);
        userUpdatedEventProducer.send("UserUpdatedTopic", userUpdatedEvent);
    }

    private UserAggregate aggregateFromCreateCommand(CreateUserCommand command) {
        return new UserAggregate(command.getEmail(), command.getFirstname(), command.getLastname(),
            command.getPassword(), UserStatus.ACTIVE,
                roleRepository.find(command.getRoles()));
    }

    private UserAggregate aggregateFromUpdateCommand(UpdateUserCommand command) {
        var userAggregate = userRepository.findByEmail(command.getEmail());
        if (userAggregate != null) {
            userAggregate.setEmail(command.getEmail());
            userAggregate.setFirstname(command.getFirstname());
            userAggregate.setLastname(command.getLastname());
            if (command.getPassword() == null) {
                userAggregate.setPassword(userRepository.findByEmail(command.getEmail())
                    .getPassword());
            } else {
                userAggregate.setPassword(command.getPassword());
            }
            userAggregate.setLastModifiedDate(new Date());
            return userAggregate;
        } else {
            return null;
        }
    }

    private void validateEmail(UserAggregate aggregate) throws Exception {
        if (userRepository.existsByEmail(aggregate.getEmail()))
            throw new UserAlreadyExistsException(aggregate.getEmail());
    }

    private void validateRoles(UserAggregate aggregate, List<String> roles) throws Exception{
        var aggregateRoles = aggregate.getRoleAggregates().stream().map(r -> r.getName().toString())
            .collect(Collectors.toList());
        if (!aggregateRoles.containsAll(roles))
            throw new UnknownRoleException(roles.stream().filter(role -> !aggregateRoles
                    .contains(role)).collect(Collectors.toList()));
    }

    private UserCreatedEvent createEventFromAggregate(UserAggregate userAggregate) {
        return new UserCreatedEvent(userAggregate.getId(), userAggregate.getEmail(),
            userAggregate.getFirstname(), userAggregate.getLastname(), userAggregate.getPassword(),
            userAggregate.getCreatedDate(), userAggregate.getLastModifiedDate(),
            userAggregate.getCurrentStatus(), userAggregate.getRoleAggregates().stream()
                .map(role -> role.getName().toString()).collect(Collectors.toList()));
    }

    private UserUpdatedEvent updateEventFromAggregate(UserAggregate userAggregate) {
        return new UserUpdatedEvent(userAggregate.getId(), userAggregate.getEmail(),
                userAggregate.getFirstname(), userAggregate.getLastname(),
                userAggregate.getPassword(), userAggregate.getCreatedDate(),
                userAggregate.getLastModifiedDate(),
                userAggregate.getRoleAggregates().stream()
                        .map(role -> role.getName().toString()).collect(Collectors.toList()));
    }
}