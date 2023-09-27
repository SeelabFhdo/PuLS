import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environment';

@Injectable({ providedIn: 'root' })
export class CniChargingInfoService {
  private API_URL_CHARGING_INFO_SERVICE =
    environment.backend_url + ':8080/PARK_QUERY/resources/v1';

  constructor(private http: HttpClient) {}

  getLatestSensorValue(chargingStationId: string, infoType: string) {
    return this.http.get<any>(
      `${this.API_URL_CHARGING_INFO_SERVICE}/charginginformation/${chargingStationId}/${infoType}/latest`
    );
  }

  getStatusInformation(chargingStationId: string) {
    return this.http.get<any>(
      `${this.API_URL_CHARGING_INFO_SERVICE}/charginginformation/${chargingStationId}/status/latest`
    );
  }

  getKeepAliveInformation(chargingStationId: string) {
    return this.http.get<any>(
      `${this.API_URL_CHARGING_INFO_SERVICE}/charginginformation/${chargingStationId}/keepalive/latest`
    );
  }
}
