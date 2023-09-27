package de.fhdo.puls.environment_service.query.repository

import de.fhdo.puls.environment_service.query.domain.EnvironmentSensorUnitAggregate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EnvironmentSensorUnitRepository : CrudRepository<EnvironmentSensorUnitAggregate, String> {
    fun findEnvironmentSensorUnitAggregateByName(name: String): EnvironmentSensorUnitAggregate
    fun findEnvironmentSensorUnitAggregateBySensorBoxId(sensorBoxId: Long):
        EnvironmentSensorUnitAggregate
}