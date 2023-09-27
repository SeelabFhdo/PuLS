package de.fhdo.puls.booking_service.query.service;

import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.domain.ParkBooking;
import de.fhdo.puls.booking_service.query.domain.ParkBookingAggregate;
import de.fhdo.puls.booking_service.query.repository.ParkBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParkBookingService {

    private final ParkBookingRepository parkBookingRepository;

    @Autowired
    public ParkBookingService(ParkBookingRepository parkBookingRepository){
        this.parkBookingRepository = parkBookingRepository;
    }


    /*---------------------------------------------------------------------*/
    /*Aggregates from Events*/
    private ParkBookingAggregate getAggregateFromCreatedEvent(ParkBookingCreatedEvent parkBookingCreatedEvent){
        return new ParkBookingAggregate(parkBookingCreatedEvent.getBookingId(),
                parkBookingCreatedEvent.getUserId(),
                parkBookingCreatedEvent.getUserName(),
                parkBookingCreatedEvent.getUserLastName(),
                parkBookingCreatedEvent.getParkingSpaceId(),
                parkBookingCreatedEvent.getParkingCity(),
                parkBookingCreatedEvent.getPostCode(),
                parkBookingCreatedEvent.getParkingStreet(),
                parkBookingCreatedEvent.getParkingStreetNumber(),
                parkBookingCreatedEvent.getStartOfBooking(),
                parkBookingCreatedEvent.getEndOfBooking(),
                parkBookingCreatedEvent.getParkingPricePerHour(),
                parkBookingCreatedEvent.getGetParkingPriceTotal(),
                parkBookingCreatedEvent.getBookingCreatedDate(),
                parkBookingCreatedEvent.getLastModifiedDate(),
                false);
    }


    private ParkBookingAggregate getAggregateFromUpdatedEvent(ParkBookingAggregate parkBookingAggregate,
                                                             ParkBookingUpdatedEvent parkBookingUpdatedEvent){
        parkBookingAggregate.setStartOfBooking(parkBookingUpdatedEvent.getStartOfBooking());
        parkBookingAggregate.setEndOfBooking(parkBookingUpdatedEvent.getEndOfBooking());
        parkBookingAggregate.setBookingPriceTotal(parkBookingUpdatedEvent.getParkingPriceTotal());
        parkBookingAggregate.setLastModifiedDate(new Date());
        return parkBookingAggregate;
    }


    private ParkBookingAggregate getAggregateFromCanceledEvent(ParkBookingAggregate parkBookingAggregate,
                                                               ParkBookingCanceledEvent parkBookingCanceledEvent){
        parkBookingAggregate.setBookingCanceled(parkBookingCanceledEvent.isBookingCanceled());
        return parkBookingAggregate;
    }


    /*---------------------------------------------------------------------*/
    /*Event handling*/
    public void handleParkBookingCreatedEvent(ParkBookingCreatedEvent parkBookingCreatedEvent){
        var parkBookingAggregate = getAggregateFromCreatedEvent(parkBookingCreatedEvent);
        this.parkBookingRepository.save(parkBookingAggregate);
    }


    public  void handleParkBookingUpdatedEvent(ParkBookingUpdatedEvent parkBookingUpdatedEvent){
        var parkBookingAggregate = this.parkBookingRepository.getOne(parkBookingUpdatedEvent.getBookingId());
        if(parkBookingAggregate != null){
            this.parkBookingRepository.save(this.getAggregateFromUpdatedEvent(parkBookingAggregate, parkBookingUpdatedEvent));
        }
    }


    public void handleParkBookingCanceledEvent(ParkBookingCanceledEvent parkBookingCanceledEvent){
        var parkBookingAggregate = parkBookingRepository.getOne(parkBookingCanceledEvent.getBookingId());
        if (parkBookingAggregate != null){
            this.parkBookingRepository.save(this.getAggregateFromCanceledEvent(parkBookingAggregate, parkBookingCanceledEvent));
        }
    }


    /*---------------------------------------------------------------------*/
    /*Value object from aggregate*/
    public ParkBooking getValueObjectFromAggregate(ParkBookingAggregate parkBookingAggregate) {
        return new ParkBooking(parkBookingAggregate.getUserId(),
                parkBookingAggregate.getUserName(),
                parkBookingAggregate.getUserLastName(),
                parkBookingAggregate.getParkingSpaceId(),
                parkBookingAggregate.getParkingCity(),
                parkBookingAggregate.getPostCode(),
                parkBookingAggregate.getParkingStreet(),
                parkBookingAggregate.getParkingStreetNumber(),
                parkBookingAggregate.getStartOfBooking(),
                parkBookingAggregate.getEndOfBooking(),
                parkBookingAggregate.getBookingPricePerHour(),
                parkBookingAggregate.getBookingPriceTotal(),
                parkBookingAggregate.isBookingCanceled());
    }

    /*---------------------------------------------------------------------*/

    public List<ParkBooking> getAllParkBookings(){
        var parkBookingsList = new LinkedList<ParkBooking>();
        var parkBookingAggregateList = this.parkBookingRepository.findAll();
        parkBookingAggregateList.forEach(parkBookingAggregate ->
                parkBookingsList.add(this.getValueObjectFromAggregate(parkBookingAggregate)));
        return parkBookingsList;
    }


    public List<ParkBooking> getCurrentBookingsOfParkingSpace(String parkingSpaceId){
        var parkBookingsOfParkingSpace = new LinkedList<ParkBooking>();
        var currentParkBookingsAggregateList = this.parkBookingRepository.findAllByBookingCanceledIsFalse(false);

        currentParkBookingsAggregateList.forEach(parkBookingAggregate ->
        {
            if (parkBookingAggregate.getParkingSpaceId() == parkingSpaceId){
                parkBookingsOfParkingSpace.add(this.getValueObjectFromAggregate(parkBookingAggregate));
            }
        });

        return parkBookingsOfParkingSpace;
    }


    public List<ParkBooking> getCanceledBookingsOfParkingSpace(String parkingSpaceId){
        var canceledBookingsOfParkingSpace = new LinkedList<ParkBooking>();
        var canceledBookingAggregatesList = this.parkBookingRepository.findAllByBookingCanceledIsTrue(true);

        canceledBookingAggregatesList.forEach(parkBookingAggregate ->
        {
            if(parkBookingAggregate.getParkingSpaceId() == parkingSpaceId){
                canceledBookingsOfParkingSpace.add(this.getValueObjectFromAggregate(parkBookingAggregate));
            }
        });

        return canceledBookingsOfParkingSpace;
    }
}
