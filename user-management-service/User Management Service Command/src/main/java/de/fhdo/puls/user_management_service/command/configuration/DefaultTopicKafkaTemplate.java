package de.fhdo.puls.user_management_service.command.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public class DefaultTopicKafkaTemplate<K, V> extends KafkaTemplate<K, V> {
    @Value(value = "${kafka.message.topic.name}")
    private String defaultTopicName;

    public DefaultTopicKafkaTemplate(ProducerFactory<K, V> producerFactory) {
        super(producerFactory);
    }

    public DefaultTopicKafkaTemplate(ProducerFactory<K, V> producerFactory, boolean autoFlush) {
        super(producerFactory, autoFlush);
    }

    public ListenableFuture<SendResult<K, V>> send(V data) {
        return super.send(defaultTopicName, data);
    }
}