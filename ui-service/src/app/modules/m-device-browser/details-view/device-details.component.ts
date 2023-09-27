import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { DeviceDataProviderService } from '@deviceBrowserModule/details-view/device-details.service';
import { ChipStateInterface } from 'src/app/shared/interfaces/chip-state.interface';

@Component({
  selector: 'device-details-component',
  templateUrl: './device-details.component.html',
  styleUrls: ['./device-details.component.scss'],
})
export class DeviceBrowserDetailsPanelComponent implements OnInit {
  private _returnUrl: string = '/';
  private _deviceTypeUrl: string;
  private _deviceIdUrl: string;
  private _electrified: boolean = true;

  private _onlineStatusChipStates: ChipStateInterface[] = [
    { selectionState: true, label: 'device-browser.details.statusValue.active' },
    { selectionState: false, label: 'device-browser.details.statusValue.inactive' },
  ];

  constructor(
    private router: Router, 
    private deviceDataProviderService: DeviceDataProviderService
    ) {}

  ngOnInit() {
    // extract information from route
    this._deviceTypeUrl = this.router.url.split('/')[2];
    this._returnUrl = this.router.url.split('/')[1] + '/' + this._deviceTypeUrl;
    this._deviceIdUrl = this.router.url.split('/')[3];
    this._electrified = (this.router.url.split('/')[4] == 'electrified');

    this.deviceDataProviderService.loadDefaultValues(this._deviceTypeUrl);
    
    // load data from backend
    this.deviceDataProviderService.reloadDeviceData(
      this._deviceTypeUrl, this._deviceIdUrl, this._electrified);
  }

  /**
   * returns an instance of the core service of this component
   */
  $(): DeviceDataProviderService {
    return this.deviceDataProviderService;
  }

  get data(): any {
    return this.deviceDataProviderService.device;
  }

  get deviceType(): string {
    return this._deviceTypeUrl;
  }

  get returnUrl(): string {
    return this._returnUrl;
  }

  get isOnline(): boolean {
    if (this.data.device.parkingSpaceSize) {
      // parking space
      return this.data.device.activated;
    } else {
      // sensor unit
      return this.data.device.status === 'ACTIVE' ? true : false;
    }
  }

  get onlineChipState(): ChipStateInterface {
    return this._onlineStatusChipStates[this.isOnline ? 0 : 1];
  }
}
