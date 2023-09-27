package de.fhdo.puls.environment_service.query.repository

import de.fhdo.puls.environment_service.common.event.SensorValue
import de.fhdo.puls.environment_service.query.domain.EnvironmentInformation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EnvironmentInformationRepository: CrudRepository<EnvironmentInformation, String> {
    fun findEnvironmentInformationBySensorBoxId(id: Long): EnvironmentInformation
}