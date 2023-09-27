import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })
export class InternetServiceProviderService {
  private API_URL_IP = "http://ip-api.com";

  constructor(private http: HttpClient) {}

  get() {
    return this.http.get<any>(`${this.API_URL_IP}/json`);
  }
}
