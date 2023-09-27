import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class OpenRouteService {
  private API_URL = 'https://api.openrouteservice.org/v2';

  constructor(private http: HttpClient) {}

  getWalkingTime(locations: number[][]) {
    const headers = {
      'Accept': 'application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8',
      'Content-Type': 'application/json',
      'Authorization': '5b3ce3597851110001cf62487b24c16d6aae4ac68d5f0d44f96d73fa'
    };
    const body =  {
      locations,
      metrics: ['duration'],
      sources: [0]
    };

    return this.http.post<any>( `${this.API_URL}/matrix/foot-walking`, body, { headers });
  }
}
