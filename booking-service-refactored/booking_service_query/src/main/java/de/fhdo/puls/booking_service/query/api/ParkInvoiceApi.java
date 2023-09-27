package de.fhdo.puls.booking_service.query.api;

import de.fhdo.puls.booking_service.common.dataTransferObjects.BookingInvoiceDto;
import de.fhdo.puls.booking_service.query.exception.InvoiceNotFoundException;
import de.fhdo.puls.booking_service.query.service.ParkInvoiceService;
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
public class ParkInvoiceApi {

    private final ParkInvoiceService parkInvoiceService;


    @Autowired
    public ParkInvoiceApi(ParkInvoiceService parkInvoiceService) {
        this.parkInvoiceService = parkInvoiceService;
    }


    @GetMapping("/parkInvoice/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public BookingInvoiceDto getParkInvoice(@PathVariable Long id) throws InvoiceNotFoundException {
        return this.parkInvoiceService.getInvoice(id);
    }


    @GetMapping("/parkInvoices")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public List<BookingInvoiceDto> getParkInvoices() {
        return this.parkInvoiceService.getAllInvoices();
    }


    @GetMapping("/parkInvoice/booking/{id}")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid or bad request")
    })
    public BookingInvoiceDto getParkInvoiceFromBooking(@PathVariable Long id) throws InvoiceNotFoundException {
        return this.parkInvoiceService.getInvoiceFromBooking(id);
    }
}
