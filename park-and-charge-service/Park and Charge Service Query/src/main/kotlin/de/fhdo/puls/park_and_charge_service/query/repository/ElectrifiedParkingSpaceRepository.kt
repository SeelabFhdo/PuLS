package de.fhdo.puls.park_and_charge_service.query.repository

import de.fhdo.puls.park_and_charge_service.query.domain.ElectrifiedParkingSpaceAggregate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ElectrifiedParkingSpaceRepository: CrudRepository<ElectrifiedParkingSpaceAggregate, String> {
    fun findElectrifiedParkingSpaceAggregateByParkingSenorId(parkingSenorId: String): ElectrifiedParkingSpaceAggregate
    fun findByOriginalId(originalId: String): Optional<ElectrifiedParkingSpaceAggregate>
}