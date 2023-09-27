package de.fhdo.puls.booking_service.query.repository;

import de.fhdo.puls.booking_service.query.domain.ParkBooking;
import de.fhdo.puls.booking_service.query.domain.ParkBookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkBookingRepository extends JpaRepository<ParkBookingAggregate, Long> {
    @Query
    List<ParkBookingAggregate> findAllByBookingCanceledIsFalse(boolean bookingCanceled);

    @Query
    List<ParkBookingAggregate> findAllByBookingCanceledIsTrue(boolean bookingCanceled);
}
