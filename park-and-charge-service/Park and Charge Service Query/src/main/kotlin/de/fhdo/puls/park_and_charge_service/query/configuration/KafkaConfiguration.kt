package de.fhdo.puls.park_and_charge_service.query.configuration

import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudParkingSpacesSyncedEvent
import de.fhdo.puls.park_and_charge_service.common.event.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.HashMap

@Configuration
class KafkaConfiguration {
    @Value("\${kafka.bootstrapAddress}")
    lateinit var bootstrapAddress: String

    @Value("\${kafka.group.id}")
    lateinit var defaultGroupId: String

    fun parkingSpaceCreatedEventConsumerFactory():
            ConsumerFactory<String, ParkingSpaceCreatedEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                ParkingSpaceCreatedEvent>(
            props, StringDeserializer(),
            JsonDeserializer<ParkingSpaceCreatedEvent>
                (ParkingSpaceCreatedEvent::class.java)
        )
    }

    fun electrifiedParkingSpaceCreatedEventConsumerFactory():
            ConsumerFactory<String, ElectrifiedParkingSpaceCreatedEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                ElectrifiedParkingSpaceCreatedEvent>(
            props, StringDeserializer(),
            JsonDeserializer<ElectrifiedParkingSpaceCreatedEvent>
                (ElectrifiedParkingSpaceCreatedEvent::class.java)
        )
    }

    fun chargingInformationEventConsumerFactory():
            ConsumerFactory<String, ChargingInformationEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                ChargingInformationEvent>(
            props, StringDeserializer(),
            JsonDeserializer<ChargingInformationEvent>
                (ChargingInformationEvent::class.java)
        )
    }

    fun chargecloudParkingSpacesSyncedEventConsumerFactory():
            ConsumerFactory<String, ChargecloudParkingSpacesSyncedEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                ChargecloudParkingSpacesSyncedEvent>(
                props, StringDeserializer(),
                JsonDeserializer<ChargecloudParkingSpacesSyncedEvent>
                (ChargecloudParkingSpacesSyncedEvent::class.java)
        )
    }

    fun keepAliveInformationEventConsumerFactory():
            ConsumerFactory<String, KeepAliveInformationEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                KeepAliveInformationEvent>(
            props, StringDeserializer(),
            JsonDeserializer<KeepAliveInformationEvent>
                (KeepAliveInformationEvent::class.java)
        )
    }

    fun statusInformationEventConsumerFactory():
            ConsumerFactory<String, StatusInformationEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                StatusInformationEvent>(
            props, StringDeserializer(),
            JsonDeserializer<StatusInformationEvent>
                (StatusInformationEvent::class.java)
        )
    }

    fun parkingEventConsumerFactory():
            ConsumerFactory<String, ParkingStatusEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                ParkingStatusEvent>(
            props, StringDeserializer(),
            JsonDeserializer<ParkingStatusEvent>
                (ParkingStatusEvent::class.java)
        )
    }
    @Bean
    fun parkingSpaceCreatedEventKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, ParkingSpaceCreatedEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                ParkingSpaceCreatedEvent> =
            ConcurrentKafkaListenerContainerFactory<String, ParkingSpaceCreatedEvent>()
        factory.consumerFactory = parkingSpaceCreatedEventConsumerFactory()
        return factory
    }

    @Bean
    fun electrifiedParkingSpaceCreatedEventKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, ElectrifiedParkingSpaceCreatedEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                ElectrifiedParkingSpaceCreatedEvent> =
            ConcurrentKafkaListenerContainerFactory<String,
                    ElectrifiedParkingSpaceCreatedEvent>()
        factory.consumerFactory = electrifiedParkingSpaceCreatedEventConsumerFactory()
        return factory
    }

    @Bean
    fun chargingInformationEventKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, ChargingInformationEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                ChargingInformationEvent> = ConcurrentKafkaListenerContainerFactory<String, ChargingInformationEvent>()
        factory.consumerFactory = chargingInformationEventConsumerFactory()
        return factory
    }

    @Bean
    fun chargecloudParkingSpacesSyncedEventKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, ChargecloudParkingSpacesSyncedEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                ChargecloudParkingSpacesSyncedEvent> = ConcurrentKafkaListenerContainerFactory<String, ChargecloudParkingSpacesSyncedEvent>()
        factory.consumerFactory = chargecloudParkingSpacesSyncedEventConsumerFactory()
        return factory
    }

    @Bean
    fun keepAliveInformationEventKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, KeepAliveInformationEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                KeepAliveInformationEvent> =
            ConcurrentKafkaListenerContainerFactory<String, KeepAliveInformationEvent>()
        factory.consumerFactory = keepAliveInformationEventConsumerFactory()
        return factory
    }

    @Bean
    fun statusInformationEventKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, StatusInformationEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                StatusInformationEvent> =
            ConcurrentKafkaListenerContainerFactory<String, StatusInformationEvent>()
        factory.consumerFactory = statusInformationEventConsumerFactory()
        return factory
    }

    @Bean
    fun parkingEventKafkaListenerContainerFactory():
            ConcurrentKafkaListenerContainerFactory<String, ParkingStatusEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                ParkingStatusEvent> =
            ConcurrentKafkaListenerContainerFactory<String, ParkingStatusEvent>()
        factory.consumerFactory = parkingEventConsumerFactory()
        return factory
    }
}