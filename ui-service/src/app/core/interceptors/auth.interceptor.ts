import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";

import { Injectable } from "@angular/core";
import { KeyCloakAuthService } from "@shared/services/backend-data/keycloak-auth.service";
import { Observable } from "rxjs";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private authService: KeyCloakAuthService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone({
            setHeaders: {
                'Authorization': `Bearer ${this.authService.getToken()}`,
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
            },
        });
        return next.handle(req);
    }
}
