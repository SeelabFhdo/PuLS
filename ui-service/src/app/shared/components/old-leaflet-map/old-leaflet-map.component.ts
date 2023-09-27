import {
  Component,
  OnInit,
  AfterViewInit,
  Input,
  OnDestroy,
  Output,
  EventEmitter,
} from "@angular/core";

import * as L from "leaflet";
import * as esri from "esri-leaflet";
import "leaflet.markercluster";

import { OsmNominatimGeocoderService } from "@shared/services/external-data/osm-nominatim-geocoder.service";
import { Observable } from "rxjs";

@Component({
  selector: "old-leaflet-map",
  templateUrl: "./old-leaflet-map.component.html",
  styleUrls: ["./old-leaflet-map.component.scss"],
})
export class OldLeafletMapComponent implements OnInit, AfterViewInit, OnDestroy {
  private _map: any;

  private _customMarkers: Map<string, any>;
  private _markerClusterGroup: any;
  private _initialLocation: number[] = [0.0, 0.0];
  private _initialZoomLevel: number = 3;
  private _minZoomLevel: number = 1;
  private _maxZoomLevel: number = 19;
  private _tileProvider: string =
    "https://maps.wikimedia.org/osm-intl/{z}/{x}/{y}.png"; // default fallback. overridden by @Input value

  @Output() onMapReady = new EventEmitter<any>();

  @Input()
  set initialLocation(geoLocation: number[]) {
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

  constructor(
    private osmNominatimGeocoderService: OsmNominatimGeocoderService
  ) {}

  ngOnInit(): void {
    this._customMarkers = new Map();
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  private initMap(): void {
    setTimeout(() => {
      // init map
      this._map = L.map("map", {
        center: this._initialLocation,
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
    markerIconKey: string,
    iconUrl: string,
    shadowUrl: string,
    iconSize: number[], //[x, y]
    shadowSize: number[],
    iconAnchor: number[],
    shadowAnchor: number[],
    popupAnchor: number[]
  ): void {
    var icon = L.icon({
      iconUrl: iconUrl,
      shadowUrl: shadowUrl,
      iconSize: iconSize,
      shadowSize: shadowSize,
      iconAnchor: iconAnchor,
      shadowAnchor: shadowAnchor,
      popupAnchor: popupAnchor,
    });

    this._customMarkers.set(markerIconKey, icon);
  }

  public getCustomMarkerIcon(markerIconKey: string): any {
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

  public pinMarkerOnClusterGroup(
    markerIconKey: string,
    geoLocation: number[],
    tooltip: string,
    popupContent: string
  ) {
    let marker: any;
    if (markerIconKey != "") {
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

  public pinMarker(
    markerIconKey: string,
    geoLocation: number[],
    tooltip: string,
    popupContent: string
  ) {
    if (markerIconKey != "") {
      L.marker(geoLocation, { icon: this._customMarkers.get(markerIconKey) })
        .addTo(this._map)
        .bindTooltip(tooltip)
        .bindPopup(popupContent);
    } else {
      L.marker(geoLocation)
        .addTo(this._map)
        .bindTooltip(tooltip)
        .bindPopup(popupContent);
    }
  }

  public drawRect(boundingBox: number[], color: string, weight: number) {
    L.rectangle(
      [
        [boundingBox[0], boundingBox[2]],
        [boundingBox[1], boundingBox[3]],
      ],
      { color: color, weight: weight }
    ).addTo(this._map);
  }

  public drawCircle(
    circleCenterCoords: number[],
    radius: number,
    color: string,
    fillColor: string,
    fillOpacity: number,
    weight: number
  ) {
    L.circle(circleCenterCoords, radius, {
      color: color,
      fillColor: fillColor,
      fillOpacity: fillOpacity,
      weight: weight,
    }).addTo(this._map);
  }

  public addBaseMapLayer(baseMapLayerKey: string) {
    const l = esri.basemapLayer(baseMapLayerKey);
    this._map.addLayer(l);
  }

  public panTo(geoLocation: number[]) {
    this._map.panTo(new L.LatLng(geoLocation[0], geoLocation[1]));
  }

  ngOnDestroy() {
    this._map.remove();
    this._map = null;
    this._customMarkers.clear();
  }
}
