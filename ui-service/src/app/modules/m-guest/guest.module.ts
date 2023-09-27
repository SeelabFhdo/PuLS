import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { DeviceBrowserModule } from '@deviceBrowserModule/device-browser.module';
import { DetailsComponent } from '@guestModule/details/details.component';
import { GuestMainComponent } from '@guestModule/guest-main/guest-main.component';
import { ResultsComponent } from '@guestModule/results/results.component';
import { SearchComponent } from '@guestModule/search/search.component';
import { TranslocoModule } from '@ngneat/transloco';
import { MaterialUiModule } from '@shared/modules/material-ui.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { BookingsOverviewDialog } from './bookings-dialog/bookings.dialog';
import { OfferParkingSpaceDialog } from './offer-dialog/offer.dialog';
import { ReserveSpotDialog } from './reserve-dialog/reserve.dialog';
import { BookingsDetailsDialog } from './bookings-details-dialog/bookings-details.dialog';

@NgModule({
  declarations: [
    GuestMainComponent,
    SearchComponent,
    ResultsComponent,
    DetailsComponent,
    BookingsOverviewDialog,
    OfferParkingSpaceDialog,
    ReserveSpotDialog,
    BookingsDetailsDialog
  ],
  exports: [
    GuestMainComponent,
    SearchComponent,
    ResultsComponent,
    DetailsComponent,
    BookingsOverviewDialog,
    OfferParkingSpaceDialog,
    ReserveSpotDialog,
    BookingsDetailsDialog
  ],
  imports: [
    CommonModule,
    RouterModule,
    NgxMaterialTimepickerModule,
    DeviceBrowserModule,
    TranslocoModule,
    MaterialUiModule,
    FormsModule,
    ReactiveFormsModule
  ],
  entryComponents: [
    BookingsOverviewDialog, 
    OfferParkingSpaceDialog, 
    ReserveSpotDialog, 
    BookingsDetailsDialog
  ],
  providers: [],
})
export class GuestModule { }
