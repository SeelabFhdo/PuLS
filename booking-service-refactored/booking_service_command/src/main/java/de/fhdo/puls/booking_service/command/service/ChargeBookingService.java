package de.fhdo.puls.booking_service.command.service;

import de.fhdo.puls.booking_service.command.configuration.DefaultTopicKafkaTemplate;
import de.fhdo.puls.booking_service.command.domain.ChargeBookingAggregate;
import de.fhdo.puls.booking_service.command.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.command.repository.ChargeBookingRepo;
import de.fhdo.puls.booking_service.common.commands.CancelChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateChargeBookingCommand;
import de.fhdo.puls.booking_service.common.dataTransferObjects.ChargeBookingDto;
import de.fhdo.puls.booking_service.common.events.ChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingUpdatedEvent;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


@Service
public class ChargeBookingService {

    private final ChargeBookingRepo chargeBookingRepo;
    private final DefaultTopicKafkaTemplate<String,ChargeBookingCreatedEvent> createdEventProducer;
    private final DefaultTopicKafkaTemplate<String,ChargeBookingUpdatedEvent> updatedEventProducer;
    private final DefaultTopicKafkaTemplate<String,ChargeBookingCanceledEvent> canceledEventProducer;



    @Autowired
    public ChargeBookingService(ChargeBookingRepo chargeBookingRepo,
                                DefaultTopicKafkaTemplate<String,ChargeBookingCreatedEvent> createdEventProducer,
                                DefaultTopicKafkaTemplate<String,ChargeBookingUpdatedEvent> updatedEventProducer,
                                DefaultTopicKafkaTemplate<String,ChargeBookingCanceledEvent> canceledEventProducer)
    {
        this.chargeBookingRepo = chargeBookingRepo;
        this.createdEventProducer = createdEventProducer;
        this.updatedEventProducer = updatedEventProducer;
        this.canceledEventProducer = canceledEventProducer;
    }


    /*** === GENERATE VERIFY-CODE === */

    private int generateVerifyCode(String bookerId,
                                   String parkingSpaceId,
                                   Date bookingStart,
                                   Date bookingEnd)
    {
        //METHOD: 	hash(Object... values)
        //Generates a hash code for a sequence of input values
        int hash = Objects.hash(bookerId,parkingSpaceId,bookingStart,bookingEnd);
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }


    /*** === GET AGGREGATES FROM COMMANDS === */

    private ChargeBookingAggregate getAggregateFromCreateCommand(CreateChargeBookingCommand command) {
        int verifyCode = generateVerifyCode(
                command.getBookerId(),
                command.getParkingSpaceId(),
                command.getBookingStart(),
                command.getBookingEnd()
        );

        return new ChargeBookingAggregate(
                command.getBookerId(),
                command.getParkingSpaceId(),
                command.getBookingStart(),
                command.getBookingEnd(),
                command.getParkingPricePerHour(),
                command.getChargingPricePerKWh(),
                verifyCode
        );
    }


    private ChargeBookingAggregate getAggregateFromUpdateCommand(UpdateChargeBookingCommand command) throws BookingNotFoundException {
        Long bookingId = command.getBookingId();
        if(chargeBookingRepo.existsById(bookingId)) {
            ChargeBookingAggregate aggregate = chargeBookingRepo.findById(bookingId).orElseGet(null);
            if (aggregate != null) {
                aggregate.setBookingStart(command.getBookingStart());
                aggregate.setBookingEnd(command.getBookingEnd());
                aggregate.setLastModified(new Date());
                return aggregate;
            }
            else {
                return null;
            }
        }
        else {
            throw new BookingNotFoundException(
                    "ChargeBooking with booking_ID " + bookingId + " not found!"
            );
        }
    }


    /*** === GET EVENTS FROM AGGREGATES === */

