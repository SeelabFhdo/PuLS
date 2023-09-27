package de.fhdo.puls.booking_service.command.api;

import de.fhdo.puls.booking_service.command.service.ParkBookingService;
import de.fhdo.puls.booking_service.common.commands.CancelParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateParkBookingCommand;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController @RequestMapping(value = "/resources/v1")
@CrossOrigin(origins = "${cors.origins}", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.HEAD,
                RequestMethod.POST, RequestMethod.PUT , RequestMethod.DELETE})
public class ParkBookingCommandApi {

    private final ParkBookingService parkBookingService;

    @Autowired
    public ParkBookingCommandApi(ParkBookingService parkBookingService){
        this.parkBookingService = parkBookingService;
    }

    /*---------------------------------------------------------------------*/


    /* Throws a ParkBookingAlreadyExistsException, if the generated booking-ID is already used */
    @PostMapping(value = "/parkBooking")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public void createParkBooking(@RequestBody @Valid CreateParkBookingCommand command) throws Exception{
        this.parkBookingService.handleCreateParkBookingCommand(command);
        
    }


    @PutMapping(value = "/parkBooking")
    public void updateParkBooking(@RequestBody UpdateParkBookingCommand command){
        this.parkBookingService.handleUpdateParkBookingCommand(command);
    }


    /* Throws a ParkBookingNotFoundException, if the booking to be canceled does not exist */
    @DeleteMapping(value = "/parkBooking")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public void cancelParkBooking(@RequestBody CancelParkBookingCommand command) throws Exception{
        this.parkBookingService.handleCancelParkBookingCommand(command);
    }
}
