package de.fhdo.puls.environment_service.query.service

import de.fhdo.puls.environment_service.common.event.EnvironmentDataReceivedEvent
import de.fhdo.puls.environment_service.common.event.EnvironmentSensorUnitCreatedEvent
import de.fhdo.puls.environment_service.query.domain.EnvironmentInformation
import de.fhdo.puls.environment_service.query.domain.EnvironmentInformationValueObject
import de.fhdo.puls.environment_service.query.domain.EnvironmentSensorUnitAggregate
import de.fhdo.puls.environment_service.query.domain.EnvironmentSensorUnitValueObject
import de.fhdo.puls.environment_service.query.domain.Location
import de.fhdo.puls.environment_service.query.domain.SensorType
import de.fhdo.puls.environment_service.query.domain.SensorUnitStatus
import de.fhdo.puls.environment_service.query.repository.EnvironmentInformationRepository
import de.fhdo.puls.environment_service.query.repository.EnvironmentSensorUnitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.LinkedList

@Service
class EnvironmentSensorUnitService {
    @Autowired
    lateinit var environmentSensorUnitRepository: EnvironmentSensorUnitRepository

    @Autowired
    lateinit var environmentInformationRepository: EnvironmentInformationRepository

    fun handleEnvironmentSensorUnitCreatedEvent(event: EnvironmentSensorUnitCreatedEvent) {
        val aggregate = aggregateFromEnvironmentSensorUnitCreatedEvent(event)
        environmentSensorUnitRepository.save(aggregate)
        val information = createEnvironmentInformationStoreForSensorUnit(aggregate)
        environmentInformationRepository.save(information)
    }

    fun handleEnvironmentDataReceivedEvent(event: EnvironmentDataReceivedEvent) {
        val information = environmentInformationRepository.findEnvironmentInformationBySensorBoxId(
            event.sensorUnitId.toLong())
        information.particulate2Data.add(event.particulates2)
        information.particulate10Data.add(event.particulates10)
        information.lightData.add(event.light)
        information.temperatureData.add(event.temperature)
        information.humidityData.add(event.humidity)
        environmentInformationRepository.save(information)
    }

    fun aggregateFromEnvironmentSensorUnitCreatedEvent(event: EnvironmentSensorUnitCreatedEvent):
        EnvironmentSensorUnitAggregate {
        return EnvironmentSensorUnitAggregate( event.name, event.id,event.sensorBoxId,
                event.description, SensorUnitStatus.valueOf(event.status),
                Location(event.longitude, event.latitude), SensorType.valueOf(event.sensorTyp))
    }

    fun valueObjectFromAggregate(aggregate: EnvironmentSensorUnitAggregate):
        EnvironmentSensorUnitValueObject {
        return EnvironmentSensorUnitValueObject(aggregate.name, aggregate.sensorBoxId,
            aggregate.description, aggregate.status.toString(),
            aggregate.location.longitude, aggregate.location.latitude,
            aggregate.sensorType.toString())
    }

    fun valueObjectListFromAggregates(aggregates: List<EnvironmentSensorUnitAggregate>):
        List<EnvironmentSensorUnitValueObject> {
        val list = LinkedList<EnvironmentSensorUnitValueObject>()
        aggregates.forEach { aggregate -> list.add(valueObjectFromAggregate(aggregate)) }
        return list
    }

    fun valueObjectFromEnvironmentInformation(information: EnvironmentInformation):
        EnvironmentInformationValueObject {
        return EnvironmentInformationValueObject(information.sensorBoxId.toString(),
            information.particulate2Data, information.particulate10Data, information.lightData,
            information.temperatureData, information.humidityData)
    }

    private fun createEnvironmentInformationStoreForSensorUnit(aggregate: EnvironmentSensorUnitAggregate):
        EnvironmentInformation {
        return EnvironmentInformation(aggregate.sensorBoxId)
    }
}