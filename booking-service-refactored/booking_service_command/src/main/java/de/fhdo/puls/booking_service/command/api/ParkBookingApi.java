package de.fhdo.puls.booking_service.command.api;

import de.fhdo.puls.booking_service.command.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.command.service.ParkBookingService;
import de.fhdo.puls.booking_service.common.commands.CancelParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateParkBookingCommand;
import de.fhdo.puls.booking_service.common.dataTransferObjects.ParkBookingDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/resources/v1")
@CrossOrigin(origins = "${cors.origins}",
        allowedHeaders = "*",
        methods = {
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE
        })
public class ParkBookingApi {

    private final ParkBookingService parkBookingService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParkBookingApi.class);


    @Autowired
    public ParkBookingApi (ParkBookingService service) {
        this.parkBookingService = service;
    }


    @PostMapping(value = "/parkBooking")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public ParkBookingDto createParkBooking(@RequestBody CreateParkBookingCommand command) {
        ParkBookingDto dto = this.parkBookingService.handleCreateParkBookingCommand(command);
        LOGGER.info(
                "ParkBooking with booking_ID " + dto.getBookingId() + " successfully saved!"
        );
        return dto;
    }


    @PutMapping(value = "/parkBooking")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public ParkBookingDto updateParkBooking(@RequestBody UpdateParkBookingCommand command) throws BookingNotFoundException {
        ParkBookingDto dto = null;
        try {
            dto = this.parkBookingService.handleUpdateParkBookingCommand(command);
            LOGGER.info(
                    "ParkBooking with booking_ID " + dto.getBookingId() + "successfully updated!"
            );
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
        return dto;
    }


    @DeleteMapping(value = "/parkBooking/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public ParkBookingDto cancelParkBooking(@PathVariable Long bookingId) throws BookingNotFoundException {
        ParkBookingDto dto = null;
        try {
            dto = this.parkBookingService.handleCancelParkBookingCommand(bookingId);
            LOGGER.info(
                    "ParkBooking with booking_ID " + dto.getBookingId() + " successfully canceled!"
            );
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
        return dto;
    }
}
