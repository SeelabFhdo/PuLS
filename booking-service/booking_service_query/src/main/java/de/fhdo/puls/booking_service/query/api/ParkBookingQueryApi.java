package de.fhdo.puls.booking_service.query.api;

import de.fhdo.puls.booking_service.query.domain.ParkBooking;
import de.fhdo.puls.booking_service.query.repository.ParkBookingRepository;
import de.fhdo.puls.booking_service.query.service.ParkBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources/v1")
@CrossOrigin(origins = "${cors.origins}", allowedHeaders = "*", methods =
        {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD})
public class ParkBookingQueryApi {

    private final ParkBookingService parkBookingService;
    private final ParkBookingRepository parkBookingRepository;

    @Autowired
    public ParkBookingQueryApi(ParkBookingService parkBookingService,
                               ParkBookingRepository parkBookingRepository){
        this.parkBookingService = parkBookingService;
        this.parkBookingRepository = parkBookingRepository;
    }

    /*---------------------------------------------------------------------*/

    @GetMapping("/parkBooking/{bookingId}")
    public ParkBooking getParkBooking(@PathVariable Long bookingId) throws ParkBookingNotFoundException{
        var parkBookingAggregate = this.parkBookingRepository.getOne(bookingId);
        if (parkBookingAggregate != null){
            return this.parkBookingService.getValueObjectFromAggregate(parkBookingAggregate);
        }
        else {
            throw new ParkBookingNotFoundException(bookingId);
        }
    }


    @GetMapping("/parkBookings")
    public List<ParkBooking> getAllParkBookings(){
        return this.parkBookingService.getAllParkBookings();
    }


    @GetMapping("/parkBookings/{parkingSpaceId}")
    public List<ParkBooking> getCurrentBookingsOfParkingSpace(@PathVariable String parkingSpaceId){
        return this.parkBookingService.getCurrentBookingsOfParkingSpace(parkingSpaceId);
    }


    @GetMapping("/canceledParkBookings/{parkingSpaceId}")
    public List<ParkBooking> getCanceledBookingsOfParkingSpace(@PathVariable String parkingSpaceId){
        return this.parkBookingService.getCanceledBookingsOfParkingSpace(parkingSpaceId);
    }
}
