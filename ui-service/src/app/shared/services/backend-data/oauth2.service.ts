import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";

import { environment } from "@environment";

import { map } from "rxjs/operators";

@Injectable({ providedIn: "root" })
export class OAuth2Service {
  private API_URL_OAUTH2_SERVICE =
    environment.backend_url + ":8080/security_service/oauth/token";

  public currentAccessToken: Observable<Object>;
  private currentAccessTokenSubject: BehaviorSubject<Object>;

  constructor(private http: HttpClient) {
    //bind the current user subject to the current user data from the session storage gathered as json
    this.currentAccessTokenSubject = new BehaviorSubject<string>(
      JSON.parse(localStorage.getItem("authToken"))
    );

    //make the current user observable
    this.currentAccessToken = this.currentAccessTokenSubject.asObservable();
  }

  public get accessToken(): Object {
    return this.currentAccessTokenSubject.value;
  }

  private authenticate(email: string, password: string) {
    // setup oauth2 header
    const headers = {
      // "<client id>:<client secret>"
      Authorization: "Basic " + btoa("PuLS_UI:password"),
      "Content-type": "application/x-www-form-urlencoded",
    };

    // setup oauth2 request body
    const body = new HttpParams()
      .set("username", email)
      .set("password", password)
      .set("grant_type", "password");

    // perform rest request against the oauth backend service with the given body and headers
    // return the oauth access token as response on success
    return this.http.post(`${this.API_URL_OAUTH2_SERVICE}`, body, { headers });
  }

  login(email: string, password: string) {
    return this.authenticate(email, password).pipe(
      map((authToken) => {
        if (authToken) {
          // store access token
          localStorage.setItem("authToken", JSON.stringify(authToken));

          this.currentAccessTokenSubject.next(authToken);
        }
        return authToken;
      })
    );
  }

  logout() {
    localStorage.removeItem("currentUser");
    localStorage.removeItem("authToken");
    this.currentAccessTokenSubject.next(null);
  }
}
