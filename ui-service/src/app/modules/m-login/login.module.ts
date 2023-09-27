import { NgModule } from "@angular/core";

import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TranslocoModule} from '@ngneat/transloco';
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";

import { MaterialUiModule } from "@shared/modules/material-ui.module";

@NgModule({
  imports: [
    FormsModule, 
    ReactiveFormsModule, 
    TranslocoModule,
    CommonModule, 
    RouterModule, 
    MaterialUiModule,
  ],
  exports: [LoginComponent, RegisterComponent],
  declarations: [LoginComponent, RegisterComponent ],
  providers: []
})
export class LoginModule {}
