import { AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { LocationInterface } from '@shared/interfaces/location.interface';
import * as L from 'leaflet';
import 'leaflet.markercluster';

export enum IconKey {
  PSU = 'psuMarker',
  EV = 'evMarker',
  ParkingSpace = 'parkingSpaceMarker',
  Comb = 'pulsCombMarker',
  SensorComb = 'pulsSensorCombMarker'
}

@Component({
  selector: 'app-leaflet-map',
  templateUrl: './leaflet-map.component.html',
  styleUrls: ['./leaflet-map.component.scss'],
})
export class LeafletMapComponent implements OnInit, AfterViewInit, OnDestroy {
  private _map: any;

  private _customMarkers: Map<IconKey, any>;
  private _markerClusterGroup: any;
  private _initialLocation: LocationInterface = {latitude: 0, longitude: 0};
  private _initialZoomLevel: number = 3;
  private _minZoomLevel: number = 1;
  private _maxZoomLevel: number = 19;
  private _tileProvider: string =
    'https://maps.wikimedia.org/osm-intl/{z}/{x}/{y}.png'; // default fallback. overridden by @Input value

  @Output() onMapReady = new EventEmitter<any>();

  @Input()
  set initialLocation(geoLocation: LocationInterface) {
    this._initialLocation = geoLocation;
  }

  @Input()
  set initialZoomLevel(zoomLevel: number) {
    this._initialZoomLevel = zoomLevel;
  }

  @Input()
  set minZoomLevel(minZoomLevel: number) {
    this._minZoomLevel = minZoomLevel;
  }

  @Input()
  set maxZoomLevel(maxZoomLevel: number) {
    this._maxZoomLevel = maxZoomLevel;
  }

  @Input()
  set tileProvider(tileProviderString: string) {
    this._tileProvider = tileProviderString;
  }

  constructor() {}

  ngOnInit(): void {
    this._customMarkers = new Map();
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  private initMap(): void {
    setTimeout(() => {
      // init map
      this._map = L.map('map', {
        center: [this._initialLocation.latitude, this._initialLocation.longitude],
        zoom: this._initialZoomLevel,
      });

      const tiles = L.tileLayer(this._tileProvider, {
        minZoom: this._minZoomLevel,
        maxZoom: this._maxZoomLevel,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      });

      tiles.addTo(this._map);

      // init marker cluster group
      this.initMarkerClusterGroup(null);

      // emit map ready event
      this.onMapReady.emit(this);
    });
  }

  public get map(): any {
    return this._map;
  }

  public produceCustomMarkerIcon(
    markerIconKey: IconKey,
    iconUrl: string,
    shadowUrl: string,
    iconSize: number[], // [x, y]
    shadowSize: number[],
    iconAnchor: number[],
    shadowAnchor: number[],
    popupAnchor: number[]
  ): void {
    const icon = L.icon({
      iconUrl,
      shadowUrl,
      iconSize,
      shadowSize,
      iconAnchor,
      shadowAnchor,
      popupAnchor,
    });

    this._customMarkers.set(markerIconKey, icon);
  }

  public getCustomMarkerIcon(markerIconKey: IconKey): any {
    return this._customMarkers.get(markerIconKey);
  }

  public initMarkerClusterGroup(config: any) {
    if (config != null) {
      this._markerClusterGroup = L.markerClusterGroup(config);
    } else {
      this._markerClusterGroup = L.markerClusterGroup();
    }

    this._map.addLayer(this._markerClusterGroup);
  }

  public pinMarkerOnClusterGroup(geoLocation: number[], tooltip: string, popupContent: string, markerIconKey?: IconKey): any {
    let marker: any;
    if (markerIconKey) {
      marker = L.marker(geoLocation, {
        icon: this._customMarkers.get(markerIconKey),
      })
        .bindTooltip(tooltip)
        .bindPopup(popupContent);
    } else {
      marker = L.marker(geoLocation)
        .bindTooltip(tooltip)
        .bindPopup(popupContent);
    }

    this._markerClusterGroup.addLayer(marker);
  }

  public removeMarkersFromCLusterGroup(): void {
    this._markerClusterGroup.clearLayers();
  }

  public removeMarker(markerRef): void {
    this._map.removeLayer(markerRef);
  }

  public drawCircle(circleCenterCoords: LocationInterface, radius: number, color: string, fillColor: string, fillOpacity: number,
    weight: number): any {
    const circleRef = L.circle([circleCenterCoords.latitude, circleCenterCoords.longitude], radius, {
      color,
      fillColor,
      fillOpacity,
      weight,
    }).addTo(this._map);

    return circleRef;
  }

  public removeCircle(circleRef): void {
    this._map.removeLayer(circleRef);
  }

  public panTo(location: LocationInterface) {
    this._map.panTo(new L.LatLng(location.latitude, location.longitude));
  }

  public zoomToLevel(zoomLevel: number) {
    this._map.zoomIn(zoomLevel);
  }

  ngOnDestroy() {
    this._map.remove();
    this._map = null;
    this._customMarkers.clear();
  }
}
