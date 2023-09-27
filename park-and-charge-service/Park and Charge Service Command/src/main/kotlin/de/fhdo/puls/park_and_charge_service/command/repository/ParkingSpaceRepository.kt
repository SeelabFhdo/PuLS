package de.fhdo.puls.park_and_charge_service.command.repository

import de.fhdo.puls.park_and_charge_service.command.domain.ParkingSpaceAggregate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ParkingSpaceRepository: CrudRepository<ParkingSpaceAggregate, String> {
}