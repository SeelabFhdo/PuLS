import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environment';
import { LocationDataInterface } from '@shared/interfaces/location-data.interface';

@Injectable({ providedIn: 'root' })
export class CniParkingSpacesSearchService {
  private API_URL_SENSOR_SERVICE =
    environment.backend_url + ':8080/PARKINGSPACE_QUERY/resources/v1'; //:8051/resources/v1

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<LocationDataInterface[]>(
      `${this.API_URL_SENSOR_SERVICE}/locationdatainterface/locationData`
    );
  }

  getById(id: string) {
    return this.http.get<LocationDataInterface>(
      `${
        this.API_URL_SENSOR_SERVICE
      }/locationdatainterface/locationData/${id}`
    );
  }

  findNear(lat: number, lng: number, distance: number, metric: string) {
    return this.http.get<LocationDataInterface[]>(
        `${this.API_URL_SENSOR_SERVICE}/locationdatainterface/locationData/findNear?lat=${
            lat}&lng=${lng}&distance=${distance}&metric=${metric}`
      );
  }

  findNearFiltered(lat: number, lng: number, distance: number, metric: string, extFilter: string) {
    return this.http.get<LocationDataInterface[]>(
        `${this.API_URL_SENSOR_SERVICE}/locationdatainterface/locationData/findNearFiltered?lat=${
            lat}&lng=${lng}&distance=${distance}&metric=${metric}&filter=${extFilter}`
      );
  }

  findByAddress(searchString: string) {
    return this.http.get<LocationDataInterface[]>(
        `${this.API_URL_SENSOR_SERVICE}/locationdatainterface/locationData/findByAddress?q=${
            searchString}`
      );
   }

   findByAddressFiltered(searchString: string, extFilter: string) {
    return this.http.get<LocationDataInterface[]>(
        `${this.API_URL_SENSOR_SERVICE}/locationdatainterface/locationData/findByAddress?q=${
            searchString}&filter=${extFilter}`
      );
   }
}
