package de.fhdo.puls.booking_service.query.service;

import de.fhdo.puls.booking_service.common.dataTransferObjects.ChargeBookingDto;
import de.fhdo.puls.booking_service.common.events.ChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.domain.ChargeBookingAggregate;
import de.fhdo.puls.booking_service.query.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.query.repository.ChargeBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ChargeBookingService {

    private final ChargeBookingRepo chargeBookingRepo;

    @Autowired
    public ChargeBookingService (ChargeBookingRepo chargeBookingRepo) {
        this.chargeBookingRepo = chargeBookingRepo;
    }


    /*** === GET AGGREGATES FROM EVENTS === */
    private ChargeBookingAggregate getAggregateFromCreatedEvent(ChargeBookingCreatedEvent event) {
        return new ChargeBookingAggregate(
                event.getBookingId(),
                event.getBookerId(),
                event.getParkingSpaceId(),
                event.getBookingStart(),
                event.getBookingEnd(),
                event.getParkingPricePerHour(),
                event.getChargingPricePerKWh(),
                event.getVerifyCode(),
                false,
                event.getBookingCreated(),
                event.getLastModified()
        );
    }


    private ChargeBookingAggregate getAggregateFromUpdatedEvent(ChargeBookingUpdatedEvent event,
                                                                ChargeBookingAggregate aggregate) {
        aggregate.setBookingStart(event.getBookingStart());
        aggregate.setBookingEnd(event.getBookingEnd());
        aggregate.setLastModified(event.getLastModified());
        return aggregate;
    }


    private ChargeBookingAggregate getAggregateFromCanceledEvent(ChargeBookingCanceledEvent event,
                                                                 ChargeBookingAggregate aggregate) {
        aggregate.setCanceled(event.isCanceled());
        return aggregate;
    }


    /*** === EVENT HANDLING === */
    public void handleChargeBookingCreatedEvent(ChargeBookingCreatedEvent event) {
        ChargeBookingAggregate aggregate = this.getAggregateFromCreatedEvent(event);
        this.chargeBookingRepo.save(aggregate);
    }


    public void handleChargeBookingUpdatedEvent(ChargeBookingUpdatedEvent event) throws BookingNotFoundException {
        Long bookingId = event.getBookingId();
        ChargeBookingAggregate aggregate = this.chargeBookingRepo.findById(bookingId).orElseGet(null);
        if (aggregate!=null) {
            ChargeBookingAggregate newAggregate = this.getAggregateFromUpdatedEvent(event,aggregate);
            this.chargeBookingRepo.save(newAggregate);
        }
        else {
            throw new BookingNotFoundException(
                    "ChargeBooking with booking_ID " + bookingId + " not found!"
            );
        }
    }


    public void handleChargeBookingCanceledEvent(ChargeBookingCanceledEvent event) throws BookingNotFoundException {
        Long bookingId = event.getBookingId();
        ChargeBookingAggregate aggregate = this.chargeBookingRepo.findById(bookingId).orElseGet(null);
        if (aggregate!=null) {
            ChargeBookingAggregate newAggregate = this.getAggregateFromCanceledEvent(event,aggregate);
            this.chargeBookingRepo.save(newAggregate);
        }
        else {
            throw new BookingNotFoundException(
                    "ChargeBooking with booking_ID " + bookingId + " not found!"
            );
        }
    }


    /*** === GET DATA TRANSFER OBJECT (DTO) FROM AGGREGATE === */
    public ChargeBookingDto getDtoFromAggregate(ChargeBookingAggregate aggregate) {
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


    /*** === QUERY HANDLING === */
    public List<ChargeBookingDto> getAllChargeBookings(){
        List<ChargeBookingDto> chargeBookings = new LinkedList<>();
        List<ChargeBookingAggregate> aggregates = this.chargeBookingRepo.findAll();
        aggregates.forEach(aggregate -> chargeBookings.add(getDtoFromAggregate(aggregate)));
        return chargeBookings;
    }


    public List<ChargeBookingDto> getActualBookingsOfParkingSpace(String parkingSpaceId) {
        List<ChargeBookingDto> chargeBookings = new LinkedList<>();
        List<ChargeBookingAggregate> aggregates = this.chargeBookingRepo.findAll();
        Stream<ChargeBookingAggregate> actualChargeBookings = aggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == false
        );
        actualChargeBookings.forEach(aggregate -> {
            if (aggregate.getParkingSpaceId().equals(parkingSpaceId)) {
                chargeBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return chargeBookings;
    }


    public List<ChargeBookingDto> getCanceledBookingsOfParkingSpace(String parkingSpaceId) {
        List<ChargeBookingDto> chargeBookings = new LinkedList<>();
        List<ChargeBookingAggregate> aggregates = this.chargeBookingRepo.findAll();
        Stream<ChargeBookingAggregate> canceledChargeBookings = aggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == true
        );
        canceledChargeBookings.forEach(aggregate -> {
            if (aggregate.getParkingSpaceId().equals(parkingSpaceId)) {
                chargeBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return chargeBookings;
    }


    public List<ChargeBookingDto> getActualBookingsFromBooker(String bookerId) {
        List<ChargeBookingDto> chargeBookings = new LinkedList<>();
        List<ChargeBookingAggregate> aggregates = this.chargeBookingRepo.findAll();
        Stream<ChargeBookingAggregate> actualChargeBookings = aggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == false
        );
        actualChargeBookings.forEach(aggregate -> {
            if (aggregate.getBookerId().equals(bookerId)){
                chargeBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return chargeBookings;
    }


    public List<ChargeBookingDto> getCanceledBookingsFromBooker(String bookerId) {
        List<ChargeBookingDto> chargeBookings = new LinkedList<>();
        List<ChargeBookingAggregate> aggregates = this.chargeBookingRepo.findAll();
        Stream<ChargeBookingAggregate> canceledChargeBookings = aggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == true
        );
        canceledChargeBookings.forEach(aggregate -> {
            if (aggregate.getBookerId().equals(bookerId)){
                chargeBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return chargeBookings;
    }
}
