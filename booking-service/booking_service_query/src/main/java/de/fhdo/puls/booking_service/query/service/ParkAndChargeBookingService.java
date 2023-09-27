package de.fhdo.puls.booking_service.query.service;

import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.domain.ParkAndChargeBooking;
import de.fhdo.puls.booking_service.query.domain.ParkAndChargeBookingAggregate;
import de.fhdo.puls.booking_service.query.repository.ParkAndChargeBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParkAndChargeBookingService {

    private final ParkAndChargeBookingRepository parkAndChargeBookingRepository;

    @Autowired
    public ParkAndChargeBookingService(ParkAndChargeBookingRepository parkAndChargeBookingRepository){
        this.parkAndChargeBookingRepository = parkAndChargeBookingRepository;
    }


    /*---------------------------------------------------------------------*/
    /*Aggregate from event*/
    public ParkAndChargeBookingAggregate getAggregateFromCreatedEvent(ParkAndChargeBookingCreatedEvent parkAndChargeBookingCreatedEvent){
        return new ParkAndChargeBookingAggregate(parkAndChargeBookingCreatedEvent.getBookingId(),
                parkAndChargeBookingCreatedEvent.getUserId(),
                parkAndChargeBookingCreatedEvent.getUserName(),
                parkAndChargeBookingCreatedEvent.getUserLastName(),
                parkAndChargeBookingCreatedEvent.getParkingSpaceId(),
                parkAndChargeBookingCreatedEvent.getParkingCity(),
                parkAndChargeBookingCreatedEvent.getPostCode(),
                parkAndChargeBookingCreatedEvent.getParkingStreet(),
                parkAndChargeBookingCreatedEvent.getParkingStreetNumber(),
                parkAndChargeBookingCreatedEvent.getStartOfBooking(),
                parkAndChargeBookingCreatedEvent.getEndOfBooking(),
                parkAndChargeBookingCreatedEvent.getParkingPricePerHour(),
                parkAndChargeBookingCreatedEvent.getChargingPricePerKwh(),
                parkAndChargeBookingCreatedEvent.getGetParkingPriceTotal(),
                parkAndChargeBookingCreatedEvent.getVerifyCode(),
                parkAndChargeBookingCreatedEvent.getBookingCreatedDate(),
                parkAndChargeBookingCreatedEvent.getLastModifiedDate(),
                false);
    }


    public ParkAndChargeBookingAggregate getAggregateFromUpdatedEvent(ParkAndChargeBookingAggregate parkAndChargeBookingAggregate,
                                                                      ParkAndChargeBookingUpdatedEvent parkAndChargeBookingUpdatedEvent){
        parkAndChargeBookingAggregate.setStartOfBooking(parkAndChargeBookingUpdatedEvent.getStartOfBooking());
        parkAndChargeBookingAggregate.setEndOfBooking(parkAndChargeBookingUpdatedEvent.getEndOfBooking());
        parkAndChargeBookingAggregate.setBookingPriceTotal(parkAndChargeBookingUpdatedEvent.getParkingPriceTotal());
        parkAndChargeBookingAggregate.setVerifyCode(parkAndChargeBookingUpdatedEvent.getVerifyCode());
        parkAndChargeBookingAggregate.setLastModifiedDate(new Date());
        return parkAndChargeBookingAggregate;
    }


    public ParkAndChargeBookingAggregate getAggregateFromCanceledEvent(ParkAndChargeBookingAggregate parkAndChargeBookingAggregate,
                                                                       ParkAndChargeBookingCanceledEvent parkAndChargeBookingCanceledEvent){
        parkAndChargeBookingAggregate.setBookingCanceled(parkAndChargeBookingCanceledEvent.isBookingCanceled());
        return parkAndChargeBookingAggregate;
    }


    /*---------------------------------------------------------------------*/
    /*Event handling*/
    public void handleParkAndChargeBookingCreatedEvent(ParkAndChargeBookingCreatedEvent parkAndChargeBookingCreatedEvent){
        var parkAndChargeBookingAggregate = getAggregateFromCreatedEvent(parkAndChargeBookingCreatedEvent);
            this.parkAndChargeBookingRepository.save(parkAndChargeBookingAggregate);
    }


    public void handleParkAndChargeBookingUpdatedEvent(ParkAndChargeBookingUpdatedEvent parkAndChargeBookingUpdatedEvent){
        var parkAndChargeBookingAggregate =
                this.parkAndChargeBookingRepository.getOne(parkAndChargeBookingUpdatedEvent.getBookingId());

        if (parkAndChargeBookingAggregate != null){
            this.parkAndChargeBookingRepository.save(this.getAggregateFromUpdatedEvent(parkAndChargeBookingAggregate,parkAndChargeBookingUpdatedEvent));
        }
    }


    public void handleParkAndChargeBookingCanceledEvent(ParkAndChargeBookingCanceledEvent parkAndChargeBookingCanceledEvent){
        var parkAndChargeBookingAggregate =
                this.parkAndChargeBookingRepository.getOne(parkAndChargeBookingCanceledEvent.getBookingId());
        if (parkAndChargeBookingAggregate != null){
            this.parkAndChargeBookingRepository.save(this.getAggregateFromCanceledEvent(parkAndChargeBookingAggregate,parkAndChargeBookingCanceledEvent));
        }
    }


    /*---------------------------------------------------------------------*/
    /*Value object from aggregate*/
    public ParkAndChargeBooking getValueObjectFromAggregate(ParkAndChargeBookingAggregate parkAndChargeBookingAggregate){
        return new ParkAndChargeBooking(parkAndChargeBookingAggregate.getUserId(),
                parkAndChargeBookingAggregate.getUserName(),
                parkAndChargeBookingAggregate.getUserLastName(),
                parkAndChargeBookingAggregate.getParkingSpaceId(),
                parkAndChargeBookingAggregate.getParkingCity(),
                parkAndChargeBookingAggregate.getPostCode(),
                parkAndChargeBookingAggregate.getParkingStreet(),
                parkAndChargeBookingAggregate.getParkingStreetNumber(),
                parkAndChargeBookingAggregate.getStartOfBooking(),
                parkAndChargeBookingAggregate.getEndOfBooking(),
                parkAndChargeBookingAggregate.getBookingPricePerHour(),
                parkAndChargeBookingAggregate.getChargingPricePerKwh(),
                parkAndChargeBookingAggregate.getBookingPriceTotal(),
                parkAndChargeBookingAggregate.getVerifyCode(),
                parkAndChargeBookingAggregate.isBookingCanceled());
    }

    /*---------------------------------------------------------------------*/

    public List<ParkAndChargeBooking> getAllParkAndChargeBookings(){
        var parkAndChargeBookingsList = new LinkedList<ParkAndChargeBooking>();
        var parkAndChargeBookingsAggregatesList = this.parkAndChargeBookingRepository.findAll();

        parkAndChargeBookingsAggregatesList.forEach(parkAndChargeBookingAggregate ->
                parkAndChargeBookingsList.add(this.getValueObjectFromAggregate(parkAndChargeBookingAggregate)));
        return parkAndChargeBookingsList;
    }


    public List<ParkAndChargeBooking> getCurrentBookingsOfParkingSpace(String parkingSpaceId){
        var parkBookingsOfParkingSpace = new LinkedList<ParkAndChargeBooking>();
        var currentParkBookingsAggregateList =
                this.parkAndChargeBookingRepository.findAllByBookingCanceledIsFalse(false);

        currentParkBookingsAggregateList.forEach(parkAndChargeBookingAggregate ->
        {
            if (parkAndChargeBookingAggregate.getParkingSpaceId() == parkingSpaceId){
                parkBookingsOfParkingSpace.add(this.getValueObjectFromAggregate(parkAndChargeBookingAggregate));
            }
        });

        return parkBookingsOfParkingSpace;
    }


    public List<ParkAndChargeBooking> getCanceledBookingsOfParkingSpace(String parkingSpaceId){
        var canceledBookingsOfParkingSpace = new LinkedList<ParkAndChargeBooking>();
        var canceledBookingAggregatesList = this.parkAndChargeBookingRepository.findAllByBookingCanceledIsTrue(true);

        canceledBookingAggregatesList.forEach(parkAndChargeBookingAggregate ->
        {
            if (parkAndChargeBookingAggregate.getParkingSpaceId() == parkingSpaceId){
                canceledBookingsOfParkingSpace.add(this.getValueObjectFromAggregate(parkAndChargeBookingAggregate));
            }
        });

        return canceledBookingsOfParkingSpace;
    }
}
