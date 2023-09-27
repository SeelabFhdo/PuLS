package de.fhdo.puls.booking_service.query.api;

import de.fhdo.puls.booking_service.common.dataTransferObjects.ParkBookingDto;
import de.fhdo.puls.booking_service.query.domain.ParkBookingAggregate;
import de.fhdo.puls.booking_service.query.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.query.repository.ParkBookingRepo;
import de.fhdo.puls.booking_service.query.service.ParkBookingService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/resources/v1")
@CrossOrigin(origins = "${cors.origins}",
        allowedHeaders = "*",
        methods = {
                RequestMethod.GET
        })
public class ParkBookingApi {

    private final ParkBookingService parkBookingService;
    private final ParkBookingRepo parkBookingRepo;


    @Autowired
    public ParkBookingApi(ParkBookingService service,
                          ParkBookingRepo repo) {
        this.parkBookingService = service;
        this.parkBookingRepo = repo;
    }


    @GetMapping("/parkBooking/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public ParkBookingDto getParkBooking(@PathVariable Long id) throws BookingNotFoundException {
        ParkBookingAggregate aggregate = this.parkBookingRepo.findByOriginalIdEquals(id);
        if (aggregate != null) {
            return this.parkBookingService.getDtoFromAggregate(aggregate);
        }
        else {
            throw new BookingNotFoundException(
                    "ParkBooking with booking_ID " + id + " not found!"
            );
        }
    }


    @GetMapping("/parkBookings")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ParkBookingDto> getParkBookings() {
        return this.parkBookingService.getAllParkBookings();
    }


    @GetMapping("/actualParkBookings/parkingSpace/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ParkBookingDto> getActualBookingsOfParkingSpace(@PathVariable String id) {
        return this.parkBookingService.getActualBookingsOfParkingSpace(id);
    }


    @GetMapping("/canceledParkBookings/parkingSpace/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ParkBookingDto> getCanceledBookingsOfParkingSpace(@PathVariable String id) {
        return this.parkBookingService.getCanceledBookingsOfParkingSpace(id);
    }


    @GetMapping("/actualParkBookings/booker/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ParkBookingDto> getActualBookingsFromBooker(@PathVariable String id) {
        return this.parkBookingService.getActualBookingsFromBooker(id);
    }


    @GetMapping("/canceledParkBookings/booker/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ParkBookingDto> getCanceledParkBookingsFromBooker(@PathVariable String id) {
        return this.parkBookingService.getCanceledBookingsFromBooker(id);
    }

    /* Alle Buchungen eines Parkplatzbesitzers
    * --> Statistik-Service */
}
