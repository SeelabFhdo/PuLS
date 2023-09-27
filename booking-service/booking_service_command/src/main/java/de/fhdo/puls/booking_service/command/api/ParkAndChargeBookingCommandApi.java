package de.fhdo.puls.booking_service.command.api;

import de.fhdo.puls.booking_service.command.service.ParkAndChargeBookingService;
import de.fhdo.puls.booking_service.common.commands.CancelParkAndChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateParkAndChargeBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateParkAndChargeBookingCommand;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/resources/v1")
@CrossOrigin(origins = "${cors.origin}", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.HEAD,
                RequestMethod.POST, RequestMethod.PUT , RequestMethod.DELETE})
public class ParkAndChargeBookingCommandApi {

    private final ParkAndChargeBookingService parkAndChargeBookingService;

    @Autowired
    public ParkAndChargeBookingCommandApi(ParkAndChargeBookingService parkAndChargeBookingService){
        this.parkAndChargeBookingService = parkAndChargeBookingService;
    }

    /*---------------------------------------------------------------------*/

    /* Throws a ParkBookingAlreadyExistsException, if the generated booking-ID is already used */
    @PostMapping(value = "/parkAndChargeBooking")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public void createParkAndChargeBooking(@RequestBody CreateParkAndChargeBookingCommand command) throws Exception{
        this.parkAndChargeBookingService.handleCreateParkAndChargeBookingCommand(command);
    }


    @PutMapping(value = "/parkAndChargeBooking")
    public void updateParkAndChargeBooking(@RequestBody UpdateParkAndChargeBookingCommand command){
        this.parkAndChargeBookingService.handleUpdateParkAndChargeBookingCommand(command);
    }


    /* Throws a ParkBookingNotFoundException, if the booking to be canceled does not exist */
    @DeleteMapping(value = "/parkAndChargeBooking")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public void cancelParkAndChargeBooking(@RequestBody CancelParkAndChargeBookingCommand command) throws Exception{
        this.parkAndChargeBookingService.handleCancelParkAndChargeBookingCommand(command);
    }
}
