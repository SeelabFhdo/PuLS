import { AvailabilityPeriodInterface } from './availability-period.interface';

export interface SpaceInterface {
    id: string;
    originalId: string;
    name: string;
    description: string;
    ownerId: number;
    activated: boolean;
    availabilityPeriods: AvailabilityPeriodInterface[];
    blocked: boolean;
    createdDate: Date;
    lastModifiedDate: Date;
    latitude: number;
    longitude: number;
    offered: boolean;
    parkingPricePerHour: number;
    parkingSpaceSize: string;
}
