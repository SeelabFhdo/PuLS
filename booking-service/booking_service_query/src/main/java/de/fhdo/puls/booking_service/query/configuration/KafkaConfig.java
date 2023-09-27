package de.fhdo.puls.booking_service.query.configuration;

import de.fhdo.puls.booking_service.common.events.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.protocol.types.Field;
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
public class KafkaConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.group.id}")
    private String defaultGroupId;

    @Bean
    public Map<String, Object> configProps(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, defaultGroupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return configProps;
    }


    /*---------------------------------------------------------------------*/
    /*Created Events*/
    public ConsumerFactory<String, ParkBookingCreatedEvent> parkBookingCreatedEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(configProps(),
                new StringDeserializer(), new JsonDeserializer<>(ParkBookingCreatedEvent.class));
    }

    public ConsumerFactory<String, ParkAndChargeBookingCreatedEvent> parkAndChargeBookingCreatedEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(configProps(),
                new StringDeserializer(), new JsonDeserializer<>(ParkAndChargeBookingCreatedEvent.class));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkBookingCreatedEvent>
        parkBookingCreatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, ParkBookingCreatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(parkBookingCreatedEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkAndChargeBookingCreatedEvent>
        parkAndChargeBookingCreatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, ParkAndChargeBookingCreatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(parkAndChargeBookingCreatedEventConsumerFactory());
        return factory;
    }


    /*---------------------------------------------------------------------*/
    /*Updated Events*/
    public ConsumerFactory<String, ParkBookingUpdatedEvent> parkBookingUpdatedEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(configProps(),
                new StringDeserializer(), new JsonDeserializer<>(ParkBookingUpdatedEvent.class));
    }

    public ConsumerFactory<String, ParkAndChargeBookingUpdatedEvent> parkAndChargeBookingUpdatedEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(configProps(),
                new StringDeserializer(), new JsonDeserializer<>(ParkAndChargeBookingUpdatedEvent.class));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkBookingUpdatedEvent>
        parkBookingUpdatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, ParkBookingUpdatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(parkBookingUpdatedEventConsumerFactory());
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkAndChargeBookingUpdatedEvent>
        parkAndChargeBookingUpdatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, ParkAndChargeBookingUpdatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(parkAndChargeBookingUpdatedEventConsumerFactory());
        return factory;
    }


    /*---------------------------------------------------------------------*/
    /*Canceled or. delete Events*/
    public ConsumerFactory<String, ParkBookingCanceledEvent> parkBookingCanceledEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(configProps(),
                new StringDeserializer(), new JsonDeserializer<>(ParkBookingCanceledEvent.class));
    }

    public ConsumerFactory<String, ParkAndChargeBookingCanceledEvent> parkAndChargeBookingCanceledEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(configProps(),
                new StringDeserializer(), new JsonDeserializer<>(ParkAndChargeBookingCanceledEvent.class));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkBookingCanceledEvent>
        parkBookingCanceledEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, ParkBookingCanceledEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(parkBookingCanceledEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ParkAndChargeBookingCanceledEvent>
        parkAndChargeBookingCanceledEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, ParkAndChargeBookingCanceledEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(parkAndChargeBookingCanceledEventConsumerFactory());
        return  factory;
    }
}
