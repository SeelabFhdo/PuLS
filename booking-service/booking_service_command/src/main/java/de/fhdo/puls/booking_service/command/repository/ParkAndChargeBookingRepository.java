package de.fhdo.puls.booking_service.command.repository;

import de.fhdo.puls.booking_service.command.domain.ParkAndChargeBookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkAndChargeBookingRepository extends JpaRepository<ParkAndChargeBookingAggregate, Long> {
    //ParkAndChargeBookingAggregate findByparkBookingId(Long parkBookingId);
}
