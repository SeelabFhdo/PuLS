import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environment';

@Injectable({ providedIn: 'root' })
export class CniBookingService {

  private API_URL_BOOKING_QUERY_SERVICE =
    environment.backend_url + ':8080/BOOKING_QUERY/resources/v1';

  private API_URL_BOOKING_COMMAND_SERVICE =
    environment.backend_url + ':8080/BOOKING_COMMAND/resources/v1';

  constructor(private http: HttpClient) {}

  // park bookings
  getParkBooking(bookingId: string) {
    return this.http.get<any>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/parkBooking/${bookingId}`
    );
  }

  getAllParkBookings() {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/parkBookings`
    );
  }

  getActualBookingsOfParkingSpace(parkingSpaceId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/actualParkBookings/parkingSpace/${parkingSpaceId}`
    );
  }

  getCanceledBookingsOfParkingSpace(parkingSpaceId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/canceledParkBookings/parkingSpace/${parkingSpaceId}`
    );
  }

  getActualBookingsOfBooker(bookerId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/actualParkBookings/booker/${bookerId}`
    );
  }

  getCanceledBookingsOfBooker(bookerId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/canceledParkBookings/booker/${bookerId}`
    );
  }

  // -----------------------

  // park invoices
  getParkInvoice(invoiceId: string) {
    return this.http.get<any>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/parkInvoice/${invoiceId}`
    );
  }

  getAllParkInvoices() {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/parkInvoices`
    );
  }

  getParkInvoiceFromBooking(bookingId: string) {
    return this.http.get<any>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/parkInvoice/booking/${bookingId}`
    );
  }

  // ---------------------------------------------------------------------------------

  // charging bookings
  getChargeBooking(bookingId: string) {
    return this.http.get<any>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/chargeBooking/${bookingId}`
    );
  }

  getAllChargeBookings() {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/chargeBookings`
    );
  }

  getActualChargeBookings(chargeBookingId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/actualChargeBookings/parkingSpace/${chargeBookingId}`
    );
  }

  getCanceledChargeBookings(chargeBookingId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/canceledChargeBookings/parkingSpace/${chargeBookingId}`
    );
  }

  getActualChargeBookingsOfBooker(bookerId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/actualChargeBookings/booker/${bookerId}`
    );
  }

  getCanceledChargeBookingsOfBooker(bookerId: string) {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/canceledChargeBookings/booker/${bookerId}`
    );
  }

  // -----------------------

  // charging invoices
  getChargeInvoice(invoiceId: string) {
    return this.http.get<any>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/chargeInvoice/${invoiceId}`
    );
  }

  getAllChargeInvoices() {
    return this.http.get<any[]>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/chargeInvoices`
    );
  }

  getChargeInvoiceFromBooking(bookingId: string) {
    return this.http.get<any>(
      `${this.API_URL_BOOKING_QUERY_SERVICE}/chargeInvoice/booking/${bookingId}`
    );
  }

  // ###########################################################################

  // park booking
  createParkBooking(bookingData: any) {
      return this.http
        .post(
            `${this.API_URL_BOOKING_COMMAND_SERVICE}/parkBooking`,
            bookingData
        )
        .toPromise();
  }

  updateParkBooking(bookingData: any) {
    return this.http
        .put(
            `${this.API_URL_BOOKING_COMMAND_SERVICE}/parkBooking`,
            bookingData
        )
        .toPromise();
  }

  deleteParkBooking(bookingData: any) {
    return this.http
        .delete(
            `${this.API_URL_BOOKING_COMMAND_SERVICE}/parkBooking`,
            bookingData
        )
        .toPromise();
   }

   // -----------------------

   // park invoices
   createBookingInvoice(invoiceData: any) {
    return this.http
      .post(
          `${this.API_URL_BOOKING_COMMAND_SERVICE}/parkInvoice`,
          invoiceData
      )
      .toPromise();
   }

   // ---------------------------------------------------------------------------------

   // park booking
   createChargeBooking(chargeBookingData: any) {
        return this.http
        .post(
            `${this.API_URL_BOOKING_COMMAND_SERVICE}/chargeBooking`,
            chargeBookingData
        )
        .toPromise();
    }

    updateChargeBooking(chargeBookingData: any) {
    return this.http
        .put(
            `${this.API_URL_BOOKING_COMMAND_SERVICE}/chargeBooking`,
            chargeBookingData
        )
        .toPromise();
    }

    deleteChargeBooking(chargeBookingData: any) {
    return this.http
        .delete(
            `${this.API_URL_BOOKING_COMMAND_SERVICE}/chargeBooking`,
            chargeBookingData
        )
        .toPromise();
    }

    // -----------------------

   // park invoices
   createChargeBookingInvoice(invoiceData: any) {
    return this.http
      .post(
          `${this.API_URL_BOOKING_COMMAND_SERVICE}/chargeInvoice`,
          invoiceData
      )
      .toPromise();
   }
}
