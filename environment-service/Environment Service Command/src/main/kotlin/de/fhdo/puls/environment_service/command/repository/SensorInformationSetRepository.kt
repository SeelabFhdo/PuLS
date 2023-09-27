package de.fhdo.puls.environment_service.command.repository

import de.fhdo.puls.environment_service.command.domian.SensorInformationSet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SensorInformationSetRepository : CrudRepository<SensorInformationSet, String> {
    fun findSensorInformationSetBySensorBoxId(sensorBoxId: Long): SensorInformationSet
}