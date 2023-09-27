package de.fhdo.puls.booking_service.query.repository;

import de.fhdo.puls.booking_service.query.domain.ChargeInvoiceAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeInvoiceRepo extends JpaRepository<ChargeInvoiceAggregate, Long> {
    ChargeInvoiceAggregate findByOriginalIdEquals(Long originalInvoiceId);
    ChargeInvoiceAggregate findByBookingIdEquals(Long bookingId);
}
