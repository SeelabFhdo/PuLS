import { Injectable } from '@angular/core';
import { environment } from '@environment';
import { CniParkingSpacesService } from '@shared/services/backend-data/cni-parkingspaces.service';

import { CniSensorDataService } from '../../../shared/services/backend-data/cni-sensorbox-sensordata.service';
import { CniDeviceService } from '../../../shared/services/backend-data/cni-sensorbox.service';

@Injectable({ providedIn: 'root' })
export class DeviceDataProviderService {
  private _deviceData: object;

  // inject rest interfaces
  constructor(
    private cniDeviceService: CniDeviceService,
    private cniSensorDataService: CniSensorDataService,
    private cniParkingSpaceService: CniParkingSpacesService
  ) {}

  get device(): object {
    return this._deviceData;
  }

  loadDefaultValues(deviceType: string) {
    switch (deviceType) {
      case 'psu': {
        this._deviceData = {
          device: {
            id: '-1',
            name: 'Unknown Device',
            desc: 'no data available',
            lat: '0',
            lng: '0',
            status: 'OFFLINE',
            type: 'VIRTUAL',
          },
          sensors: [],
        };
        break;
      }
      case 'ev': {
        this._deviceData = {
          device: {
            id: '-1',
            name: 'Unknown Device',
            desc: 'no data available',
            lat: '0',
            lng: '0',
            status: false,
          },
          sensors: [],
        };
        break;
      }
    }
  }

  /**
  reloads the complete device data with meta info about the device and the environment.
  It also loads the environment sensors and its sensor values.
  */
  reloadDeviceData(deviceType: string, deviceId: string, electrified: boolean) {
    switch (deviceType) {
      case 'psu': {
        // load device data by the given id
        this.cniDeviceService.getById(deviceId).subscribe(
          (deviceData) => {
            const sensors: object[] = [];

            // load list of measurement types available for the sensor data request
            this.cniSensorDataService
              .getAllMeasurementTypesOfSensorBox(deviceData.sensorBoxId)
              .subscribe((measurementTypes) => {
                // try to load sensor values for each available measurement type
                for (
                  let i = 0, p = Promise.resolve();
                  i < measurementTypes.length;
                  i++
                ) {
                  const measurementType = measurementTypes[i];
                  p = p.then(
                    () =>
                      new Promise((resolve) => {
                        this.cniSensorDataService
                          .getByNumberOfValues(
                            deviceData.sensorBoxId,
                            measurementType,
                            environment.default_sensor_value_request_number
                          )
                          .subscribe((sensorData) => {

                            sensors.push({
                              type: measurementType,
                              values: sensorData,
                            });

                            // resolve the inner promise now
                            resolve();
                          });
                      })
                  );
                }

                // assign data
                this._deviceData = {
                  device: {
                    id: deviceData.sensorBoxId,
                    name: deviceData.name,
                    desc: deviceData.description,
                    lat: deviceData.latitude,
                    lng: deviceData.longitude,
                    status: deviceData.status,
                    type: deviceData.sensorType,
                  },
                  sensors,
                };
              });
          },
          (error) => {
            // load defaults on error
            this.loadDefaultValues(deviceType);
          }
        );
        break;
      }

      case 'ev': {
        if (electrified) {
          // electrified
          this.cniParkingSpaceService.getElectrifiedById(deviceId).subscribe(
            (data) => {
              this._deviceData = {
                device: {
                  id: data.id,
                  originalId: data.originalId,
                  name: data.name,
                  desc: data.description,
                  ownerId: data.ownerId,
                  parkingPricePerHour: data.parkingPricePerHour,
                  activated: data.activated,
                  blocked: data.blocked,
                  offered: data.offered,
                  lat: data.latitude,
                  lng: data.longitude,
                  availabilityPeriods: data.availabilityPeriods,
                  status: data.activated,
                  chargingStationId: data.chargingStationId,
                  parkingSpaceSize: data.parkingSpaceSize,
                  chargingPricePerKWH: data.chargingPricePerKWH,
                  chargingType: data.chargingType,
                  pluginType: data.pluginType,
                },
              };
            },
            (error) => {}
          );
        } else {
          // none electrified
          this.cniParkingSpaceService
          .getNoneElectrifiedById(deviceId)
          .subscribe(
            (data) => {
              this._deviceData = {
                device: {
                  id: data.id,
                  originalId: data.originalId,
                  name: data.name,
                  desc: data.description,
                  ownerId: data.ownerId,
                  parkingPricePerHour: data.parkingPricePerHour,
                  activated: data.activated,
                  blocked: data.blocked,
                  offered: data.offered,
                  lat: data.latitude,
                  lng: data.longitude,
                  availabilityPeriods: data.availabilityPeriods,
                  status: data.activated,
                  parkingSpaceSize: data.parkingSpaceSize,
                },
              };
            },
            (error) => {}
          );
        }
        break;
      }

    }
  }
}
