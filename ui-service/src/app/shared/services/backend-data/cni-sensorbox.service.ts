import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { environment } from "@environment";
import { DeviceDataInterface } from "@sharedInterfaces/device-data.interface";

@Injectable({ providedIn: "root" })
export class CniDeviceService {
  private API_URL_SENSOR_SERVICE =
    environment.backend_url + ":8080/ENVIRONMENT_QUERY/resources/v1";

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<DeviceDataInterface[]>(
      `${this.API_URL_SENSOR_SERVICE}/environmentsensorunits`
    );
  }

  getById(id: string) {
    return this.http.get<DeviceDataInterface>(
      `${
        this.API_URL_SENSOR_SERVICE
      }/environmentsensorunit/sensorboxid/${id}`
    );
  }
}
