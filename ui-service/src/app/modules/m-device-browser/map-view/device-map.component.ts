import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IconKey, LeafletMapComponent } from '@shared/components/leaflet-map/leaflet-map.component';
import { DeviceDataInterface } from '@shared/interfaces/device-data.interface';
import { ElectricSpaceInterface } from '@shared/interfaces/electric-space.interface';
import { LocationInterface } from '@shared/interfaces/location.interface';
import { SpaceInterface } from '@shared/interfaces/space.interface';
import { CniParkingSpacesService } from '@shared/services/backend-data/cni-parkingspaces.service';
import { CniDeviceService } from '@shared/services/backend-data/cni-sensorbox.service';

const DORTMUND: LocationInterface = {latitude: 51.51494, longitude: 7.466};

@Component({
  selector: 'app-device-map-component',
  templateUrl: './device-map.component.html',
  styleUrls: ['./device-map.component.scss'],
})
export class DeviceBrowserMapViewComponent implements OnInit {
  private _title: string = '';
  private _initialLocation: LocationInterface = DORTMUND;
  private _deviceTypeFromUrl: string;
  private _leafletRef: LeafletMapComponent;

  get title(): string {
    return this._title;
  }

  get initialLocation(): LocationInterface {
    return this._initialLocation;
  }

  get deviceTypeFromUrl(): string {
    return this._deviceTypeFromUrl;
  }

  constructor(private router: Router, private cniDeviceService: CniDeviceService,
    private cniParkingSpacesService: CniParkingSpacesService) { }

  public ngOnInit(): void {
    this._deviceTypeFromUrl = this.router.url.split('/')[2];
  }

  // callback triggers when the view child leaflet map has finished loading
  public onLeafletMapReady(leafletMap: LeafletMapComponent): void {
    // get last parking option selection from session storage
    const parkingOptionSelection = sessionStorage.getItem(
      'device-browser-list/parkingOptionSelection'
    );

    // setup the map view
    this.setup(
      this._deviceTypeFromUrl,
      (parkingOptionSelection !== 'null' ? parkingOptionSelection : '-1')
    );

    this._leafletRef = leafletMap;
  }

  public applyAddressSearch(searchString: string): void {
    // TODO
  }

  setup(deviceType: string, parkingOptionSelection: string): void {
    switch (deviceType) {
      case 'psu': {
        this._title = 'device-browser.map.psu.title';

        this.createCustomIcon(IconKey.PSU, 'assets/graphics/pulsSensorUnitMarker.png');
        this.createCustomIcon(IconKey.SensorComb, 'assets/graphics/pulsSensorCombMarker.png');
        this.initMarkerGroup(IconKey.SensorComb);

        // get all sensor devices
        this.cniDeviceService.getAll().subscribe(devices => {
          this.pinMarker(devices, IconKey.PSU);
        });

        break;
      }
      case 'ev': {
        this._title = 'device-browser.map.ev.title';

        this.createCustomIcon(IconKey.EV, 'assets/graphics/pulsEvStationMarker.png');
        this.createCustomIcon(IconKey.ParkingSpace, 'assets/graphics/pulsParkingSpaceMarker.png');
        this.createCustomIcon(IconKey.Comb, 'assets/graphics/pulsCombMarker.png');
        this.initMarkerGroup(IconKey.Comb);

        // pin markers for all electrified parking spaces
        if (parkingOptionSelection === '-1' || parkingOptionSelection === '0') {
          this.cniParkingSpacesService.getAllElectrified().subscribe(devices => {
            this.pinMarker(devices, IconKey.EV);
          });
        }

        // pin markers for all none electrified parking spaces
        if (parkingOptionSelection === '-1' || parkingOptionSelection === '1') {
          this.cniParkingSpacesService.getAllNoneElectrified().subscribe(devices => {
            this.pinMarker(devices, IconKey.ParkingSpace);
          });
        }

        break;
      }
    }
  }

  private createCustomIcon(key: IconKey, iconUrl: string): void {
    this._leafletRef.produceCustomMarkerIcon(
      key,
      iconUrl,
      'assets/graphics/markerShadow.png',
      [32, 37],
      [39, 37],
      [16, 36],
      [10, 31],
      [0, -37]
    );
  }

  private initMarkerGroup(key: IconKey): void {
    this._leafletRef.initMarkerClusterGroup({
      iconCreateFunction() {
        return this._leafletRef.getCustomMarkerIcon(key);
      },
      spiderfyOnMaxZoom: true,
      showCoverageOnHover: false,
      zoomToBoundsOnClick: false,
      maxClusterRadius: 40,
    });
  }

  private pinMarker(devices: SpaceInterface[] | ElectricSpaceInterface[] | DeviceDataInterface[], key: IconKey): void {
    devices.forEach(device => {
      let id: string;
      if (this._deviceTypeFromUrl === 'ev') {
        id = device.id;
      } else {
        id = device.sensorBoxId;
      }

      this._leafletRef.pinMarkerOnClusterGroup(
        [device.longitude, device.latitude],
        device.name,
        '<b>' +
          device.name +
          '</b><br>' +
          device.description +
          "<br><a href='./devicebrowser/" + this._deviceTypeFromUrl + '/' +
          id +
          "'>Open details</a>",
        key
      );
    });
  }
}
