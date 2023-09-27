package de.fhdo.puls.booking_service.command.repository;

import de.fhdo.puls.booking_service.command.domain.ChargeInvoiceAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeInvoiceRepo extends JpaRepository<ChargeInvoiceAggregate, Long> {

}
