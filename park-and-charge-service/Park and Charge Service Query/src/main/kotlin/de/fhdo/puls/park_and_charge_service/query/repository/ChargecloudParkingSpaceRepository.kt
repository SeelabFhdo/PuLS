package de.fhdo.puls.park_and_charge_service.query.repository

import de.fhdo.puls.park_and_charge_service.query.domain.ChargecloudParkingSpaceAggregate
import org.springframework.data.repository.CrudRepository

interface ChargecloudParkingSpaceRepository: CrudRepository<ChargecloudParkingSpaceAggregate, String> {
}