package de.fhdo.puls.environment_service.command.service

import de.fhdo.puls.environment_service.command.configuration.DefaultTopicKafkaTemplate
import de.fhdo.puls.environment_service.command.domian.EnvironmentSensorUnitAggregate
import de.fhdo.puls.environment_service.command.domian.Location
import de.fhdo.puls.environment_service.command.domian.ReceivedDataSet
import de.fhdo.puls.environment_service.command.domian.SensorInformationSet
import de.fhdo.puls.environment_service.command.domian.SensorType
import de.fhdo.puls.environment_service.command.domian.SensorUnitStatus
import de.fhdo.puls.environment_service.command.repository.EnvironmentSensorUnitRepository
import de.fhdo.puls.environment_service.command.repository.SensorInformationSetRepository
import de.fhdo.puls.environment_service.common.command.CreateEnvironmentSensorUnitCommand
import de.fhdo.puls.environment_service.common.event.EnvironmentDataReceivedEvent
import de.fhdo.puls.environment_service.common.event.EnvironmentSensorUnitCreatedEvent
import de.fhdo.puls.environment_service.common.event.SensorValue
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.util.Date

@Service
class EnvironmentSensorUnitService {
    @Value("\${kafka.topic.sensorunitevent.created}")
    lateinit var sensorUnitCreatedTopic: String

    @Value("\${kafka.topic.sensorunitevent.received}")
    lateinit var environmentSensorReceivedTopic: String

    @Autowired
    lateinit var environmentSensorUnitRepository: EnvironmentSensorUnitRepository

    @Autowired
    lateinit var sensorInformationSetRepository: SensorInformationSetRepository

    @Qualifier("environmentSensorUnitEventProducer")
    @Autowired
    lateinit var createdEventProducer:
        DefaultTopicKafkaTemplate<String, EnvironmentSensorUnitCreatedEvent>

    @Qualifier("environmentSensorValueEventProducer")
    @Autowired
    lateinit var receivedEventProducer:
        DefaultTopicKafkaTemplate<String, EnvironmentDataReceivedEvent>

    private val logger = KotlinLogging.logger {}

    fun handleCreateEnvironmentSensorUnitCommand(command: CreateEnvironmentSensorUnitCommand) {
        var aggregate = this.aggregateFromCreateSensorUnitCommand(command)
        aggregate = environmentSensorUnitRepository.save(aggregate)
        createSensorInformationSetFromAggregate(aggregate)
        val event = this.createEnvironmentSensorUnitCreatedEventFromAggregate(aggregate)
        createdEventProducer.send(sensorUnitCreatedTopic, event)
        logger.info { "Event send: ${event.toString()}" }
    }

    fun handleIncomingSensorValues(sensorValues: String) {
        val dataSet = receivedDataSetFromString(sensorValues)
        val sensorInformationSet = sensorInformationSetRepository
            .findSensorInformationSetBySensorBoxId(dataSet.sensorId.toLong())
        sensorInformationSet.ReceivedDataSetList.add(dataSet)
        sensorInformationSetRepository.save(sensorInformationSet)
        val event = eventFromDataSet(dataSet)
        receivedEventProducer.send(environmentSensorReceivedTopic, event)
        logger.info { "Event send: ${event.toString()}" }
    }

    private fun eventFromDataSet(data: ReceivedDataSet): EnvironmentDataReceivedEvent{
        return EnvironmentDataReceivedEvent(data.sensorId,
            SensorValue(data.particulate2.toString(), data.receivedTimeStamp),
            SensorValue(data.particulate10.toString(), data.receivedTimeStamp),
            SensorValue(data.light.toString(), data.receivedTimeStamp),
            SensorValue(data.temperature.toString(), data.receivedTimeStamp),
            SensorValue(data.humidity.toString(), data.receivedTimeStamp))
    }

    private fun aggregateFromCreateSensorUnitCommand(command: CreateEnvironmentSensorUnitCommand):
        EnvironmentSensorUnitAggregate {
        return EnvironmentSensorUnitAggregate(command.name, command.sensorBoxId,
            command.description, SensorUnitStatus.valueOf(command.status),
            Location(command.longitude, command.latitude), Date(), Date(),
            SensorType.valueOf(command.sensorType))
    }

    private fun createEnvironmentSensorUnitCreatedEventFromAggregate(
        aggregate: EnvironmentSensorUnitAggregate): EnvironmentSensorUnitCreatedEvent {
        return EnvironmentSensorUnitCreatedEvent(aggregate.id.toString(), aggregate.name,
                aggregate.sensorBoxId, aggregate.description, aggregate.status.toString(),
                aggregate.location.longitude, aggregate.location.latitude,
                aggregate.createdDate, aggregate.lastModifiedDate, aggregate.sensorType.toString())
    }

    private fun createSensorInformationSetFromAggregate(aggregate: EnvironmentSensorUnitAggregate) {
        sensorInformationSetRepository.save(SensorInformationSet(aggregate.name,
            aggregate.sensorBoxId))
    }

    private fun receivedDataSetFromString(data: String): ReceivedDataSet {
        val id = convertHexToInt(data.substring(0,2)).toString()
        val pm2 = convertHexToFloat(data.substring(4, 12))
        val pm10 = convertHexToFloat(data.substring(12, 20))
        val light = convertHexToBigInt(data.substring(20, 24)).toInt()
        val temperature = convertHexToFloat(data.substring(40, 48))
        val humidity = convertHexToFloat(data.substring(32, 40))

        return ReceivedDataSet(id, Date(), pm2, pm10, light, temperature, humidity)
    }

    private fun convertHexToInt(hex: String): Int {
        return hex.toInt()
    }

    private fun convertHexToFloat(hex: String): Float {
        return java.lang.Float.intBitsToFloat(hex.toLong(16).toInt())
    }

    private fun convertHexToBigInt(hex: String): BigInteger {
        return hex.toBigInteger(16)
    }


}