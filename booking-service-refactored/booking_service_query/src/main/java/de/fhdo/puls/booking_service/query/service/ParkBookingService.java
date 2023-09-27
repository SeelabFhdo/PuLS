package de.fhdo.puls.booking_service.query.service;

import de.fhdo.puls.booking_service.common.dataTransferObjects.ParkBookingDto;
import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.domain.ParkBookingAggregate;
import de.fhdo.puls.booking_service.query.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.query.repository.ParkBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ParkBookingService {

    private final ParkBookingRepo parkBookingRepo;


    @Autowired
    public ParkBookingService(ParkBookingRepo parkBookingRepo) {
        this.parkBookingRepo = parkBookingRepo;
    }


    /*** === GET AGGREGATES FROM EVENTS === */
    private ParkBookingAggregate getAggregateFromCreatedEvent(ParkBookingCreatedEvent event) {
        return new ParkBookingAggregate(
                event.getBookingId(),
                event.getBookerId(),
                event.getParkingSpaceId(),
                event.getBookingStart(),
                event.getBookingEnd(),
                event.getPricePerHour(),
                false,
                event.getBookingCreated(),
                event.getLastModified()
        );
    }


    private ParkBookingAggregate getAggregateFromUpdatedEvent(ParkBookingUpdatedEvent event,
                                                              ParkBookingAggregate aggregate) {
        aggregate.setBookingStart(event.getBookingStart());
        aggregate.setBookingEnd(event.getBookingEnd());
        aggregate.setLastModified(event.getLastModified());
        return aggregate;
    }


    private ParkBookingAggregate getAggregateFromCanceledEvent(ParkBookingCanceledEvent event,
                                                               ParkBookingAggregate aggregate) {
        aggregate.setCanceled(event.isBookingCanceled());
        return aggregate;
    }


    /*** === EVENT HANDLING === */
    public void handleParkBookingCreatedEvent(ParkBookingCreatedEvent event) {
        ParkBookingAggregate aggregate = this.getAggregateFromCreatedEvent(event);
        this.parkBookingRepo.save(aggregate);
    }


    public void handleParkBookingUpdatedEvent(ParkBookingUpdatedEvent event) throws BookingNotFoundException {
        Long bookingId = event.getBookingId();
        ParkBookingAggregate aggregate = this.parkBookingRepo.findById(bookingId).orElseGet(null);
        if (aggregate != null) {
            ParkBookingAggregate newAggregate = this.getAggregateFromUpdatedEvent(event,aggregate);
            this.parkBookingRepo.save(newAggregate);
        }
        else {
            throw new BookingNotFoundException(
                    "ParkBooking with booking_ID " + bookingId + " not found!"
            );
        }
    }


    public void handleParkBookingCanceledEvent(ParkBookingCanceledEvent event) throws BookingNotFoundException {
        Long bookingId = event.getBookingId();
        ParkBookingAggregate aggregate = this.parkBookingRepo.findById(bookingId).orElseGet(null);
        if (aggregate != null) {
            //Call the AggregateFromEvent method which sets isCanceled = true
            ParkBookingAggregate newAggregate = this.getAggregateFromCanceledEvent(event,aggregate);
            this.parkBookingRepo.save(newAggregate);
        }
        else {
            throw new BookingNotFoundException(
                    "ParkBooking with booking_ID + " + event.getBookingId() + " not found!"
            );
        }
    }


    /*** === GET DATA TRANSFER OBJECT (DTO) FROM AGGREGATE === */
    public ParkBookingDto getDtoFromAggregate(ParkBookingAggregate aggregate) {
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


    /*** === QUERY HANDLING === */
    public List<ParkBookingDto> getAllParkBookings() {
        List<ParkBookingDto> parkBookings = new LinkedList<>();
        List<ParkBookingAggregate> parkBookingAggregates = this.parkBookingRepo.findAll();
        parkBookingAggregates.forEach(aggregate ->
                parkBookings.add(this.getDtoFromAggregate(aggregate)));
        return parkBookings;
    }


    public List<ParkBookingDto> getActualBookingsOfParkingSpace(String parkingSpaceId) {
        List<ParkBookingDto> parkBookings = new LinkedList<>();
        List<ParkBookingAggregate> parkBookingAggregates = this.parkBookingRepo.findAll();
        Stream<ParkBookingAggregate> actualParkBookings = parkBookingAggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == false
        );
        actualParkBookings.forEach(aggregate -> {
            if (aggregate.getParkingSpaceId().equals(parkingSpaceId)){
                parkBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return parkBookings;
    }


    public List<ParkBookingDto> getCanceledBookingsOfParkingSpace(String parkingSpaceId) {
        List<ParkBookingDto> parkBookings = new LinkedList<>();
        List<ParkBookingAggregate> parkBookingAggregates = this.parkBookingRepo.findAll();
        Stream<ParkBookingAggregate> canceledParkBookings = parkBookingAggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == true
        );
        canceledParkBookings.forEach(aggregate -> {
            if (aggregate.getParkingSpaceId().equals(parkingSpaceId)) {
                parkBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return parkBookings;
    }


    public List<ParkBookingDto> getActualBookingsFromBooker(String bookerId) {
        List<ParkBookingDto> parkBookings = new LinkedList<>();
        List<ParkBookingAggregate> parkBookingAggregates = this.parkBookingRepo.findAll();
        Stream<ParkBookingAggregate> actualPerkBookings = parkBookingAggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == false
        );
        actualPerkBookings.forEach(aggregate -> {
            if (aggregate.getBookerId().equals(bookerId)) {
                parkBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return parkBookings;
    }


    public List<ParkBookingDto> getCanceledBookingsFromBooker(String bookerId) {
        List<ParkBookingDto> parkBookings = new LinkedList<>();
        List<ParkBookingAggregate> parkBookingAggregates = this.parkBookingRepo.findAll();
        Stream<ParkBookingAggregate> canceledParkBookings = parkBookingAggregates.stream().filter(
                aggregate -> aggregate.isCanceled() == true
        );
        canceledParkBookings.forEach(aggregate -> {
            if (aggregate.getBookerId().equals(bookerId)){
                parkBookings.add(this.getDtoFromAggregate(aggregate));
            }
        });
        return parkBookings;
    }
}
