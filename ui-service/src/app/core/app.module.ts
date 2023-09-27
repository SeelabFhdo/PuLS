import { BrowserModule } from '@angular/platform-browser';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { DoBootstrap, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { translocoHttpLoader } from "./localization/transloco.loader";
import { TranslocoModule, TRANSLOCO_CONFIG, TranslocoConfig, TRANSLOCO_SCOPE } from '@ngneat/transloco';
import { TranslocoPreloadLangsModule } from "@ngneat/transloco-preload-langs";
import { CountToModule } from "angular-count-to";

// material ui imports
import { MaterialUiModule } from "@shared/modules/material-ui.module";

// custom module imports
import { GuestModule } from '@guestModule/guest.module';
import { DeviceBrowserModule } from '../modules/m-device-browser/device-browser.module';
import { LoginModule } from '../modules/m-login/login.module';
//import { UserManagementModule } from '../modules/m-user-management/user-management.module';
import { ParkingSpaceManagementModule } from '../modules/m-parkingspace-management/parkingspace-management.module';
import { AuthInterceptor } from "./interceptors/auth.interceptor";
import { ErrorInterceptor } from "./interceptors/error.interceptor";

// layout imports
import { HalfCloseSideNavComponent } from '../layout/half-close-side-nav/half-close-side-nav.component';
import { MainPageComponent } from '../layout/main-container/main-container.component';
import { ToolbarComponent } from "../layout/toolbar/toolbar.component";
import { environment } from 'src/environments/environment.defaults';

// init keyCloak service instance
const keycloakService: KeycloakService = new KeycloakService();

@NgModule({
  declarations: [
    // base declarations
    AppComponent,

    // custom declarations
    MainPageComponent,
    HalfCloseSideNavComponent,
    ToolbarComponent
  ],
  imports: [
    // base imports
    BrowserModule,
    KeycloakAngularModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    TranslocoModule,
    TranslocoPreloadLangsModule.preload(['en', 'de']),
    CountToModule,

    // custom imports
    MaterialUiModule,
    LoginModule,
    //UserManagementModule,
    ParkingSpaceManagementModule,
    DeviceBrowserModule,
    GuestModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    },
    {
      provide: KeycloakService,
      useValue: keycloakService
    },
    {
      provide: TRANSLOCO_CONFIG,
      useValue: {
        availableLangs: ['en', 'de'],
        defaultLang: 'de',
        fallbackLang: 'en',
        prodMode: environment.production,
        reRenderOnLangChange: true
      } as TranslocoConfig
    },
    translocoHttpLoader
  ],
  entryComponents: [
    AppComponent
  ]
})
//export class AppModule {}
export class AppModule implements DoBootstrap {
  async ngDoBootstrap(app) {
    // @ts-ignore
    const { keycloakConfig } = environment;

    try {
      keycloakService.init({ 
          config: keycloakConfig , 
          initOptions: {onLoad: 'login-required', checkLoginIframe: false}}).then(() => {
          app.bootstrap(AppComponent);
        });
    } catch (error) {
      console.error('Keycloak init failed', error);
    }
  }
}
