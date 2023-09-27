package de.fhdo.puls.booking_service.command.service;

import de.fhdo.puls.booking_service.command.configuration.DefaultTopicKafkaTemplate;
import de.fhdo.puls.booking_service.command.domain.ParkBookingAggregate;
import de.fhdo.puls.booking_service.command.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.command.repository.ParkBookingRepo;
import de.fhdo.puls.booking_service.common.commands.CancelParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateParkBookingCommand;
import de.fhdo.puls.booking_service.common.dataTransferObjects.ParkBookingDto;
import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ParkBookingService {

    private final ParkBookingRepo parkBookingRepo;
    private final DefaultTopicKafkaTemplate<String,ParkBookingCreatedEvent> createdEventProducer;
    private final DefaultTopicKafkaTemplate<String,ParkBookingUpdatedEvent> updatedEventProducer;
    private final DefaultTopicKafkaTemplate<String,ParkBookingCanceledEvent> canceledEventProducer;


    @Autowired
    public ParkBookingService(ParkBookingRepo parkBookingRepo,
                              DefaultTopicKafkaTemplate<String,ParkBookingCreatedEvent> createdEventProducer,
                              DefaultTopicKafkaTemplate<String,ParkBookingUpdatedEvent> updatedEventProducer,
                              DefaultTopicKafkaTemplate<String,ParkBookingCanceledEvent> canceledEventProducer)
    {
        this.parkBookingRepo = parkBookingRepo;
        this.createdEventProducer = createdEventProducer;
        this.updatedEventProducer = updatedEventProducer;
        this.canceledEventProducer = canceledEventProducer;
    }


    /*** === GET AGGREGATES FROM COMMANDS === */

    private ParkBookingAggregate getAggregateFromCreateCommand(CreateParkBookingCommand command) {
        return new ParkBookingAggregate(
                command.getBookerId(),
                command.getParkingSpaceId(),
                command.getBookingStart(),
                command.getBookingEnd(),
                command.getPricePerHour(),
                false
        );
    }


    private ParkBookingAggregate getAggregateFromUpdateCommand(UpdateParkBookingCommand command) throws BookingNotFoundException {
        Long bookingId = command.getBookingId();
        if (parkBookingRepo.existsById(bookingId)) {
            ParkBookingAggregate aggregate = parkBookingRepo.findById(bookingId).orElseGet(null);
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
                    "ParkBooking with booking_ID " + bookingId + " not found!"
            );
        }
    }


    /*** === GET EVENTS FROM AGGREGATES === */

    private ParkBookingCreatedEvent createEventFromAggregate(ParkBookingAggregate aggregate) {
        return new ParkBookingCreatedEvent(
                aggregate.getId(),
                aggregate.getBookerId(),
                aggregate.getParkingSpaceId(),
                aggregate.getBookingCreated(),
                aggregate.getLastModified(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd(),
                aggregate.getPricePerHour()
        );
    }


    private ParkBookingUpdatedEvent updateEventFromAggregate(ParkBookingAggregate aggregate) {
        return new ParkBookingUpdatedEvent(
                aggregate.getId(),
                aggregate.getLastModified(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd());
    }


    private ParkBookingCanceledEvent cancelEventFromAggregate(ParkBookingAggregate aggregate) {
        return new ParkBookingCanceledEvent(
                aggregate.getId(),
                true);
    }


    /*** === COMMAND HANDLING === */

    public ParkBookingDto handleCreateParkBookingCommand(CreateParkBookingCommand command) {
        ParkBookingAggregate aggregate = getAggregateFromCreateCommand(command);
        aggregate = this.parkBookingRepo.save(aggregate);
        //Send Park-Booking-Created-Event
        ParkBookingCreatedEvent event = createEventFromAggregate(aggregate);
        this.createdEventProducer.send("ParkBookingCreatedTopic",event);

        return new ParkBookingDto(
                aggregate.getId(),
                aggregate.getBookerId(),
                aggregate.getParkingSpaceId(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd(),
                aggregate.getPricePerHour(),
                aggregate.isCanceled(),
                aggregate.getBookingCreated()
        );
    }


    public ParkBookingDto handleUpdateParkBookingCommand(UpdateParkBookingCommand command) throws BookingNotFoundException{
        ParkBookingAggregate aggregate;
        try {
            aggregate = getAggregateFromUpdateCommand(command);
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
        assert aggregate != null;
        aggregate = this.parkBookingRepo.save(aggregate);
        //Send Park-Booking-Updated-Event
        ParkBookingUpdatedEvent event = updateEventFromAggregate(aggregate);
        this.updatedEventProducer.send("ParkBookingUpdatedTopic",event);

        return new ParkBookingDto(
                aggregate.getId(),
                aggregate.getBookerId(),
                aggregate.getParkingSpaceId(),
                aggregate.getBookingStart(),
                aggregate.getBookingEnd(),
                aggregate.getPricePerHour(),
                aggregate.isCanceled(),
                aggregate.getBookingCreated()
        );
    }


    public ParkBookingDto handleCancelParkBookingCommand(Long bookingId) throws BookingNotFoundException{
        //String bookingIdConv = convertBookingIdFromCancelBookingCommand(bookingId);
        if (parkBookingRepo.existsById(bookingId)) {
            var aggregate = parkBookingRepo.findById(bookingId).orElseGet(null);
            parkBookingRepo.deleteById(bookingId);
            //Send Park-Booking-Canceled-Event
            ParkBookingCanceledEvent event = cancelEventFromAggregate(aggregate);
            this.canceledEventProducer.send("ParkBookingCanceledTopic",event);

            return new ParkBookingDto(
                    aggregate.getId(),
                    aggregate.getBookerId(),
                    aggregate.getParkingSpaceId(),
                    aggregate.getBookingStart(),
                    aggregate.getBookingEnd(),
                    aggregate.getPricePerHour(),
                    event.isBookingCanceled(),
                    aggregate.getBookingCreated()
            );
        }
        else {
            throw new BookingNotFoundException(
                    "ParkBooking with booking_ID " + bookingId + " not found!"
            );
        }
    }


    /** ===== method converts a bookingID passed by the frontend (in JSON string format) to a string value.
     *        This is necessary to prevent a parsing error when reading the property value. ===== */
    public String convertBookingIdFromCancelBookingCommand(String bookingIdJson) {
        String jsonLongBookingId = bookingIdJson;
        JSONObject jsonObject = new JSONObject(jsonLongBookingId);
        return jsonObject.getString("bookingId");
    }
}
