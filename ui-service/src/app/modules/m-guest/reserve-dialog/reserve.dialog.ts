import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Router } from "@angular/router";
import { MapSearchService } from "@guestModule/map-search.service";
import { ElectricSpaceInterface } from "@shared/interfaces/electric-space.interface";
import { CniBookingService } from "@shared/services/backend-data/cni-booking.service";
import { environment } from "src/environments/environment.defaults";

@Component({
    selector: "reserve-spot-dialog",
    templateUrl: './reserve.dialog.html',
    styleUrls: ['./reserve.dialog.scss'],
  })
  export class ReserveSpotDialog implements OnInit {

    private _spot: ElectricSpaceInterface;
    private _timeSpan: string[] = [];

    loading: boolean = false;
    success: boolean = false;
    error: boolean = false;

    spotTypeBooking: 'none' | 'electrified' = 'electrified';

    constructor(
        public dialogRef: MatDialogRef<ReserveSpotDialog>,
        private router: Router,
        private bookingService: CniBookingService,
        private mapSearchService: MapSearchService,
        @Inject(MAT_DIALOG_DATA) public data: any
        ) {}

    ngOnInit(): void {
        this._spot = this.data.spot;
        this._timeSpan = this.data.timeSpan;

        console.log(this._spot);
    }

    get spot(): ElectricSpaceInterface {
      return this._spot;
    }

    get from(): string {
      return new Date(this._timeSpan[0]).toLocaleString();
    }

    get until(): string {
      return new Date(this._timeSpan[1]).toLocaleString();
    }

    onCancel(): void {
      this.dialogRef.close();
    }

    onReserveNow(): void {

      if (this._timeSpan.length <= 0) return;

      this.error = false;
      this.loading = true;

      // do the reservation
      setTimeout(() => {

        // fetch local booking data
        // TODO ...
        let bookingData = {
          "bookerId": environment.debug_usr_id,
          "parkingSpaceId": this._spot.id,
          "bookingStart": this._timeSpan[0],
          "bookingEnd": this._timeSpan[1],
          "parkingPricePerHour": this._spot.parkingPricePerHour,
          "chargingPricePerKWh": this._spot.chargingPricePerKWH
        };

        // booking request ...
        this.bookingService.createChargeBooking(bookingData).then(
          (bookingDataResponse: any) => {
            if (bookingDataResponse) {

              this.loading = false;
              this.success = true;
      
              setTimeout(() => {
                // close dialog window
                this.dialogRef.close();
                this.mapSearchService.clear();
                this.router.navigateByUrl("/");
              }, 3000);
            }
        }, (error) => {
          console.log(error);
          this.loading = false;
          this.success = false;
          this.error = true;
        });

      }, 3000);
    }

  }