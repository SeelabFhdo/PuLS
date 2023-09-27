package de.fhdo.puls.booking_service.command.repository;

import de.fhdo.puls.booking_service.command.domain.ParkBookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkBookingRepository extends JpaRepository<ParkBookingAggregate, Long> {
    //ParkBookingAggregate findByParkBookingId(Long parkBookingId);
}
