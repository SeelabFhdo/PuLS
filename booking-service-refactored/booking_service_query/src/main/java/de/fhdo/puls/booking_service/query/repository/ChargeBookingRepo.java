package de.fhdo.puls.booking_service.query.repository;

import de.fhdo.puls.booking_service.query.domain.ChargeBookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeBookingRepo extends JpaRepository<ChargeBookingAggregate,Long> {

    ChargeBookingAggregate findByOriginalIdEquals(Long originalBookingId);
}
