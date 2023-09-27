/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.config;

import de.fhdo.puls.park_and_charge_service.common.event.ElectrifiedParkingSpaceCreatedEvent;
import de.fhdo.puls.park_and_charge_service.common.event.ParkingSpaceCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.group.id}")
    private String defaultGroupId;

    public ConsumerFactory<String, ParkingSpaceCreatedEvent> parkingSpaceCreatedEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, defaultGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(ParkingSpaceCreatedEvent.class));
    }

    public ConsumerFactory<String, ElectrifiedParkingSpaceCreatedEvent>
    electrifiedParkingSpaceUpdatedEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, defaultGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(ElectrifiedParkingSpaceCreatedEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkingSpaceCreatedEvent>
    parkingSpaceCreatedEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ParkingSpaceCreatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(parkingSpaceCreatedEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ElectrifiedParkingSpaceCreatedEvent>
    electrifiedParkingSpaceCreatedEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ElectrifiedParkingSpaceCreatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(electrifiedParkingSpaceUpdatedEventConsumerFactory());
        return factory;
    }
}

