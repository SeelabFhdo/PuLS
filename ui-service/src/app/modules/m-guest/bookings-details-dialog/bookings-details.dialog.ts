import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { CniBookingService } from "@shared/services/backend-data/cni-booking.service";

@Component({
    selector: "bookings-details-dialog",
    templateUrl: './bookings-details.dialog.html',
    styleUrls: ['./bookings-details.dialog.scss'],
})
export class BookingsDetailsDialog implements OnInit {

    constructor(
        public dialogRef: MatDialogRef<BookingsDetailsDialog>,
        private bookingService: CniBookingService,
        @Inject(MAT_DIALOG_DATA) public data: any
        ) {}

    ngOnInit(): void {
        
    }

}