    private ChargeBookingCreatedEvent createEventFromAggregate(ChargeBookingAggregate aggregate) {
        return new ChargeBookingCreatedEvent(
                aggregate.getId(),
                aggregate.getBookerId(),
                aggregate.getParkingSpaceId(),
                aggregate.getLastModified(),
                aggregate.getBookingCreated(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd(),
                aggregate.getPricePerHour(),
                aggregate.getPricePerKWh(),
                aggregate.getVerifyCode()
        );
    }


    private ChargeBookingUpdatedEvent updateEventFromAggregate(ChargeBookingAggregate aggregate) {
        return new ChargeBookingUpdatedEvent(
                aggregate.getId(),
                aggregate.getLastModified(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd(),
                aggregate.getVerifyCode()
        );
    }


    public ChargeBookingCanceledEvent cancelEventFromAggregate(ChargeBookingAggregate aggregate) {
        return new ChargeBookingCanceledEvent(
                aggregate.getId(),
                true
        );
    }


    /*** === COMMAND HANDLING === */

    public ChargeBookingDto handleCreateChargeBookingCommand(CreateChargeBookingCommand command){
        ChargeBookingAggregate aggregate = getAggregateFromCreateCommand(command);
        aggregate = this.chargeBookingRepo.save(aggregate);
        //Send Charge-Booking-Created-Event
        ChargeBookingCreatedEvent event = createEventFromAggregate(aggregate);
        this.createdEventProducer.send("ChargeBookingCreatedTopic",event);

        return new ChargeBookingDto(
                aggregate.getId(),
                aggregate.getBookerId(),
                aggregate.getParkingSpaceId(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd(),
                aggregate.getPricePerHour(),
                aggregate.getPricePerKWh(),
                aggregate.getVerifyCode(),
                aggregate.isCanceled(),
                aggregate.getBookingCreated()
        );
    }


    public ChargeBookingDto handleUpdateChargeBookingCommand(UpdateChargeBookingCommand command) throws BookingNotFoundException {
        ChargeBookingAggregate aggregate;
        try {
            aggregate = getAggregateFromUpdateCommand(command);
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
        assert aggregate != null;
        aggregate = this.chargeBookingRepo.save(aggregate);
        //Send Charge-Booking-Updated-Event
        ChargeBookingUpdatedEvent event = updateEventFromAggregate(aggregate);
        this.updatedEventProducer.send("ChargeBookingUpdatedTopic",event);

        return new ChargeBookingDto(
                aggregate.getId(),
                aggregate.getBookerId(),
                aggregate.getParkingSpaceId(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd(),
                aggregate.getPricePerHour(),
                aggregate.getPricePerKWh(),
                aggregate.getVerifyCode(),
                aggregate.isCanceled(),
                aggregate.getBookingCreated()
        );
    }


    public ChargeBookingDto handleCancelChargeBookingCommand(Long bookingId) throws BookingNotFoundException {
        //Long bookingId = convertBookingIdFromCancelBookingCommand(command.getBookingId());
        //Long bookingId =  command.getBookingId();
        if (this.chargeBookingRepo.existsById(bookingId)) {
            var aggregate = this.chargeBookingRepo.findById(bookingId).orElseGet(null);
            chargeBookingRepo.deleteById(bookingId);
            //Send Charge-Booking-Canceled-Event
            ChargeBookingCanceledEvent event = cancelEventFromAggregate(aggregate);
            this.canceledEventProducer.send("ChargeBookingCanceledTopic",event);

            return new ChargeBookingDto(
                    aggregate.getId(),
                    aggregate.getBookerId(),
                    aggregate.getParkingSpaceId(),
                    aggregate.getBookingStart(),
                    aggregate.getBookingEnd(),
                    aggregate.getPricePerHour(),
                    aggregate.getPricePerKWh(),
                    aggregate.getVerifyCode(),
                    event.isCanceled(),
                    aggregate.getBookingCreated()
            );
        }
        else {
            throw new BookingNotFoundException(
                    "ChargeBooking with booking_ID " + bookingId + " not found!"
            );
        }
    }


    /** ===== method converts a bookingID passed by the frontend (in JSON string format) to a string value.
     *        This is necessary to prevent a parsing error when reading the property value. ===== */
    public Long convertBookingIdFromCancelBookingCommand(Long bookingIdJson) {
        Long jsonLongBookingId = bookingIdJson;
        JSONObject jsonObject = new JSONObject(jsonLongBookingId);
        return jsonObject.getLong("bookingId");
    }
}
