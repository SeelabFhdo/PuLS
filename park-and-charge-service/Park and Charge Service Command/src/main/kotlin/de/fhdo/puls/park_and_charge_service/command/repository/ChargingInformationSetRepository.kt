package de.fhdo.puls.park_and_charge_service.command.repository

import de.fhdo.puls.park_and_charge_service.command.domain.ChargingInformationSet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChargingInformationSetRepository: CrudRepository<ChargingInformationSet, String> {
    fun findByChargingStationId(id: String): ChargingInformationSet
}