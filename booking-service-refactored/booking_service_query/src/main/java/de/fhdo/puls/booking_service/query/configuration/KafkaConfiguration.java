package de.fhdo.puls.booking_service.query.configuration;


import de.fhdo.puls.booking_service.common.events.ChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingUpdatedEvent;
import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.group.id}")
    private String defaultGroupId;

    private Map<String,Object> configProps;

    @PostConstruct
    public void init() {
        configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, defaultGroupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    }


    /*** === CREATE EVENTS === */
    public ConsumerFactory<String, ParkBookingCreatedEvent>
        parkBookingCreatedEventConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(ParkBookingCreatedEvent.class)
        );
    }


    public ConsumerFactory<String, ChargeBookingCreatedEvent>
        chargeBookingCreatedEventConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(ChargeBookingCreatedEvent.class)
        );
    }


    public ConsumerFactory<String, InvoiceCreatedEvent>
        invoiceCreatedEventConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(InvoiceCreatedEvent.class)
        );
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,ParkBookingCreatedEvent>
        parkBookingCreatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String,ParkBookingCreatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(parkBookingCreatedEventConsumerFactory());
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,ChargeBookingCreatedEvent>
    chargeBookingCreatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String,ChargeBookingCreatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chargeBookingCreatedEventConsumerFactory());
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InvoiceCreatedEvent>
    invoiceCreatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String,InvoiceCreatedEvent> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(invoiceCreatedEventConsumerFactory());
        return factory;
    }


    /*** === UPDATE EVENTS === */
    public ConsumerFactory<String, ParkBookingUpdatedEvent>
        parkBookingUpdatedEventConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(ParkBookingUpdatedEvent.class)
        );
    }


    public ConsumerFactory<String, ChargeBookingUpdatedEvent>
        chargeBookingUpdatedEventConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(ChargeBookingUpdatedEvent.class)
        );
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,ParkBookingUpdatedEvent>
        parkBookingUpdatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String,ParkBookingUpdatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(parkBookingUpdatedEventConsumerFactory());
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,ChargeBookingUpdatedEvent>
        chargeBookingUpdatedEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String,ChargeBookingUpdatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chargeBookingUpdatedEventConsumerFactory());
        return factory;
    }


    /*** === CANCELED OR DELETE EVENTS === */
    public ConsumerFactory<String, ParkBookingCanceledEvent>
        parkBookingCanceledEventConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(ParkBookingCanceledEvent.class)
        );
    }


    public ConsumerFactory<String, ChargeBookingCanceledEvent>
        chargeBookingCanceledEventConsumerFactory()
    {
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(ChargeBookingCanceledEvent.class)
        );
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
    public ConcurrentKafkaListenerContainerFactory<String, ChargeBookingCanceledEvent>
        chargeBookingCanceledEventKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, ChargeBookingCanceledEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chargeBookingCanceledEventConsumerFactory());
        return factory;
    }
}
