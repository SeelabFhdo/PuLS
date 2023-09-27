import { Injectable } from "@angular/core";
import { environment } from "@environment";
import { KeycloakService } from "keycloak-angular";
import { KeycloakProfile } from "keycloak-js";


@Injectable({ providedIn: "root" })
export class KeyCloakAuthService {

    private AUTH_TOKEN_ID = 'authToken';


    userDetails: KeycloakProfile;
    profileUrl: string = environment.profileUrl;


    constructor(private keycloakService: KeycloakService) { }

    // token management methods

    setTokenIfNotExist() {
        if (localStorage.getItem(this.AUTH_TOKEN_ID) === null) {
        return this.setToken();
        }
    }

    public setToken(): Promise<string | void> {
        return this.keycloakService.getToken().then(token => {
            localStorage.setItem(this.AUTH_TOKEN_ID, token);
            return token;
        });
    }

    public getToken(): string {
        return localStorage.getItem(this.AUTH_TOKEN_ID);
    }

    clear() {
        localStorage.clear();
    }

    // authentication

    async login() {
        if (await this.keycloakService.isLoggedIn()) {
            this.userDetails = await this.keycloakService.loadUserProfile();
            //localStorage.setItem("email", this.userDetails.email);
            //localStorage.setItem("currentUser", JSON.stringify(this.userDetails));
          }
    }

    async logout() {
        this.clear();
        await this.keycloakService.logout();
    }
}
