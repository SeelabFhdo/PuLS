package de.fhdo.puls.park_and_charge_service.command.service;

import de.fhdo.puls.park_and_charge_service.command.configuration.DefaultTopicKafkaTemplate
import de.fhdo.puls.park_and_charge_service.command.domain.ChargecloudParkingSpaceAggregate
import de.fhdo.puls.park_and_charge_service.command.domain.ChargecloudPlugInformation
import de.fhdo.puls.park_and_charge_service.command.domain.Location
import de.fhdo.puls.park_and_charge_service.command.repository.ChargecloudParkingSpaceRepository
import de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.ChargecloudClient
import de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilityLocationsData.Data
import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudParkingSpace
import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudParkingSpacesSyncedEvent
import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudPlugInformation as ChargecloudPlugInformationEvent
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import de.fhdo.puls.park_and_charge_service.command.service.client.chargecloud.dto.emobilitylocationsDataDetails.Data as DetailData

@Service
class ChargecloudService {
    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var chargecloudClient: ChargecloudClient

    @Autowired
    lateinit var repository: ChargecloudParkingSpaceRepository

    @Value("\${kafka.topic.chargecloudparkingspace.synced}")
    lateinit var chargecloudParkingSpaceCreatedTopic: String

    @Qualifier("chargecloudParkingSpacesSyncedEventProducer")
    @Autowired
    lateinit var producer: DefaultTopicKafkaTemplate<String, ChargecloudParkingSpacesSyncedEvent>


    fun handleSyncCommand() {
        repository.deleteAll()
        val event = ChargecloudParkingSpacesSyncedEvent();

        for (location in chargecloudClient.getEmobilityLocationsData()) {
            // TODO Null-Asserted call (!!) oder Safe call (?.) ??
            val locationDetail = chargecloudClient.getEmobilityLocationsDataDetails(location.id)!!.data.first()
            var aggregate = aggregateFromDTO(location, locationDetail)
            aggregate = repository.save(aggregate)
            event.parkingSpaces.add(eventFromAggregate(aggregate))

        }

        producer.send(chargecloudParkingSpaceCreatedTopic, event)

        logger.info { "Saved ${event.parkingSpaces.size} chargecloud parkingspace aggregates" }
        logger.info { "Event sent: ${event.toString()}" }
    }


    /*
        ################
        ## Converters ##
        ################
     */

    private fun eventFromAggregate(aggregate: ChargecloudParkingSpaceAggregate): ChargecloudParkingSpace {
        return ChargecloudParkingSpace(name = aggregate.name,
                commandId = aggregate.id.orEmpty(),
                operatorId = aggregate.operatorId,
                operatorName = aggregate.operatorName,
                locationId = aggregate.locationId,
                latitude = aggregate.location.latitude,
                longitude = aggregate.location.longitude,
                address_street = aggregate.address_street,
                address_city = aggregate.address_city,
                address_zip = aggregate.address_zip,
                address_country = aggregate.address_country,
                plugs = aggregate.plugs.map { ChargecloudPlugInformationEvent(plugType = it.plugType, plugPowerType = it.plugPowerType, plugMaxPower = it.plugMaxPower, roaming = it.roaming) }
        )
    }

    private fun aggregateFromDTO(location: Data, locationDetail: DetailData): ChargecloudParkingSpaceAggregate {
        var plugs = mutableListOf<ChargecloudPlugInformation>();

        for (evse in locationDetail.evses) {
            // Even though connectors is a list, each Evse only has one connector!
            val connector = evse.connectors.first()

            plugs.add(ChargecloudPlugInformation(plugType = connector.standard,
                    plugPowerType = connector.power_type,
                    plugMaxPower =  connector.max_power.toString(),
                    roaming = evse.roaming))
        }

        // TODO PlugType -> Enum
        return ChargecloudParkingSpaceAggregate(id = null,
                name = locationDetail.name,
                operatorId = location.operatorId,
                operatorName = locationDetail.operator.name,
                locationId = location.id,
                location = Location(location.coordinates.longitude.toDouble(),
                        location.coordinates.latitude.toDouble()),
                address_street = locationDetail.address,
                address_city = locationDetail.city,
                address_country = locationDetail.country,
                address_zip = locationDetail.postal_code,
                plugs = plugs.toMutableSet().toList(),
                createdDate = Date(),
                lastModifiedDate = Date())
    }
}
