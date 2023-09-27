package de.fhdo.puls.booking_service.command.api;

import de.fhdo.puls.booking_service.command.service.ParkInvoiceService;
import de.fhdo.puls.booking_service.common.commands.CreateBookingInvoiceCommand;
import de.fhdo.puls.booking_service.common.dataTransferObjects.BookingInvoiceDto;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/resources/v1")
@CrossOrigin(origins = "${cors.origins}",
        allowedHeaders = "*",
        methods = {
                RequestMethod.POST
        })
public class ParkInvoiceApi {

    private final ParkInvoiceService parkInvoiceService;
    private final static Logger LOGGER = LoggerFactory.getLogger(ParkInvoiceApi.class);

    @Autowired
    public ParkInvoiceApi(ParkInvoiceService service) {
        this.parkInvoiceService = service;
    }


    @PostMapping("/parkInvoice")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public BookingInvoiceDto createParkInvoice(@RequestBody CreateBookingInvoiceCommand command) {
        BookingInvoiceDto dto = this.parkInvoiceService.handleCreateBookingInvoiceCommand(command);
        LOGGER.info(
                "ParkInvoice with invoice_ID " + dto.getInvoiceId() + " successfully saved!"
        );
        return dto;
    }

}
