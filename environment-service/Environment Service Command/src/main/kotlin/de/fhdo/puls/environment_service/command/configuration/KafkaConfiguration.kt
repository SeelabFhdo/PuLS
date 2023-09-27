package de.fhdo.puls.environment_service.command.configuration

import de.fhdo.puls.environment_service.common.event.EnvironmentDataReceivedEvent
import de.fhdo.puls.environment_service.common.event.EnvironmentSensorUnitCreatedEvent
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
    fun environmentSensorUnitCreatedEventProducerFactory():
            ProducerFactory<String, EnvironmentSensorUnitCreatedEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
             Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun environmentSensorValueReveivedEventProducerFactory():
        ProducerFactory<String, EnvironmentDataReceivedEvent> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
                Class.forName("org.springframework.kafka.support.serializer.JsonSerializer")
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun environmentSensorUnitEventProducer():
        DefaultTopicKafkaTemplate<String, EnvironmentSensorUnitCreatedEvent> {
        return DefaultTopicKafkaTemplate(environmentSensorUnitCreatedEventProducerFactory())
    }

    @Bean
    fun environmentSensorValueEventProducer():
        DefaultTopicKafkaTemplate<String, EnvironmentDataReceivedEvent> {
        return DefaultTopicKafkaTemplate(environmentSensorValueReveivedEventProducerFactory())
    }
}