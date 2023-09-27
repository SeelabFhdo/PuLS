import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from "@angular/router";
import { KeyCloakAuthService } from "@shared/services/backend-data/keycloak-auth.service";
import { KeycloakAuthGuard, KeycloakService } from "keycloak-angular";


@Injectable()
export class AuthGuard extends KeycloakAuthGuard {
  constructor(
    protected router: Router, 
    protected keycloakAngular: KeycloakService, 
    private authService: KeyCloakAuthService) {
    super(router, keycloakAngular);
  }

  isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise(async (resolve, reject) => {
      if (!this.authenticated) {
        this.keycloakAngular.login().then(() => this.authService.setToken());
        return;
      }

      await this.authService.setToken();

      const requiredRoles = route.data.roles;
      if (!requiredRoles || requiredRoles.length === 0) {
        return resolve(true);
      } else {
        if (!this.roles || this.roles.length === 0) {
          resolve(false);
        }
        let granted: boolean = false;
        for (const requiredRole of requiredRoles) {
          if (this.roles.indexOf(requiredRole) > -1) {
            granted = true;
            break;
          }
        }
        resolve(granted);
      }
    });
  }
}
