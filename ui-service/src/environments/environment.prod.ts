import { KeycloakConfig } from "keycloak-angular";
import { environment as defaultEnvironment } from "./environment.defaults";

const keycloakConfig: KeycloakConfig = {
  url: 'https://auth.seelab.fh-dortmund.de/auth',
  realm: 'puls',
  clientId: 'puls',
  credentials: {
    secret: 'b642cf4b-c86b-4530-bd4d-2d71e34ea706'
  }
};

export const environment = {
  ...defaultEnvironment,
  
  production: true,
  backend_url: "http://puls.seelab.fh-dortmund.de",
  backend_request_update_interval: 30000,

  keycloakConfig,
  profileUrl: 'https://auth.seelab.fh-dortmund.de/auth/realms/puls/account/',
};
