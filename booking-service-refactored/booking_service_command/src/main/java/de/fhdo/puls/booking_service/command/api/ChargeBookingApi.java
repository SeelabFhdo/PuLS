package de.fhdo.puls.booking_service.command.api;

import de.fhdo.puls.booking_service.command.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.command.service.ChargeBookingService;
import de.fhdo.puls.booking_service.common.commands.CancelChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CancelParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateChargeBookingCommand;
import de.fhdo.puls.booking_service.common.dataTransferObjects.ChargeBookingDto;
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
public class ChargeBookingApi {

    private final ChargeBookingService chargeBookingService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChargeBookingApi.class);


    @Autowired
    public ChargeBookingApi (ChargeBookingService service) {
        this.chargeBookingService = service;
    }


    @PostMapping(value = "/chargeBooking")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public ChargeBookingDto createChargeBooking(@RequestBody CreateChargeBookingCommand command) {
        ChargeBookingDto dto = this.chargeBookingService.handleCreateChargeBookingCommand(command);
        LOGGER.info(
                "ChargeBooking with booking_ID " + dto.getBookingId() + " successfully saved!"
        );
        return dto;
    }


    @PutMapping(value = "/chargeBooking")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(
            @ApiResponse(code = 400, message = "Invalid or bad request")
    )
    public ChargeBookingDto updateChargeBooking(@RequestBody UpdateChargeBookingCommand command) throws BookingNotFoundException {
        ChargeBookingDto dto;
        try {
            dto = this.chargeBookingService.handleUpdateChargeBookingCommand(command);
            LOGGER.info(
                    "ChargeBooking with booking_ID " + dto.getBookingId() + " successfully updated!"
            );
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
        return dto;
    }


    @DeleteMapping(value = "/chargeBooking/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(
            @ApiResponse(code = 400, message = "Invalid or bad request")
    )
    public ChargeBookingDto cancelChargeBooking(@PathVariable Long bookingId) throws BookingNotFoundException {
        ChargeBookingDto dto;
        try {
            dto = this.chargeBookingService.handleCancelChargeBookingCommand(bookingId);
            LOGGER.info(
                    "ChargeBooking with booking_ID " + dto.getBookingId() + " successfully canceled!"
            );
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
        return dto;
    }
}
