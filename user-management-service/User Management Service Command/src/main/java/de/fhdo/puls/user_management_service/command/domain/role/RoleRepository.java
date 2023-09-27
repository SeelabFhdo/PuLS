package de.fhdo.puls.user_management_service.command.domain.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends CrudRepository<RoleAggregate, Long> {
    @Query(value = "SELECT * FROM roles where name IN (:roles)", nativeQuery = true)
    Set<RoleAggregate> find(@Param("roles") List<String> roles);
    RoleAggregate findByName(RoleTyp name);
}