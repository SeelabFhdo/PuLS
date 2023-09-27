import { Component, OnInit } from "@angular/core";
import { KeyCloakAuthService } from "@shared/services/backend-data/keycloak-auth.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
})
export class AppComponent implements OnInit {
  title = "UIService";

  constructor(
    private authService: KeyCloakAuthService
    ) {}

  async ngOnInit() {
    this.authService.login();
  }
}
