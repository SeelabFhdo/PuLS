import { KeycloakConfig } from "keycloak-angular";

const keycloakConfig: KeycloakConfig = {
  url: 'https://auth.seelab.fh-dortmund.de/auth',
  realm: 'puls',
  clientId: 'puls',
  credentials: {
    secret: 'b642cf4b-c86b-4530-bd4d-2d71e34ea706'
  }
};

export const environment = {
  production: false,

  platform_title: "PuLS",
  platform_version: "1.0.0",
  platform_dev: "IDiAL - FH Dortmund",
  platform_dev_link: "http://seelab.fh-dortmund.de/index.php",

  backend_url: "http://localhost",
  backend_request_update_interval: 30000,
  default_sensor_value_request_number: 10,

  keycloakConfig,
  profileUrl: 'https://auth.seelab.fh-dortmund.de/auth/realms/puls/account/',

  debug_usr_id: 1
};
