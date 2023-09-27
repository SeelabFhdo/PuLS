import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DeviceBrowserDetailsPanelComponent } from '@deviceBrowserModule/details-view/device-details.component';
import { DeviceBrowserListComponent } from '@deviceBrowserModule/list-view/device-list.component';
import { DeviceBrowserMapViewComponent } from '@deviceBrowserModule/map-view/device-map.component';
import { DetailsComponent } from '@guestModule/details/details.component';
import { GuestMainComponent } from '@guestModule/guest-main/guest-main.component';
import { ResultsComponent } from '@guestModule/results/results.component';
import { SearchComponent } from '@guestModule/search/search.component';
//import { UserManagementComponent } from '@userManagementModule/user-management/user-management.component';
import { OldDeviceBrowserMapViewComponent } from '@deviceBrowserModule/old-map-view/old-device-map.component';
import { ParkingSpaceManagementComponent } from '@deviceManagementModule/parkingspace-managment/parkingspace-management.component';

import { MainPageComponent } from '../layout/main-container/main-container.component';
import { AdminRoleGuard } from './guards/admin-role.guard';
import { AuthGuard } from './guards/keycloak-auth.guard';

// the routing definition
const routes: Routes = [
  {
    // default route to main page component after login
    path: '',
    component: MainPageComponent,
    canActivate: [AuthGuard],
    children: [
      {
        // default child route of main component
        path: '',
        redirectTo: 'search',
        pathMatch: 'full',
      },
      {
        path: 'search',
        component: GuestMainComponent,
        data: { sideNavSelectionIndex: '0' },
        children: [
         {
           path: '',
           component: SearchComponent,
         },
         {
           path: 'results',
           // path: 'results/:query',
           component: ResultsComponent,
         },
         {
           path: 'details/:id',
           component: DetailsComponent,
         },
        ],
      },
      {
        path: 'devicebrowser/ev',
        component: DeviceBrowserListComponent,
        data: { sideNavSelectionIndex: '1', deviceType: 'ev' }
      },
      {
        path: 'devicebrowser/psu',
        component: DeviceBrowserListComponent,
        data: { sideNavSelectionIndex: '2', deviceType: 'psu' }
      },
      {
        path: 'devicebrowser/ev/:deviceId/:electrifiedFlag',
        component: DeviceBrowserDetailsPanelComponent,
      },
      {
        path: 'devicebrowser/psu/:deviceId',
        component: DeviceBrowserDetailsPanelComponent,
      },
      {
        path: 'devicebrowsermap/:deviceType',
        component: OldDeviceBrowserMapViewComponent, // <-- DeviceBrowserMapViewComponent
      },
      {
        path: 'deviceeditor/:deviceType',
        component: ParkingSpaceManagementComponent,
        //canActivate: [AdminRoleGuard],
      }
    ],
  },
  // otherwise redirect to home
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

// the routing module of the application imports the defined routes
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule {}
