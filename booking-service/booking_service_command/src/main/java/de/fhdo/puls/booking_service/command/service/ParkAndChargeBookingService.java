package de.fhdo.puls.booking_service.command.service;

import de.fhdo.puls.booking_service.command.configuration.DefaultTopicKafkaTemplate;
import de.fhdo.puls.booking_service.command.domain.ParkAndChargeBookingAggregate;
import de.fhdo.puls.booking_service.command.domain.ParkBookingAggregate;
import de.fhdo.puls.booking_service.command.exception.ParkAndChargeBookingAlreadyExistsException;
import de.fhdo.puls.booking_service.command.exception.ParkAndChargeBookingNotFoundException;
import de.fhdo.puls.booking_service.command.repository.ParkAndChargeBookingRepository;
import de.fhdo.puls.booking_service.common.commands.CancelParkAndChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateParkAndChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateParkAndChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateParkBookingCommand;
import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


@Service
public class ParkAndChargeBookingService {

    private final ParkAndChargeBookingRepository parkAndChargeBookingRepository;
    private final DefaultTopicKafkaTemplate<String, ParkAndChargeBookingCreatedEvent> bookingCreatedEventProducer;
    private final DefaultTopicKafkaTemplate<String, ParkAndChargeBookingUpdatedEvent> bookingUpdatedEventProducer;
    private final DefaultTopicKafkaTemplate<String, ParkAndChargeBookingCanceledEvent> bookingCanceledEventProducer;


    @Autowired
    public ParkAndChargeBookingService(ParkAndChargeBookingRepository parkAndChargeBookingRepository,
                                       DefaultTopicKafkaTemplate<String, ParkAndChargeBookingCreatedEvent> bookingEventCreatedProducer,
                                       DefaultTopicKafkaTemplate<String, ParkAndChargeBookingUpdatedEvent> bookingEventUpdatedProducer,
                                       DefaultTopicKafkaTemplate<String, ParkAndChargeBookingCanceledEvent> bookingCanceledEventProducer){
        this.parkAndChargeBookingRepository = parkAndChargeBookingRepository;
        this.bookingCreatedEventProducer = bookingEventCreatedProducer;
        this.bookingUpdatedEventProducer = bookingEventUpdatedProducer;
        this.bookingCanceledEventProducer = bookingCanceledEventProducer;
    }


    /*---------------------------------------------------------------------*/
    /*Get aggregates from commands*/
    private ParkAndChargeBookingAggregate getAggregateFromCreateCommand(CreateParkAndChargeBookingCommand command){
        int verifyCode = generateVerifyCode(command.getUserId(),
                command.getParkingSpaceId(),
                command.getStartOfBooking(),
                command.getEndOfBooking());

        return new ParkAndChargeBookingAggregate(command.getUserId(),
                command.getUserName(),
                command.getUserLastName(),
                command.getParkingSpaceId(),
                command.getParkingCity(),
                command.getPostCode(),
                command.getParkingStreet(),
                command.getParkingStreetNumber(),
                command.getStartOfBooking(),
                command.getEndOfBooking(),
                command.getParkingPricePerHour(),
                command.getChargingPricePerKwh(),
                command.getParkingPriceTotal(),
                verifyCode);
    }


    private ParkAndChargeBookingAggregate getAggregateFromUpdateCommand(UpdateParkAndChargeBookingCommand command){
        var aggregate = parkAndChargeBookingRepository.getOne(command.getBookingId());
        if (aggregate != null){
            int verifyCode = generateVerifyCode(aggregate.getUserId(),
                    aggregate.getParkingSpaceId(),
                    command.getStartOfBooking(),
                    command.getEndOfBooking());

            aggregate.setStartOfBooking(command.getStartOfBooking());
            aggregate.setEndOfBooking(command.getEndOfBooking());
            aggregate.setBookingPriceTotal(command.getParkingPriceTotal());
            aggregate.setLastModifiedDate(new Date());
            aggregate.setVerifyCode(verifyCode);
            return aggregate;
        }
        else {
            return null;
        }
    }


