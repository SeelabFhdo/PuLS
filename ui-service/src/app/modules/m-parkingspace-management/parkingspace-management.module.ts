import { NgModule } from "@angular/core";

import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { TranslocoModule} from '@ngneat/transloco';

import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { ParkingSpaceManagementComponent } from "./parkingspace-managment/parkingspace-management.component";
import { ConfirmDialog } from "@shared/dialogs/confirm/confirm.dialog";

@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    RouterModule,
    TranslocoModule,
    MaterialUiModule,
  ],
  exports: [ParkingSpaceManagementComponent],
  declarations: [
    ParkingSpaceManagementComponent,
    ConfirmDialog,
  ],
  entryComponents: [ConfirmDialog],
  providers: [],
})
export class ParkingSpaceManagementModule {}