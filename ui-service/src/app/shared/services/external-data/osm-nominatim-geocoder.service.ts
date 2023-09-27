import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddressInfoInterface } from '@shared/interfaces/address-info.interface';
import { LocationInfoInterface } from '@shared/interfaces/location-info.interface';
import { LocationInterface } from '@shared/interfaces/location.interface';

@Injectable({ providedIn: 'root' })
export class OsmNominatimGeocoderService {
  private API_URL_NOMINATIM = 'https://nominatim.openstreetmap.org';

  constructor(private http: HttpClient) {}

  getLocationInfoByAddress(address: string) {
    return this.http.get<LocationInfoInterface[]>(
      `${this.API_URL_NOMINATIM}/search?format=json&q=${address}`
    );
  }

  getAddressByGeoLocation(location: LocationInterface) {
    return this.http.get<AddressInfoInterface>(
      `${this.API_URL_NOMINATIM}/reverse?format=geojson&lat=${location.latitude}&lon=${location.longitude}`
    );
  }
}
