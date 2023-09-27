import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ChipStateInterface } from 'src/app/shared/interfaces/chip-state.interface';
import { DeviceBrowserListService } from '@deviceBrowserModule/list-view/device-list.service';
import { environment } from 'src/environments/environment';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-device-list-component',
  templateUrl: './device-list.component.html',
  styleUrls: ['./device-list.component.scss'],
})
export class DeviceBrowserListComponent implements OnInit, OnDestroy {
  private _onlineStatusChipStates: ChipStateInterface[] = [
    { selectionState: true, label: 'device-browser.list.ev.table.statusValue.active' },
    { selectionState: false, label: 'device-browser.list.ev.table.statusValue.inactive' },
  ];
  private _deviceType: string;
  private _title: string;
  private _isDeviceRouterLinkActive: boolean = true;
  private _interval: any;
  private _filterValue: string = '';

  dataSourceDevices: MatTableDataSource<any>;
  devices: any[] = [];
  deviceTableProfile: any;

  parkingOptions: any[] = [
    { label: 'device-browser.list.ev.parkingOptions.all', value: '-1' },
    { label: 'device-browser.list.ev.parkingOptions.onlyEVs', value: '0' },
    { label: 'device-browser.list.ev.parkingOptions.noEVs', value: '1' },
  ];
  parkingOptionSelection: any;

  @Input()
  set deviceType(deviceType: string) {
    this._deviceType = deviceType;
  }

  get deviceType(): string {
    return this._deviceType;
  }

  get title(): string {
    return this._title;
  }

  @ViewChild('devicesTablePaginator', { static: false })
  devicesTablePaginator: MatPaginator;
  constructor(
    private route: ActivatedRoute, 
    private deviceBrowserListService: DeviceBrowserListService,
    public dialog: MatDialog
    ) {
    this.parkingOptionSelection = sessionStorage.getItem(
      'device-browser-list/parkingOptionSelection'
    );

    this.dataSourceDevices = new MatTableDataSource([]);
  }

  ngOnInit(): void {
    // get device data from static route data parameter
    this.route.data.subscribe(data => {
      sessionStorage.setItem("sideNavSelectionIndex", data.sideNavSelectionIndex);
      this.deviceType = data.deviceType;
    });

    this.deviceTableProfile = this.deviceBrowserListService.getDeviceProfile(this._deviceType);
    this._title = this.deviceTableProfile.title;

    this.dataSourceDevices.paginator = this.devicesTablePaginator;

    // load data once on startup
    this.reloadDeviceTable();

    // start polling data frequently
    this._interval = setInterval(() => {
      this.reloadDeviceTable();
    }, environment.backend_request_update_interval);
  }

  /**
   * returns an instance of the core service of this component
   */
  $(): DeviceBrowserListService {
    return this.deviceBrowserListService;
  }

  private refreshDataSource(devices: any[]) {
    this.devices = devices;
    this.dataSourceDevices = new MatTableDataSource(this.devices);
    this.dataSourceDevices.paginator = this.devicesTablePaginator;
    this.applyFilter(this._filterValue);
  }

  reloadDeviceTable() {
    this.deviceBrowserListService.requestData(
      this.deviceType, 
      this.refreshDataSource.bind(this), 
      this.parkingOptionSelection
    );
  }

  onParkingOptionChange(event) {
    this.parkingOptionSelection = event.value;
    this.reloadDeviceTable();
  }

  applyFilter(filterValue: string) {
    this._filterValue = filterValue;

    this.dataSourceDevices.filter = filterValue.trim().toLowerCase();

    if (this.dataSourceDevices.paginator) {
      this.dataSourceDevices.paginator.firstPage();
    }
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

  onlineChipStateRow(row: any): ChipStateInterface {
    return this._onlineStatusChipStates[row.status === 'ACTIVE' ? 0 : 1];
  }

  onlineChipState(state: boolean): ChipStateInterface {
    return this._onlineStatusChipStates[state ? 0 : 1];
  }

  deviceRouterLinkActive(value: boolean) {
    this._isDeviceRouterLinkActive = value;
  }

  isDeviceRouterLinkActive(): boolean {
    return this._isDeviceRouterLinkActive;
  }

  ngOnDestroy() {
    sessionStorage.setItem(
      'device-browser-list/parkingOptionSelection',
      this.parkingOptionSelection
    );

    clearInterval(this._interval);
  }
}
