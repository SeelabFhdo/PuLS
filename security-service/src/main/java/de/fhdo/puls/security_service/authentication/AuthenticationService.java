package de.fhdo.puls.security_service.authentication;

import de.fhdo.puls.security_service.domain.role.RoleAggregate;
import de.fhdo.puls.security_service.domain.user.UserAggregate;
import de.fhdo.puls.security_service.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service(value = "authenticationService")
public class AuthenticationService implements UserDetailsService  {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userAggregate = userRepository.findByEmail(email);
        if (userAggregate == null) {
            log.error("Invalid username or password.");
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> grantedAuthority = getAuthorities(userAggregate);
        return new User(userAggregate.getEmail(), userAggregate.getPassword(),
                grantedAuthority);
    }

    private Set<GrantedAuthority> getAuthorities(UserAggregate userAggregate) {
        Set<RoleAggregate> roleByUserId = userAggregate.getRoleAggregates();
        final Set<GrantedAuthority> authorities = roleByUserId.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString()
                    .toUpperCase()))
                .collect(Collectors.toSet());
        return authorities;
    }
}