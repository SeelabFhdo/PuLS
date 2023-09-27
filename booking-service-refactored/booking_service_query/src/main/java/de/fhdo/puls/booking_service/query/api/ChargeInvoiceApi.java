package de.fhdo.puls.booking_service.query.api;

import de.fhdo.puls.booking_service.common.dataTransferObjects.BookingInvoiceDto;
import de.fhdo.puls.booking_service.query.exception.InvoiceNotFoundException;
import de.fhdo.puls.booking_service.query.service.ChargeInvoiceService;
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
public class ChargeInvoiceApi {

    private final ChargeInvoiceService chargeInvoiceService;


    @Autowired
    public ChargeInvoiceApi(ChargeInvoiceService chargeInvoiceService) {
        this.chargeInvoiceService = chargeInvoiceService;
    }


    @GetMapping("/chargeInvoice/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public BookingInvoiceDto getChargeInvoice(@PathVariable Long id) throws InvoiceNotFoundException {
        return this.chargeInvoiceService.getInvoice(id);
    }


    @GetMapping("/chargeInvoices")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<BookingInvoiceDto> getChargeInvoices() {
        return this.chargeInvoiceService.getAllInvoices();
    }


    @GetMapping("/chargeInvoice/booking/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public BookingInvoiceDto getChargeInvoiceFromBooking(@PathVariable Long id) throws InvoiceNotFoundException {
        return this.chargeInvoiceService.getInvoiceFromBooking(id);
    }

}
