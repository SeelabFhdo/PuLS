import { Injectable } from '@angular/core';
import { TranslocoService } from '@ngneat/transloco';
import { SpaceInterface } from '@shared/interfaces/space.interface';
import { CniParkingSpacesService } from '@shared/services/backend-data/cni-parkingspaces.service';
import { CniDeviceService } from '@shared/services/backend-data/cni-sensorbox.service';

@Injectable({ providedIn: 'root' })
export class DeviceBrowserListService {

  constructor(
    private cniDeviceService: CniDeviceService,
    private cniParkingSpaceService: CniParkingSpacesService,
    private translocoService: TranslocoService
    ) {}

  getDeviceProfile(deviceType: string): any {
    let profile = {
      title: '?',
      deviceListLabel: 'n/a',
      deviceIcon: '',
      deviceIconOff: '',
      parkingSpaceIcon: '',
      parkingSpaceIconOff: '',
      displayedColumnsDevices: [],
    };

    switch (deviceType) {
      case 'psu':
        {
          profile = {
            title: 'device-browser.list.psu.title',
            deviceListLabel: 'Registered Pollution Sensor Units',
            deviceIcon: 'assets/graphics/pulsSensorUnit.png',
            deviceIconOff: 'assets/graphics/pulsSensorUnitOff.png',
            parkingSpaceIcon: '',
            parkingSpaceIconOff: '',
            displayedColumnsDevices: [
              'deviceIcon',
              'deviceId',
              'deviceTitle',
              'deviceSensorType',
              'deviceLocation',
              'deviceState',
            ],
          };
        }
        break;
      case 'ev':
        {
          profile = {
            title: 'device-browser.list.ev.title',
            deviceListLabel: 'Registered Vehicle Parking Spaces',
            deviceIcon: 'assets/graphics/pulsEvStation.png',
            deviceIconOff: 'assets/graphics/pulsEvStationOff.png',
            parkingSpaceIcon: 'assets/graphics/pulsParkingSpace.png',
            parkingSpaceIconOff: 'assets/graphics/pulsParkingSpaceOff.png',
            displayedColumnsDevices: [
              'deviceIcon',
              'deviceTitle',
              'ownerId',
              'parkingSpaceSize',
              'parkingPricePerHour',
              'deviceLocation',
              'availability',
              'evstation',
              'blocked',
              'offered',
              'activated',
            ],
          };
        }
        break;
    }
    return profile;
  }

  requestData(deviceType: string, callback: (data: any[]) => any, parkingOptionSelection?: any): void {
    switch (deviceType) {
      case 'psu': {
        this.cniDeviceService.getAll().subscribe((pollutionSensorUnits) => {
          callback(pollutionSensorUnits);
        })
      }
      break;
      case 'ev': {
        Promise.all([
          this.cniParkingSpaceService.getAllElectrified().toPromise(),
          this.cniParkingSpaceService.getAllNoneElectrified().toPromise()
        ]).then((values) => {
          const electrifiedParkingSpots: SpaceInterface[] = values[0];
          const noneElectrifiedParkingSpots: SpaceInterface[] = values[1];
          switch (parkingOptionSelection) {
            case '0': { callback(electrifiedParkingSpots); break; }
            case '1': { callback(noneElectrifiedParkingSpots); break; }
            default: { callback(electrifiedParkingSpots.concat(noneElectrifiedParkingSpots)); }
          }
        })
      }
      break;
    }
  }

  evStationDetailsSneakPeakString(data: any): string {
    const locoPath = 'device-browser.list.ev.table.sneakPeaks.evStation.';
    let result: string = '';
    result += this.translocoService.translate(locoPath + 'avail') + '\n';
    result += this.translocoService.translate(locoPath + 'price') + data.parkingPricePerHour + ' €' + '\n';
    result += this.translocoService.translate(locoPath + 'type') + data.chargingType + '\n';
    result += this.translocoService.translate(locoPath + 'plug') + data.pluginType + '\n';
    return result;
  }

  parkingSpaceAvailabilitySneakPeakString(data: any): string {
    const locoPath = 'device-browser.list.ev.table.sneakPeaks.';
    let result: string = '';
    for (let i = 0; i < data.availabilityPeriods.length; i++) {
      const availPeriod = data.availabilityPeriods[i];
      result +=
        this.translocoService.translate(locoPath + 'availPeriod') +
        (i + 1) +
        ': \n' +
        this.splitLocaleString(availPeriod.start, -1) +
        ' - ' +
        this.splitLocaleString(availPeriod.end, -1) +
        '\n';
    }
    return result;
  }

  splitLocaleString(dateString: string, splitPart: number): string {
    if (dateString === undefined || dateString == null) { return 'n/a'; }
    if (splitPart === -1) { return new Date(dateString).toLocaleString(); }
    return new Date(dateString).toLocaleString().split(',')[splitPart];
  }

  splitLocationInfoString(locationAddressString): string[] {
    if (locationAddressString === undefined || locationAddressString == null) {
      return ['n/a'];
    }
    return locationAddressString.split(',');
  }
}
