package de.fhdo.puls.security_service.domain.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserAggregate, Long> {
    @Query
    UserAggregate findByEmail(String email);
}
