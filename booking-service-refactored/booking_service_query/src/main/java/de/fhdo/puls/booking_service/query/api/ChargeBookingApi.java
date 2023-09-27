package de.fhdo.puls.booking_service.query.api;

import de.fhdo.puls.booking_service.common.dataTransferObjects.ChargeBookingDto;
import de.fhdo.puls.booking_service.query.domain.ChargeBookingAggregate;
import de.fhdo.puls.booking_service.query.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.query.repository.ChargeBookingRepo;
import de.fhdo.puls.booking_service.query.service.ChargeBookingService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
public class ChargeBookingApi {

    private final ChargeBookingService chargeBookingService;
    private final ChargeBookingRepo chargeBookingRepo;


    @Autowired
    public ChargeBookingApi(ChargeBookingService service,
                            ChargeBookingRepo repo) {
        this.chargeBookingService = service;
        this.chargeBookingRepo = repo;
    }


    @GetMapping("/chargeBooking/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public ChargeBookingDto getChargeBooking(@PathVariable Long id) throws BookingNotFoundException {
        ChargeBookingAggregate aggregate = this.chargeBookingRepo.findByOriginalIdEquals(id);
        if (aggregate != null) {
            return this.chargeBookingService.getDtoFromAggregate(aggregate);
        }
        else {
            throw new BookingNotFoundException(
                    "ChargeBooking with booking_ID " + id + " not found!"
            );
        }
    }


    @GetMapping("/chargeBookings")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ChargeBookingDto> getChargeBookings() {
        return this.chargeBookingService.getAllChargeBookings();
    }


    @GetMapping("/actualChargeBookings/parkingSpace/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ChargeBookingDto> getActualBookingsOfParkingSpace(@PathVariable String id) {
        return this.chargeBookingService.getActualBookingsOfParkingSpace(id);
    }


    @GetMapping("/canceledChargeBookings/parkingSpace/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ChargeBookingDto> getCanceledBookingsOfParkingSpace(@PathVariable String id) {
        return this.chargeBookingService.getCanceledBookingsOfParkingSpace(id);
    }


    @GetMapping("/actualChargeBookings/booker/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(
            @ApiResponse(code = 400, message = "Invalid or bad request")
    )
    public List<ChargeBookingDto> getActualBookingsFromBooker(@PathVariable String id) {
        return this.chargeBookingService.getActualBookingsFromBooker(id);
    }


    @GetMapping("canceledChargeBookings/booker/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<ChargeBookingDto> getCanceledBookingsFromBooker(@PathVariable String id) {
        return this.chargeBookingService.getCanceledBookingsFromBooker(id);
    }
}
