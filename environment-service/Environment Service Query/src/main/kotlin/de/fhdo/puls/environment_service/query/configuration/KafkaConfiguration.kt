package de.fhdo.puls.environment_service.query.configuration

import de.fhdo.puls.environment_service.common.event.EnvironmentDataReceivedEvent
import de.fhdo.puls.environment_service.common.event.EnvironmentSensorUnitCreatedEvent
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

    fun environmentSensorUnitCreatedEventConsumerFactory():
        ConsumerFactory<String, EnvironmentSensorUnitCreatedEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                EnvironmentSensorUnitCreatedEvent>(props, StringDeserializer(),
                JsonDeserializer<EnvironmentSensorUnitCreatedEvent>
                    (EnvironmentSensorUnitCreatedEvent::class.java))
    }

    fun environmentDataReceivedEventConsumerFactory():
        ConsumerFactory<String, EnvironmentDataReceivedEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = defaultGroupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory<String,
                EnvironmentDataReceivedEvent>(props, StringDeserializer(),
                JsonDeserializer<EnvironmentDataReceivedEvent>
                (EnvironmentDataReceivedEvent::class.java))
    }

    @Bean
    fun environmentSensorUnitCreatedEventKafkaListenerContainerFactory():
        ConcurrentKafkaListenerContainerFactory<String, EnvironmentSensorUnitCreatedEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
            EnvironmentSensorUnitCreatedEvent> =
                ConcurrentKafkaListenerContainerFactory<String, EnvironmentSensorUnitCreatedEvent>()
        factory.consumerFactory = environmentSensorUnitCreatedEventConsumerFactory()
        return factory
    }

    @Bean
    fun environmentDataReceivedEventKafkaListenerContainerFactory():
        ConcurrentKafkaListenerContainerFactory<String, EnvironmentDataReceivedEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String,
                EnvironmentDataReceivedEvent> =
                ConcurrentKafkaListenerContainerFactory<String, EnvironmentDataReceivedEvent>()
        factory.consumerFactory = environmentDataReceivedEventConsumerFactory()
        return factory
    }
}