    /*---------------------------------------------------------------------*/
    /*Get events from Aggregates*/
    private ParkAndChargeBookingCreatedEvent createdEventFromAggregate(ParkAndChargeBookingAggregate aggregate){
        return new ParkAndChargeBookingCreatedEvent(aggregate.getBookingId(),
                aggregate.getUserId(),
                aggregate.getUserName(),
                aggregate.getUserLastName(),
                aggregate.getParkingSpaceId(),
                aggregate.getParkingCity(),
                aggregate.getPostCode(),
                aggregate.getParkingStreet(),
                aggregate.getParkingStreetNumber(),
                aggregate.getBookingCreatedDate(),
                aggregate.getLastModifiedDate(),
                aggregate.getStartOfBooking(),
                aggregate.getEndOfBooking(),
                aggregate.getBookingPricePerHour(),
                aggregate.getChargingPricePerKwh(),
                aggregate.getBookingPriceTotal(),
                aggregate.getVerifyCode());
    }


    private ParkAndChargeBookingUpdatedEvent updatedEventFromAggregate(ParkAndChargeBookingAggregate aggregate){
        return new ParkAndChargeBookingUpdatedEvent(aggregate.getBookingId(),
                aggregate.getBookingCreatedDate(),
                aggregate.getLastModifiedDate(),
                aggregate.getStartOfBooking(),
                aggregate.getEndOfBooking(),
                aggregate.getBookingPriceTotal(),
                aggregate.getVerifyCode());
    }


    private ParkAndChargeBookingCanceledEvent canceledEventFromAggregate(ParkAndChargeBookingAggregate aggregate){
        return new ParkAndChargeBookingCanceledEvent(aggregate.getBookingId(), true);
    }


    /*---------------------------------------------------------------------*/
    /*Command handling*/
    /* Throws a ParkAndChargeBookingAlreadyExistsException, if the generated booking-ID is already used */
    public void handleCreateParkAndChargeBookingCommand(CreateParkAndChargeBookingCommand command) throws Exception{
        var parkAndChargeBookingAggregate = getAggregateFromCreateCommand(command);
        validateBookingID(parkAndChargeBookingAggregate);
        this.parkAndChargeBookingRepository.save(parkAndChargeBookingAggregate);
        var parkAndChargeBookingCreatedEvent = createdEventFromAggregate(parkAndChargeBookingAggregate);
        this.bookingCreatedEventProducer.send("ParkAndChargeBookingCreatedTopic", parkAndChargeBookingCreatedEvent);
    }


    public void handleUpdateParkAndChargeBookingCommand(UpdateParkAndChargeBookingCommand command){
        var parkAndChargeBookingAggregate = getAggregateFromUpdateCommand(command);
        this.parkAndChargeBookingRepository.save(parkAndChargeBookingAggregate);
        var parkAndChargeBookingUpdatedEvent = updatedEventFromAggregate(parkAndChargeBookingAggregate);
        this.bookingUpdatedEventProducer.send("ParkAndChargeBookingUpdatedTopic", parkAndChargeBookingUpdatedEvent);
    }


    /* Throws a ParkAndChargeBookingNotFoundException, if the booking to be canceled does not exist */
    public void handleCancelParkAndChargeBookingCommand(CancelParkAndChargeBookingCommand command) throws Exception{
        if(parkAndChargeBookingRepository.existsById(command.getBookingId())){
            var parkAndChargeBookingAggregate = parkAndChargeBookingRepository.getOne(command.getBookingId());
            parkAndChargeBookingRepository.deleteById(command.getBookingId());
            var parkAndChargeBookingCanceledEvent = canceledEventFromAggregate(parkAndChargeBookingAggregate);
            this.bookingCanceledEventProducer.send("ParkAndChargeBookingCanceledTopic", parkAndChargeBookingCanceledEvent);
        }
        else{
            throw new ParkAndChargeBookingNotFoundException(command.getBookingId());
        }
    }


    /*---------------------------------------------------------------------*/

    private int generateVerifyCode(Long userId,
                                  String parkingSpaceId,
                                  Date startOfBooking,
                                  Date endOfBooking){
        //METHOD: 	hash(Object... values)
        //Generates a hash code for a sequence of input values
        return Objects.hash(userId, parkingSpaceId, startOfBooking, endOfBooking);
    }


    private void validateBookingID(ParkAndChargeBookingAggregate parkAndChargeBookingAggregate)throws Exception{
        if (parkAndChargeBookingRepository.existsById(parkAndChargeBookingAggregate.getBookingId())){
            throw new ParkAndChargeBookingAlreadyExistsException(parkAndChargeBookingAggregate.getBookingId());
        }
    }
}
