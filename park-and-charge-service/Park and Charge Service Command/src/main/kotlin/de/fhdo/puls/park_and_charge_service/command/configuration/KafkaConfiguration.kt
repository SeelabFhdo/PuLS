package de.fhdo.puls.park_and_charge_service.command.configuration

import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudParkingSpacesSyncedEvent
import de.fhdo.puls.park_and_charge_service.common.event.*
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfiguration {
    @Value("\${kafka.bootstrapAddress}")
    lateinit var bootstrapAddress: String

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = HashMap<String, Any>()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun parkingSpaceCreatedEventProducerFactory():
            ProducerFactory<String, ParkingSpaceCreatedEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun parkingSpaceUpdatedEventProducerFactory():
            ProducerFactory<String, ParkingSpaceUpdatedEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun electrifiedParkingSpaceCreatedEventProducerFactory():
            ProducerFactory<String, ElectrifiedParkingSpaceCreatedEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun chargingInformationEvenProducerFactory():
            ProducerFactory<String, ChargingInformationEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun chargecloudParkingSpacesSyncedEventFactory() :
            ProducerFactory<String, ChargecloudParkingSpacesSyncedEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun statusInformationEvenProducerFactory():
            ProducerFactory<String, StatusInformationEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
            Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun keepAliveInformationEvenProducerFactory():
            ProducerFactory<String, KeepAliveInformationEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
            Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun parkingEvenProducerFactory():
            ProducerFactory<String, ParkingStatusEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
            Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun parkingSpaceCreatedEventProducer():
            DefaultTopicKafkaTemplate<String, ParkingSpaceCreatedEvent> {
        return DefaultTopicKafkaTemplate(parkingSpaceCreatedEventProducerFactory())
    }

    @Bean
    fun parkingSpaceUpdatedEventProducer():
            DefaultTopicKafkaTemplate<String, ParkingSpaceUpdatedEvent> {
        return DefaultTopicKafkaTemplate(parkingSpaceUpdatedEventProducerFactory())
    }

    @Bean
    fun electrifiedParkingSpaceCreatedEventProducer():
            DefaultTopicKafkaTemplate<String, ElectrifiedParkingSpaceCreatedEvent> {
        return DefaultTopicKafkaTemplate(electrifiedParkingSpaceCreatedEventProducerFactory())
    }

    @Bean
    fun chargingInformationEventProducer():
            DefaultTopicKafkaTemplate<String, ChargingInformationEvent> {
        return DefaultTopicKafkaTemplate(chargingInformationEvenProducerFactory())
    }

    @Bean
    fun chargecloudParkingSpacesSyncedEventProducer():
            DefaultTopicKafkaTemplate<String, ChargecloudParkingSpacesSyncedEvent> {
        return DefaultTopicKafkaTemplate(chargecloudParkingSpacesSyncedEventFactory())
    }

    @Bean
    fun statusInformationEventProducer():
            DefaultTopicKafkaTemplate<String, StatusInformationEvent> {
        return DefaultTopicKafkaTemplate(statusInformationEvenProducerFactory())
    }

    @Bean
    fun keepAliveInformationEventProducer():
            DefaultTopicKafkaTemplate<String, KeepAliveInformationEvent> {
        return DefaultTopicKafkaTemplate(keepAliveInformationEvenProducerFactory())
    }

    @Bean
    fun parkingEventProducer():
            DefaultTopicKafkaTemplate<String, ParkingStatusEvent> {
        return DefaultTopicKafkaTemplate(parkingEvenProducerFactory())
    }
}
