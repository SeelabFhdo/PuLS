import { SpaceInterface } from './space.interface';

export interface ElectricSpaceInterface extends SpaceInterface {
    chargingStationId: string;
    chargingPricePerKWH: number;
    chargingType: string;
    parkingPricePerHour: number;
    pluginType: string;
}
