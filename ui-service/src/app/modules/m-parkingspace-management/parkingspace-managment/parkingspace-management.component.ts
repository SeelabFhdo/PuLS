import { SelectionModel } from "@angular/cdk/collections";
import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { MatDialog, MatPaginator, MatSort, MatTableDataSource } from "@angular/material";
import { ActivatedRoute } from "@angular/router";
import { TranslocoService } from "@ngneat/transloco";
import { SpaceInterface } from "@shared/interfaces/space.interface";
import { Location } from '@angular/common';
import { CniDeviceService } from "@shared/services/backend-data/cni-sensorbox.service";
import { CniParkingSpacesService } from "@shared/services/backend-data/cni-parkingspaces.service";
import { environment } from "src/environments/environment.defaults";
import { DeviceBrowserListService } from "@deviceBrowserModule/list-view/device-list.service";

@Component({
    selector: 'parkingspace-management-component',
    templateUrl: './parkingspace-management.component.html',
    styleUrls: ['./parkingspace-management.component.scss'],
  })
  export class ParkingSpaceManagementComponent implements OnInit, OnDestroy {

  private _title: string;
  private _parkingSpaces: any[];
  private _deviceType: string;

  private _interval: any;

  dataSource: MatTableDataSource<SpaceInterface>;
  selectionModel: SelectionModel<SpaceInterface>;

  deviceTableProfile: any;

  displayedColumns: string[] = [
    'selection',
    'deviceIcon',
    'deviceTitle',
    'ownerId',
    'parkingSpaceSize',
    'parkingPricePerHour',
    'dataState',
    'action'
  ];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private cniDeviceService: CniDeviceService,
    private cniParkingSpaceService: CniParkingSpacesService,
    private translocoService: TranslocoService,
    private location: Location,
    private deviceBrowserListService: DeviceBrowserListService
    ) {
    // init data source object
    this.dataSource = new MatTableDataSource(this._parkingSpaces);
  }

  ngOnInit(): void {

    this.route.data.subscribe(data => {
      sessionStorage.setItem('sideNavSelectionIndex', data.sideNavSelectionIndex);
    });

    this.route.queryParamMap.subscribe(params => {
      this._deviceType = params.get('deviceType');
      this._title = (this._deviceType == "ev") ? 
      'parkingspace-management.title' : 
      'parkingspace-management.titleSensors';
      this.deviceTableProfile = this.deviceBrowserListService.getDeviceProfile(this._deviceType);
    });

    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.selectionModel = new SelectionModel<SpaceInterface>(true, []);

    // load user data table on component initialization
    this.reloadDeviceTable();

    // start polling data frequently
    this._interval = setInterval(() => {
      this.reloadDeviceTable();
    }, environment.backend_request_update_interval);
  }

  onBack(): void {
    this.location.back();
  }

  reloadDeviceTable() {
    if (this._deviceType == 'ev') {
      // get all parking spaces
      Promise.all([
        this.cniParkingSpaceService.getAllElectrified().toPromise(),
        this.cniParkingSpaceService.getAllNoneElectrified().toPromise()
        ]).then((values) => {
          const electrifiedParkingSpots: SpaceInterface[] = values[0];
          const noneElectrifiedParkingSpots: SpaceInterface[] = values[1];

          this._parkingSpaces = electrifiedParkingSpots.concat(noneElectrifiedParkingSpots);

          this.dataSource = new MatTableDataSource(this._parkingSpaces);
          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;
        },
        (error) => { console.log(error); }
      );
    } else {
      // get all sensor devices
      this.cniDeviceService.getAll().subscribe((sensorDevices) => {
        /*
        this.dataSource = new MatTableDataSource(sensorDevices);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        */
      },
      (error) => { console.log(error); });
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  isAllSelected() {
    const numSelected = this.selectionModel.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected()
      ? this.selectionModel.clear()
      : this.dataSource.data.forEach((row) => this.selectionModel.select(row));
  }

  // open user profile dialog with registration setting
  openAddDeviceDialog(): void {
    /*
    const dialogRef = this.dialog.open(ProfileManagementDialog, {
      data: {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        roles: ['USER'],
        passwordEditorVisible: true,
        roleEditorVisible: true,
        dialogTitle: 'user-management.profileDialog.newTitle',
        confirmButtonText: 'user-management.profileDialog.newAccept',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (typeof result !== 'undefined') {
        const userData: any = {
          email: result.email,
          firstname: result.firstName,
          lastname: result.lastName,
          password: result.password,
          userRoles: result.roles,
        };

        // console.log(JSON.stringify(userData));

        this.userService.register(userData).then(() => {
          this.reloadUserTable();
        });
      }
    });
    */
  }

  // open user profile dialog with edit setting
  openDeviceEditorDialog(parkingSpaceItem: SpaceInterface): void {
    /*
    this.userService.getByEmail(userItemData.email).subscribe(
      (userData) => {
        // load initial user data on profile dialog showing up
        const dialogRef = this.dialog.open(ProfileManagementDialog, {
          data: {
            firstName: userData.firstname,
            lastName: userData.lastname,
            avatar: '',
            email: userData.email,
            roles: userData.userRoles,
            roleEditorVisible: true,
          },
        });

        //
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
              this.reloadUserTable();
            });
          }

          this.selectionModel.clear();
        });
      },
      (error) => {}
    );
    */
  }

  // open delete user dialog
  openParkingSpaceDeleteConfirmDialog(parkingSpaceData: any): void {
    /*
    const dialogRef = this.dialog.open(ConfirmDialog, {
      data: {
        title: 'user-management.deleteDialog.title',
        preQuestion:
          'user-management.deleteDialog.preQuestion',
        item: userItemData.name,
        postQuestion:
          'user-management.deleteDialog.postQuestion' ,
        userId: userItemData.id,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (typeof result !== 'undefined') {
        // delete the selected user
 
        this.userService.deleteById(result.userId).then(() => {
          this.reloadUserTable();
        });
   
      }

      this.selectionModel.clear();
    });
    */
  }

  // open delete all users dialog
  openSelectedParkingSpaceDeleteConfirmDialog(): void {
    /*
    const dialogRef = this.dialog.open(ConfirmDialog, {
      data: {
        title: 'user-management.deleteAllDialog.title',
        preQuestion: 'user-management.deleteAllDialog.question',
        item: '',
        postQuestion: 'user-management.deleteDialog.postQuestion',
        selectedUsers: this.selectionModel.selected,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (typeof result !== 'undefined') {
        // delete all selected users
        for (let i = 0; i < result.selectedUsers.length; i++) {
          
          this.userService.deleteById(result.selectedUsers[i].id).then(() => {
            this.reloadUserTable();
          });
          
        }
        this.selectionModel.clear();
      }
    });
    */
  }

  deviceIcon(isOnline: boolean): string {
    return isOnline
      ? this.deviceTableProfile.deviceIcon
      : this.deviceTableProfile.deviceIconOff;
  }

  parkingSpaceIcon(isOnline: boolean): string {
    return isOnline
      ? this.deviceTableProfile.parkingSpaceIcon
      : this.deviceTableProfile.parkingSpaceIconOff;
  }

  get title(): string {
    return this._title;
  }

  get parkingSpaces(): SpaceInterface[] {
    return this._parkingSpaces;
  }

  ngOnDestroy(): void {
    clearInterval(this._interval);
  }
}