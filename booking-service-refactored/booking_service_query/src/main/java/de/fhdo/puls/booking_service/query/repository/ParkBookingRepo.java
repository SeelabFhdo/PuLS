package de.fhdo.puls.booking_service.query.repository;

import de.fhdo.puls.booking_service.query.domain.ParkBookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkBookingRepo extends JpaRepository<ParkBookingAggregate,Long> {

    ParkBookingAggregate findByOriginalIdEquals(Long originalBookingId);
}
