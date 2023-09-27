package de.fhdo.puls.park_and_charge_service.query.repository

import de.fhdo.puls.park_and_charge_service.query.domain.ChargingInformation
import org.springframework.data.repository.CrudRepository

interface ChargingInformationRepository : CrudRepository<ChargingInformation, String> {
    fun findByChargingStationId(id: String): ChargingInformation
}