package de.fhdo.puls.user_management_service.query.api;

import de.fhdo.puls.user_management_service.query.domain.user.UserDomainService;
import de.fhdo.puls.user_management_service.query.domain.user.UserRepository;
import de.fhdo.puls.user_management_service.query.domain.user.UserValueObject;
import de.fhdo.puls.user_management_service.query.security.AccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/resources/v1")
@CrossOrigin(origins = "${cors.origins}", allowedHeaders = "*",
        methods = { RequestMethod.HEAD, RequestMethod.GET,
                RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserManagementServiceQueryApi {
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final AccessHandler accessHandler;

    @Autowired
    public UserManagementServiceQueryApi(UserRepository userRepository,
        UserDomainService userDomainService, AccessHandler accessHandler) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.accessHandler = accessHandler;
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasRole('USER')")
    public UserValueObject getUser(Principal principal,
        @PathVariable String email) throws UserNotFoundException {
        if (accessHandler.validateAccessOnResource(principal.getName(), email)) {
            var userAggregate = userRepository.findByEmail(email);
            if (userAggregate != null)
                return userDomainService.valueObjectFromAggregate(userAggregate);
            else
                throw new UserNotFoundException(email);
        } else {
            throw new AccessDeniedException("Access on resource not allowed.");
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER')")
    public List<UserValueObject> getAllUser() {
        return userDomainService.getAllUser();
    }
}
