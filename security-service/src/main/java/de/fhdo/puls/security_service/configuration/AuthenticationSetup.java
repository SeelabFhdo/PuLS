package de.fhdo.puls.security_service.configuration;

import de.fhdo.puls.security_service.domain.role.RoleAggregate;
import de.fhdo.puls.security_service.domain.role.RoleRepository;
import de.fhdo.puls.security_service.domain.role.RoleTyp;
import de.fhdo.puls.security_service.domain.user.UserAggregate;
import de.fhdo.puls.security_service.domain.user.UserRepository;
import de.fhdo.puls.user_management_service.common.events.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.LinkedList;

@Component
public class AuthenticationSetup {
    @Value(value = "${puls.username}")
    private String pulsUsername;
    @Value(value = "${puls.password}")
    private String pulsPassword;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationSetup(UserRepository userRepository, RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void createRoles() {
        if (roleRepository.findByName(RoleTyp.ADMINISTRATOR) == null) {
            var adminRole = new RoleAggregate(RoleTyp.ADMINISTRATOR, "Platform administrator role.",
                    new Date(), new Date());
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName(RoleTyp.USER) == null) {
            var userRole = new RoleAggregate(RoleTyp.USER, "Default user role.", new Date(),
                    new Date());
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName(RoleTyp.CPI_USER) == null) {
            var userRole = new RoleAggregate(RoleTyp.CPI_USER, "Charging point infrastructure user"
                    + " role.", new Date(), new Date());
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName(RoleTyp.CPI_PROVIDER) == null) {
            var userRole = new RoleAggregate(RoleTyp.CPI_PROVIDER, "Charging point infrastructure"
                    + " provider role.", new Date(), new Date());
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName(RoleTyp.ENVIRONMENTAL_OFFICER) == null) {
            var userRole = new RoleAggregate(RoleTyp.ENVIRONMENTAL_OFFICER,
                    "Environmental officer user role.", new Date(), new Date());
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName(RoleTyp.GRID_OPERATOR) == null) {
            var userRole = new RoleAggregate(RoleTyp.GRID_OPERATOR,
                    "Grid operator user role.", new Date(), new Date());
            roleRepository.save(userRole);
        }
    }

    @PostConstruct
    private void createAdministrationUser() {
        if (userRepository.findByEmail(pulsUsername) == null) {
            var roleList = new LinkedList<String>();
            roleList.add("USER");
            roleList.add("ADMINISTRATOR");
            var adminUser = new UserAggregate(0L, pulsUsername, "Administrator", "PULS",
                    passwordEncoder.encode(pulsPassword), new Date(), new Date(),
                    UserStatus.ACTIVE, roleRepository.find(roleList));
            userRepository.save(adminUser);
        }
    }
}
