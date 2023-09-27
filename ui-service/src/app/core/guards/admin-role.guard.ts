import { Injectable } from "@angular/core";
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from "@angular/router";

@Injectable({ providedIn: "root" })
export class AdminRoleGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // get current logged in user data with role info
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));

    if (currentUser.userRoles.includes("ADMINISTRATOR")) {
      //user is admin. Let the user pass.
      return true;
    }

    //stop! not an admin, so redirect back.
    this.router.navigate(["/"]);
    return false;
  }
}
