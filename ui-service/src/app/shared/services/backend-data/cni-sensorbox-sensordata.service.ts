import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environment';
import { SensorDataInterface } from '@shared/interfaces/sensor-data.interface';

@Injectable({ providedIn: 'root' })
export class CniSensorDataService {
  private API_URL_SENSOR_SERVICE =
    environment.backend_url + ':8080/ENVIRONMENT_QUERY/resources/v1';

  constructor(private http: HttpClient) {}

  getAllMeasurementTypesOfSensorBox(sensorBoxId: string) {
    return this.http.get<string[]>(
      `${
        this.API_URL_SENSOR_SERVICE
      }/environmentinformation/sensorboxid/${sensorBoxId}/measurementtypes`
    );
  }

  getByNumberOfValues(
    sensorBoxId: string,
    sensorId: string,
    numberOfValues: number
  ) {
    return this.http.get<SensorDataInterface[]>(
      `${
        this.API_URL_SENSOR_SERVICE
      }/environmentinformation/${sensorBoxId}/${sensorId}/${numberOfValues}`
    );
  }
}
