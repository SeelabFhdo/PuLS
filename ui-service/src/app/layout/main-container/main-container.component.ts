import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { environment } from '@environment';
import { TranslocoService } from '@ngneat/transloco';
import { UserItemInterface } from '@shared/interfaces/userItem.interface';
import { KeyCloakAuthService } from '@shared/services/backend-data/keycloak-auth.service';
import { UserService } from '@shared/services/backend-data/user.service';
import { UtilsService } from '@shared/services/helper/utils.service';
import { BookingsOverviewDialog } from '@guestModule/bookings-dialog/bookings.dialog';
import { OfferParkingSpaceDialog } from '@guestModule/offer-dialog/offer.dialog';

@Component({
  selector: 'app-main-container-component',
  templateUrl: './main-container.component.html',
  styleUrls: ['./main-container.component.scss'],
})
export class MainPageComponent implements OnInit {
  public currentUser: UserItemInterface;

  private _platformTitle: string = '';

  private _toolbarConfig: any;
  private _halfCloseSideNavConfig: any;

  constructor(
    private translocoService: TranslocoService,
    private authService: KeyCloakAuthService,
    private userService: UserService,
    private utilsService: UtilsService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.currentUser = this.utilsService.keyCloakProfileToUserItemInterface(
      this.authService.userDetails
    );

    this._platformTitle = environment.platform_title;

    // toolbar config
    this._toolbarConfig = {
      langMenu: {
        buttons: [
          {
            label: 'toolbar.lang.en',
            callback: () => { this.translocoService.setActiveLang('en'); }
          },
          {
            label: 'toolbar.lang.de',
            callback: () => { this.translocoService.setActiveLang('de'); }
          }
        ]
      },
      userMenu: {
        currentUserData: this.currentUser,
        buttons: [
          /*
          {
            label: 'toolbar.userMenu.settings',
            icon: 'settings',
            callback: () => { this.onUserConfigClicked(); }
          },
          */
          {
            label: 'toolbar.userMenu.ownBookings',
            icon: 'book',
            callback: () => { this.onOwnBookingsClicked(); }
          },
          {
            label: 'toolbar.userMenu.offerSpace',
            icon: 'local_offer',
            callback: () => { this.onOfferParkingSpaceClicked(); }
          },
          {
            label: 'toolbar.userMenu.logOut',
            icon: 'exit_to_app',
            callback: () => { this.onLogoutClicked(); }
          }
        ]
      }
    };

    // get pre select for side nav
    const preSelectIndex: number = sessionStorage.getItem('sideNavSelectionIndex') ?
    Number(sessionStorage.getItem('sideNavSelectionIndex')) : 0;

    // side nav config
    this._halfCloseSideNavConfig = {
      preSelectIndex: preSelectIndex,
      isAlwaysExpanded: false,
      isOpened: false,
      autoExpand: false,
      currentUserRoles: this.currentUser.userRoles,
      items: [
        {
          icon: 'search',
          label: 'sidenav.buttons.search',
          routerLink: '/search',
          userRolesAccess: ['*']
        },
        {
          icon: 'electric_car',
          label: 'sidenav.buttons.parkingSpaces',
          routerLink: '/devicebrowser/ev',
          userRolesAccess: ['*'] 
        },
        {
          icon: 'router',
          label: 'sidenav.buttons.pollutionSensorUnits',
          routerLink: '/devicebrowser/psu',
          userRolesAccess: ['*']
        },
        {
          icon: 'group',
          label: 'sidenav.buttons.users',
          routerLink: '/usermanagement',
          userRolesAccess: ['SUPER_ADMINISTRATOR']
        }/*,
        {
          icon: 'miscellaneous_services',
          label: 'sidenav.buttons.itemManagement',
          routerLink: '/parkingspacemanagement',
          userRolesAccess: ['*']
        }*/
      ]
    };
  }

  onUserConfigClicked(): void {

    // now uses KeyCloak.

    /*
    this.userService.getByEmail(this.currentUser.email).subscribe(
      (userData) => {
        const dialogRef = this.dialog.open(ProfileManagementDialog, {
          data: {
            email: userData.email,
            firstName: userData.firstname,
            lastName: userData.lastname,
            avatar: '',
            roles: userData.userRoles,
            roleEditorVisible: false,
          },
        });

        dialogRef.afterClosed().subscribe((result) => {
          if (typeof result !== 'undefined') {
            // save edited user data
            const userData: any = {
              email: result.email,
              firstname: result.firstName,
              lastname: result.lastName,
              password: result.password,
              userRoles: result.roles,
            };

            this.userService.update(userData).then(() => {
              // nothing to do right now.
            });
          }
        });
      },
      (error) => {}
    );
    */
  }

  onOwnBookingsClicked(): void {
    const dialogRef = this.dialog.open(BookingsOverviewDialog, {
      width: "60vw",
      height: "80vh",
      data: {},
    });
  }

  onOfferParkingSpaceClicked(): void {
    const dialogRef = this.dialog.open(OfferParkingSpaceDialog, {
      width: "60vw",
      height: "80vh",
      data: {},
    });
  }

  onLogoutClicked(): void {
    sessionStorage.clear();
    this.authService.logout();
  }

  public get platformTitle(): string {
    return this._platformTitle;
  }

  public get halfCloseSideNavConfig(): any {
    return this._halfCloseSideNavConfig;
  }

  public get toolbarConfig(): any {
    return this._toolbarConfig;
  }
}
