import { Injectable } from "@angular/core";
import { UserItemInterface } from "@shared/interfaces/userItem.interface";
import { KeycloakProfile } from "keycloak-js";

@Injectable({ providedIn: "root" })
export class UtilsService {
  constructor() {}

  assignOrDefault(assignValue: any, defaultValue: any): any {
    return assignValue != null || typeof assignValue !== "undefined"
      ? assignValue
      : defaultValue;
  }

  keyCloakProfileToUserItemInterface(userDetails: KeycloakProfile): UserItemInterface {
    return {
      email: userDetails.username,
      firstname: userDetails.firstName,
      lastname: userDetails.lastName,
      currentStatus: "",
      userRoles: ['ADMINISTRATOR']
    };
  }

  limitDigits(value: number): number {
    return Math.round((Number(value) + Number.EPSILON) * 100) / 100;
  }
}
