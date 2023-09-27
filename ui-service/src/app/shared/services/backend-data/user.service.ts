import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environment';
import { UserItemInterface } from '@shared/interfaces/userItem.interface';

@Injectable({ providedIn: 'root' })
export class UserService {
  private API_URL_USER_SERVICE =
    environment.backend_url + ':8080/USER_QUERY/resources/v1';
  private API_URL_USER_SERVICE_COMMAND =
    environment.backend_url + ':8080/USER_COMMAND/resources/v1';

  constructor(private http: HttpClient) {}

  register(userData: UserItemInterface) {
    return this.http
      .post(
        `${this.API_URL_USER_SERVICE_COMMAND}/user?access_token=${
          JSON.parse(localStorage.getItem('authToken')).access_token
        }`,
        userData
      )
      .toPromise();
  }

  update(userData: UserItemInterface) {
    return this.http
      .put(
        `${this.API_URL_USER_SERVICE_COMMAND}/user?access_token=${
          JSON.parse(localStorage.getItem('authToken')).access_token
        }`,
        userData
      )
      .toPromise();
  }

  getAll() {
    return this.http.get<UserItemInterface[]>(
      `${this.API_URL_USER_SERVICE}/users?access_token=${
        JSON.parse(localStorage.getItem('authToken')).access_token
      }`
    );
  }

  getByEmail(email: string) {
    return this.http.get<UserItemInterface>(
      `${this.API_URL_USER_SERVICE}/user/${email}?access_token=${
        JSON.parse(localStorage.getItem('authToken')).access_token
      }`
    );
  }

  deleteById(id: string) {
    return this.http
      .delete(
        `${this.API_URL_USER_SERVICE}/user/id/${Number(id)}?access_token=${
          JSON.parse(localStorage.getItem('authToken')).access_token
        }`
      )
      .toPromise();
  }
}
