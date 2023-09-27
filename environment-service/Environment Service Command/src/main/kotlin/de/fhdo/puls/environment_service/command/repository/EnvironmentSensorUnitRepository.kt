package de.fhdo.puls.environment_service.command.repository

import de.fhdo.puls.environment_service.command.domian.EnvironmentSensorUnitAggregate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EnvironmentSensorUnitRepository: CrudRepository<EnvironmentSensorUnitAggregate, String>