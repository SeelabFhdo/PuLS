package de.fhdo.puls.user_management_service.command.domain.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserAggregate, Long> {
    @Query("SELECT CASE WHEN count(u)> 0 THEN true ELSE false END FROM UserAggregate u where u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    UserAggregate findByEmail(String email);
}