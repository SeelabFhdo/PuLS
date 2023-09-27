import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { MatDialogRef, MatPaginator, MatTableDataSource, MAT_DIALOG_DATA } from "@angular/material";
import { Router } from "@angular/router";
import { CniBookingService } from "@shared/services/backend-data/cni-booking.service";
import { environment } from "src/environments/environment.defaults";

@Component({
    selector: "bookings-dialog",
    templateUrl: './bookings.dialog.html',
    styleUrls: ['./bookings.dialog.scss'],
})
export class BookingsOverviewDialog implements OnInit {

  //canceling: boolean = false;

  dataSourceBookings: MatTableDataSource<any>;
  bookings: any[] = [];

  displayedColumnsBookings: string[] = [
    'bookingIcon',
    //'bookingId',
    'bookingCreated',
    'bookingStart',
    'bookingEnd',
    'pricePerHour',
    'pricePerKWh',
    'canceled',
    'invoice',
    'parkingSpaceLink',
    'action',
  ];

  private _filterValue: string = '';

  /*
  "bookingId": 2,
  "bookerId": 12,
  "parkingSpaceId": "1",
  "bookingStart": "2021-09-20T08:00:00.000+0000",
  "bookingEnd": "2021-09-20T12:00:00.000+0000",
  "pricePerHour": 15.0,
  "bookingCreated": "2022-01-14T11:25:07.827+0000",
  "canceled": false
  */

  @ViewChild('bookingsTablePaginator', { static: false })
  bookingsTablePaginator: MatPaginator;

  constructor(
      public dialogRef: MatDialogRef<BookingsOverviewDialog>,
      private bookingService: CniBookingService,
      private router: Router,
      @Inject(MAT_DIALOG_DATA) public data: any
      ) {
    this.dataSourceBookings = new MatTableDataSource([]);
  }

  ngOnInit(): void {
      this.reloadTableData();
  }

  reloadTableData() {
    this.bookingService.getActualChargeBookingsOfBooker(String(environment.debug_usr_id)).toPromise()
      .then((bookings) => {
        this.bookings = bookings;
        this.dataSourceBookings = new MatTableDataSource(this.bookings);
        this.dataSourceBookings.paginator = this.bookingsTablePaginator;
        this.applyFilter(this._filterValue);
      });
  }

  applyFilter(filterValue: string) {
    this._filterValue = filterValue;

    this.dataSourceBookings.filter = filterValue.trim().toLowerCase();

    if (this.dataSourceBookings.paginator) {
      this.dataSourceBookings.paginator.firstPage();
    }
  }

  getFormattedTime(isoTime: string): string {
    return new Date(isoTime).toLocaleString();
  }

  navigateToSpot(spotId: string) {
    this.dialogRef.close();
    this.router.navigateByUrl("/devicebrowser/ev/" + spotId + "/electrified");
  }

  onBack(): void {
    this.dialogRef.close();
  }

  onPayBooking(): void {

  }

  onCancelBooking(id: string): void {

    //this.canceling = true;

    setTimeout(() => {

      this.bookingService.deleteChargeBooking(
        { bookingId: String(id) })
      .then((deletedResource) => {

        setTimeout(() => {
          this.reloadTableData();
          //this.canceling = false;    
        }, 3000);

      }, (error) => {
        console.log(error);
        //this.canceling = false;
      });

    }, 2000);
  }

}
