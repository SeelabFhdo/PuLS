package de.fhdo.puls.booking_service.query.repository;

import de.fhdo.puls.booking_service.query.domain.ParkInvoiceAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkInvoiceRepo extends JpaRepository<ParkInvoiceAggregate, Long> {
    ParkInvoiceAggregate findByOriginalIdEquals(Long originalInvoiceId);
    ParkInvoiceAggregate findByBookingIdEquals(Long bookingId);
}
