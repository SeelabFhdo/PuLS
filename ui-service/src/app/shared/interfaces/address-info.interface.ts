import { FeatureInterface } from './feature.interface';

export interface AddressInfoInterface {
    type: string;
    license: string;
    features: FeatureInterface[];
}
