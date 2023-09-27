package de.fhdo.puls.booking_service.command.configuration;

import de.fhdo.puls.booking_service.common.events.ChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingUpdatedEvent;
import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value(value = "${kafka.bootstrapAddress}")
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


    /*** === CREATE EVENTS === */
    @Bean
    public ProducerFactory<String, ParkBookingCreatedEvent>
        parkBookingCreatedEventProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkBookingCreatedEvent>
        parkBookingCreatedEventProducer()
    {
        return new DefaultTopicKafkaTemplate<>(
                parkBookingCreatedEventProducerFactory()
        );
    }

    @Bean
    public ProducerFactory<String, ChargeBookingCreatedEvent>
        chargeBookingCreatedEventProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ChargeBookingCreatedEvent>
        chargeBookingCreatedEventProducer()
    {
        return new DefaultTopicKafkaTemplate<>(
                chargeBookingCreatedEventProducerFactory()
        );
    }

    @Bean
    public ProducerFactory<String, InvoiceCreatedEvent>
        invoiceCreatedEventProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, InvoiceCreatedEvent>
        invoiceCreatedEventProducer()
    {
        return new DefaultTopicKafkaTemplate<>(
                invoiceCreatedEventProducerFactory()
        );
    }


    /*** === UPDATE EVENTS === */
    @Bean
    public ProducerFactory<String, ParkBookingUpdatedEvent>
        parkBookingUpdatedEventProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkBookingUpdatedEvent>
        parkBookingUpdatedEventProducer()
    {
        return new DefaultTopicKafkaTemplate<>(
                parkBookingUpdatedEventProducerFactory()
        );
    }

    @Bean
    public ProducerFactory<String, ChargeBookingUpdatedEvent>
        chargeBookingUpdatedEventProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ChargeBookingUpdatedEvent>
        chargeBookingUpdatedEventProducer()
    {
        return new DefaultTopicKafkaTemplate<>(
                chargeBookingUpdatedEventProducerFactory()
        );
    }


    /*** === CANCELED OR DELETE EVENTS === */
    @Bean
    public ProducerFactory<String, ParkBookingCanceledEvent>
        parkBookingCanceledEventProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ParkBookingCanceledEvent>
        parkBookingCanceledEventProducer()
    {
        return new DefaultTopicKafkaTemplate<>(
                parkBookingCanceledEventProducerFactory()
        );
    }


    @Bean
    public ProducerFactory<String, ChargeBookingCanceledEvent>
        chargeBookingCanceledEventProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(configProps());
    }
    @Bean
    public DefaultTopicKafkaTemplate<String, ChargeBookingCanceledEvent>
        chargeBookingCanceledEventProducer()
    {
        return new DefaultTopicKafkaTemplate<>(
                chargeBookingCanceledEventProducerFactory()
        );
    }
}