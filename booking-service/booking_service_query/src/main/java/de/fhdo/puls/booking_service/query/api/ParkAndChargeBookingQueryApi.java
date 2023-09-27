package de.fhdo.puls.booking_service.query.api;

import de.fhdo.puls.booking_service.query.domain.ParkAndChargeBooking;
import de.fhdo.puls.booking_service.query.repository.ParkAndChargeBookingRepository;
import de.fhdo.puls.booking_service.query.service.ParkAndChargeBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("(/resources/v1")
@CrossOrigin(origins = "${cors.origins}", allowedHeaders = "*", methods =
        {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD})
public class ParkAndChargeBookingQueryApi {

    private ParkAndChargeBookingService parkAndChargeBookingService;
    private ParkAndChargeBookingRepository parkAndChargeBookingRepository;

    @Autowired
    public ParkAndChargeBookingQueryApi(ParkAndChargeBookingService parkAndChargeBookingService,
                                        ParkAndChargeBookingRepository parkAndChargeBookingRepository){
        this.parkAndChargeBookingService = parkAndChargeBookingService;
        this.parkAndChargeBookingRepository = parkAndChargeBookingRepository;
    }

    /*---------------------------------------------------------------------*/

    @GetMapping("parkAndChargeBooking/{bookingId}")
    public ParkAndChargeBooking getParkAndChargeBooking(@PathVariable Long bookingId) throws ParkAndChargeBookingNotFoundException{
        var parkAndChargeBookingAggregate = parkAndChargeBookingRepository.getOne(bookingId);
        if (parkAndChargeBookingAggregate != null){
            return parkAndChargeBookingService.getValueObjectFromAggregate(parkAndChargeBookingAggregate);
        }
        else{
            throw new ParkAndChargeBookingNotFoundException(bookingId);
        }
    }


    @GetMapping("/parkAndChargeBookings")
    public List<ParkAndChargeBooking> getAllParkAndChargeBookings(){
        return parkAndChargeBookingService.getAllParkAndChargeBookings();
    }


    @GetMapping("/parkAndChargeBookings/{parkingSpaceId}")
    public List<ParkAndChargeBooking> getCurrentBookingsOfParkingSpace(@PathVariable String parkingSpaceId){
        return this.parkAndChargeBookingService.getCurrentBookingsOfParkingSpace(parkingSpaceId);
    }


    @GetMapping("/canceledParkAndChargeBookings/{parkingSpaceId}")
    public List<ParkAndChargeBooking> getCanceledBookingsOfParkingSpace(@PathVariable String parkingSpaceId){
        return this.parkAndChargeBookingService.getCanceledBookingsOfParkingSpace(parkingSpaceId);
    }

}
