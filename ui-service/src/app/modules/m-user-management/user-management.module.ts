import { NgModule } from "@angular/core";

import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { TranslocoModule} from '@ngneat/transloco';

import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { UserManagementComponent } from "./user-management/user-management.component";
import { ProfileManagementDialog } from "./profile-management/profile-management.dialog";
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
  exports: [UserManagementComponent, ProfileManagementDialog],
  declarations: [
    UserManagementComponent,
    ProfileManagementDialog,
    ConfirmDialog,
  ],
  entryComponents: [ProfileManagementDialog, ConfirmDialog],
  providers: [],
})
export class UserManagementModule {}
