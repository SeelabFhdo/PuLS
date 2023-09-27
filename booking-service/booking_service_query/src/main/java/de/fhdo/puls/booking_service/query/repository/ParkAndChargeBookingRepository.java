package de.fhdo.puls.booking_service.query.repository;

import de.fhdo.puls.booking_service.query.domain.ParkAndChargeBookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkAndChargeBookingRepository extends JpaRepository<ParkAndChargeBookingAggregate, Long> {
    @Query
    List<ParkAndChargeBookingAggregate> findAllByBookingCanceledIsFalse(boolean bookingCanceled);

    @Query
    List<ParkAndChargeBookingAggregate> findAllByBookingCanceledIsTrue(boolean bookingCanceled);
}
