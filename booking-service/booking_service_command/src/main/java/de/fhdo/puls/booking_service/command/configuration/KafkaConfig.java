package de.fhdo.puls.booking_service.command.configuration;

import de.fhdo.puls.booking_service.common.events.*;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrapAddess}")
    private String bootstrapAddress;


    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        return new KafkaAdmin(configs);
    }


    @Bean
    public Map<String, Object> configProps(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }


    /*---------------------------------------------------------------------*/
    /*Created Events*/
    @Bean
    public ProducerFactory<String, ParkBookingCreatedEvent> parkBookingCreatedEventProducerFactory(){
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkBookingCreatedEvent> parkBookingCreatedEventProducer(){
        return new DefaultTopicKafkaTemplate<>(parkBookingCreatedEventProducerFactory());
    }


    @Bean
    public ProducerFactory<String, ParkAndChargeBookingCreatedEvent> parkAndChargeBookingCreatedEventProducerFactory(){
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkAndChargeBookingCreatedEvent> parkAndChargeBookingCreatedEventProducer(){
        return new DefaultTopicKafkaTemplate<>(parkAndChargeBookingCreatedEventProducerFactory());
    }


    /*---------------------------------------------------------------------*/
    /*Updated Events*/
    @Bean
    public ProducerFactory<String, ParkBookingUpdatedEvent> parkBookingUpdatedEventProducerFactory(){
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkBookingUpdatedEvent> parkBookingUpdatedEventProducer(){
        return new DefaultTopicKafkaTemplate<>(parkBookingUpdatedEventProducerFactory());
    }


    @Bean ProducerFactory<String, ParkAndChargeBookingUpdatedEvent> parkAndChargeBookingUpdatedEventProducerFactory(){
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkAndChargeBookingUpdatedEvent> parkAndChargeBookingUpdatedEventProducer(){
        return new DefaultTopicKafkaTemplate<>(parkAndChargeBookingUpdatedEventProducerFactory());
    }


    /*---------------------------------------------------------------------*/
    /*Canceled or. Delete Events*/
    @Bean
    public ProducerFactory<String, ParkBookingCanceledEvent> parkBookingCanceledEventProducerFactory(){
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkBookingCanceledEvent> parkBookingCanceledEventProducer(){
        return new DefaultTopicKafkaTemplate<>(parkBookingCanceledEventProducerFactory());
    }


    @Bean
    public ProducerFactory<String, ParkAndChargeBookingCanceledEvent> parkAndChargeBookingCanceledEventProducerFactory(){
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkAndChargeBookingCanceledEvent> parkAndChargeBookingCanceledEventProducer(){
        return new DefaultTopicKafkaTemplate<>(parkAndChargeBookingCanceledEventProducerFactory());
    }
}
