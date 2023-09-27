/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.templates;

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

