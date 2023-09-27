package de.fhdo.puls.environment_service.command.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture

@Configuration
class DefaultTopicKafkaTemplate<K, V> : KafkaTemplate<K, V> {
    @Value(value = "\${kafka.message.topic.name}")
    private val defaultTopicName: String? = null
    constructor(): super(DefaultKafkaProducerFactory<K,V>(HashMap<String, Any>()))
    constructor(producerFactory: ProducerFactory<K, V>) : super(producerFactory) {}
    constructor(producerFactory: ProducerFactory<K, V>, autoFlush: Boolean) : super(producerFactory!!, autoFlush) {}

    fun send(data: V): ListenableFuture<SendResult<K, V>> {
        return super.send(defaultTopicName!!, data)
    }
}