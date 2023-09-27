import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpErrorResponse,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError, of, timer } from "rxjs";
import { mergeMap } from 'rxjs/operators';
import { catchError, retryWhen, tap } from "rxjs/operators";

import { KeyCloakAuthService } from "@shared/services/backend-data/keycloak-auth.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authenticationService: KeyCloakAuthService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err) => {
        if (err instanceof HttpErrorResponse) {

          // unauthorized (401)
          if (err.status === 401) {
            // try to refresh token and attempt a retry
            this.authenticationService.setToken().then(token => {
              if (token) {
                // if a new token is present retry the last request
                this.retryAfterDelay(1000, 1);
              } else {
                // otherwise logout
                this.authenticationService.logout();
              }
            });
          }
  
          // unknown error (0)
          if (err.status === 0) {
              // not implemented.
          }

          const error = err.error.message || err.statusText;
          return throwError(error);
        }
      })
    );
  }

  retryAfterDelay(retryDelay: number, retryMaxAttempts: number): any {
    return retryWhen(errors => {
      return errors.pipe(
        mergeMap((err, count) => {
          if (count === retryMaxAttempts) {
            // logout if retry max count was reached
            this.authenticationService.logout();
          }
          return of(err).pipe(
            tap(error => console.log(`Retrying ${error.url}. Retry count ${count + 1}`)),
            mergeMap(() => timer(retryDelay))
          );
        })
      );
    });
  }
}
