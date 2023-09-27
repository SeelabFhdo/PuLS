import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environment';
import { ElectricSpaceInterface } from '@shared/interfaces/electric-space.interface';
import { SpaceInterface } from '@shared/interfaces/space.interface';

@Injectable({ providedIn: 'root' })
export class CniParkingSpacesService {
  private API_URL_SENSOR_SERVICE =
    environment.backend_url + ':8080/PARK_QUERY/resources/v1';

  constructor(private http: HttpClient) {}

  getAllElectrified() {
    return this.http.get<ElectricSpaceInterface[]>(
      `${this.API_URL_SENSOR_SERVICE}/electrifiedparkingspaces`
    );
  }

  getAllNoneElectrified() {
    return this.http.get<SpaceInterface[]>(
      `${this.API_URL_SENSOR_SERVICE}/parkingspaces`
    );
  }

  getElectrifiedById(id: string) {
    return this.http.get<ElectricSpaceInterface>(
      `${
        this.API_URL_SENSOR_SERVICE
      }/electrifiedparkingspace/id/${id}`
    );
  }

  getNoneElectrifiedById(id: string) {
    return this.http.get<SpaceInterface>(
      `${this.API_URL_SENSOR_SERVICE}/parkingspace/id/${id}`
    );
  }

  getByOriginalIds(ids: string[], electrifiedOrNone: string) {
    if (electrifiedOrNone === "ev-electrified") {
      return this.http.post<ElectricSpaceInterface[]>(`${
          this.API_URL_SENSOR_SERVICE
        }/electrifiedparkingspace/originalids`,
        ids
      ).toPromise();
    } else {
      return this.http.post<SpaceInterface[]>(
        `${this.API_URL_SENSOR_SERVICE}
        /parkingspace/originalids`,
        ids
      ).toPromise();
    }
  }
}
