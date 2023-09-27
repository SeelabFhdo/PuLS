package de.fhdo.puls.park_and_charge_service.query.repository

import de.fhdo.puls.park_and_charge_service.query.domain.ParkingSpaceAggregate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ParkingSpaceRepository: CrudRepository<ParkingSpaceAggregate, String> {
    fun findByOriginalId(originalId: String): Optional<ParkingSpaceAggregate>
}