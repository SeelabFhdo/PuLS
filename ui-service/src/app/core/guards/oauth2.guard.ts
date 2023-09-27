import { Injectable } from "@angular/core";
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from "@angular/router";
import { OAuth2Service } from "@shared/services/backend-data/oauth2.service";

@Injectable({ providedIn: "root" })
export class OAuth2Guard implements CanActivate {
  constructor(private router: Router, private authService: OAuth2Service) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const currentAccessToken = this.authService.accessToken;

    if (currentAccessToken && localStorage.getItem("currentUser")) {
      //user is authenticated. Return true. Let the user pass.
      return true;
    }

    //stop! not logged in, so redirect back to login page and return false.
    this.router.navigate(["/login"]);
    return false;
  }
}
