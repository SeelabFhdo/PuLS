package de.fhdo.puls.user_management_service.command.api;

import de.fhdo.puls.user_management_service.command.domain.user.UserDomainService;
import de.fhdo.puls.user_management_service.common.commands.CreateUserCommand;
import de.fhdo.puls.user_management_service.common.commands.UpdateUserCommand;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/resources/v1")
@CrossOrigin(origins = "${cors.origins}", allowedHeaders = "*",
        methods = { RequestMethod.HEAD, RequestMethod.GET,
                RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserManagementServiceCommandApi {
    private final UserDomainService userDomainService;

    @Autowired
    public UserManagementServiceCommandApi(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @PostMapping(value = "/user")
    @ApiResponses({
        @ApiResponse(code = 400, message = "Invalid request")
    })
    @PreAuthorize("hasRole('USER')")
    public void createUser(@RequestBody @Valid CreateUserCommand command) throws Exception {
        userDomainService.handleCreateUserCommand(command);
    }

    @PutMapping(value = "/user")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid request")
    })
    @PreAuthorize("hasRole('USER')")
    public void updateUser(@RequestBody @Valid UpdateUserCommand command) throws Exception {
        userDomainService.handleUpdateUserCommand(command);

    }
}