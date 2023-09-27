package de.fhdo.puls.booking_service.command.repository;

import de.fhdo.puls.booking_service.command.domain.ParkInvoiceAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkInvoiceRepo extends JpaRepository<ParkInvoiceAggregate, Long> {

}
