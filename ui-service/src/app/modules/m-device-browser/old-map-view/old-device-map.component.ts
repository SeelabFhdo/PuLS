import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslocoService } from '@ngneat/transloco';

import { OldLeafletMapComponent } from '@shared/components/old-leaflet-map/old-leaflet-map.component';
import { OldDeviceBrowserMapService } from './old-device-map.service';

// DEPRECATED
@Component({
  selector: 'old-app-device-map-component',
  templateUrl: './old-device-map.component.html',
  styleUrls: ['./old-device-map.component.scss'],
})
export class OldDeviceBrowserMapViewComponent implements OnInit {
  private _title: string = "?";

  private _initialLocation: number[] = [51.51494, 7.466];
  private _leafletRef: any = null;

  deviceTypeFromUrl: string;
  config: any;

  @Input()
  set title(title: string) {
    this._title = title;
  }
  get title(): string {
    return this._title;
  }

  @Input()
  set initialLocation(initialLocation: number[]) {
    this._initialLocation = initialLocation;
  }
  get initialLocation(): number[] {
    return this._initialLocation;
  }


  constructor(
    private router: Router,
    private deviceBrowserMapService: OldDeviceBrowserMapService,
    private translocoService: TranslocoService
    ) {}

  ngOnInit(): void {
    this.deviceTypeFromUrl = this.router.url.split('/')[2];
    this.config = this.deviceBrowserMapService.getConfig(this.deviceTypeFromUrl);
  }

  /**
   * returns an instance of the core service of this component
   */
  $(): OldDeviceBrowserMapService {
    return this.deviceBrowserMapService;
  }

  // callback triggers when the view child leaflet map has finished loading
  onLeafletMapReady(leafletMap: OldLeafletMapComponent) {
    // get last parking option selection from session storage
    const parkingOptionSelection = sessionStorage.getItem(
      "device-browser-list/parkingOptionSelection"
    );

    // setup the map view
    this.deviceBrowserMapService.setup(
      leafletMap, 
      this.deviceTypeFromUrl, 
      (parkingOptionSelection !== 'null' ? parkingOptionSelection : '-1')
    );

    // set local leaflet component reference
    this._leafletRef = leafletMap;
  }

  applyAddressSearch(searchString: string) {
    // not implemented.
  }
}
