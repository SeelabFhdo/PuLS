import { GeometryInterface } from './geometry.interface';
import { PropertyInterface } from './property.interface';

export interface FeatureInterface {
    type: string;
    properties: PropertyInterface;
    bbox: number[];
    geometry: GeometryInterface;
}
