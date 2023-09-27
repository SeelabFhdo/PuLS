import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MapSearchService } from '@guestModule/map-search.service';
import { IconKey, LeafletMapComponent } from '@shared/components/leaflet-map/leaflet-map.component';
import { LocationInterface } from '@shared/interfaces/location.interface';

const DORTMUND: LocationInterface = {latitude: 51.51494, longitude: 7.466};

@Component({
  selector: 'app-guest-main',
  templateUrl: './guest-main.component.html',
  styleUrls: ['./guest-main.component.scss']
})
export class GuestMainComponent implements OnInit {

  private _location: LocationInterface = DORTMUND;
  private _radius: number = 0;
  private _radiusRef: any;
  private _mapRef: LeafletMapComponent;

  get location(): LocationInterface {
    return this._location;
  }

  constructor(
    private route: ActivatedRoute, 
    private _mapSearchService: MapSearchService) 
    { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      sessionStorage.setItem("sideNavSelectionIndex", data.sideNavSelectionIndex);
    });
  }

  public onLeafletMapReady(leafletMap: LeafletMapComponent): void {

    // add custom marker icons
    leafletMap.produceCustomMarkerIcon(
      IconKey.EV,
      'assets/graphics/pulsEvStationMarker.png',
      'assets/graphics/markerShadow.png',
      [32, 37],
      [39, 37],
      [16, 36],
      [10, 31],
      [0, -37]
    );

    leafletMap.produceCustomMarkerIcon(
      IconKey.ParkingSpace,
        'assets/graphics/pulsParkingSpaceMarker.png',
        'assets/graphics/markerShadow.png',
        [32, 37],
        [39, 37],
        [16, 36],
        [10, 31],
        [0, -37]
    );

    leafletMap.produceCustomMarkerIcon(
      IconKey.Comb,
        'assets/graphics/pulsCombMarker.png',
        'assets/graphics/markerShadow.png',
        [24, 34],
        [39, 37],
        [12, 33],
        [10, 31],
        [0, -37]
    );

    leafletMap.initMarkerClusterGroup({
        iconCreateFunction(cluster) {
        return leafletMap.getCustomMarkerIcon(IconKey.Comb);
        },
        spiderfyOnMaxZoom: true,
        showCoverageOnHover: false,
        zoomToBoundsOnClick: false,
        maxClusterRadius: 40,
    });

    this._mapRef = leafletMap;

    this._mapSearchService.location$.subscribe(location => {
      this._mapRef.panTo(location);
      this._location = location;

      this.drawRadius();
      this._mapRef.removeMarkersFromCLusterGroup();
    });

    this._mapSearchService.results$.subscribe(results => {
      this._mapRef.removeMarkersFromCLusterGroup();
      results.forEach(result => {
        this.drawMarker(result);
      });
    });

    this._mapSearchService.radius$.subscribe(radius => {
      this._radius = radius;
      this.drawRadius();
    });

    this._mapSearchService.clear$.subscribe(() => {
      this._radius = 0;
      this.drawRadius();
      this._mapRef.removeMarkersFromCLusterGroup();
    });

    this._mapSearchService.zoom$.subscribe((zoomLevel: number) => {
       this._mapRef.zoomToLevel(zoomLevel);
    });
  }

  private drawRadius(): void {
    if (this._radiusRef !== undefined) {
      this._mapRef.removeCircle(this._radiusRef);
    }

    if (this._radius > 1) {
      this._radiusRef = this._mapRef.drawCircle(
        this._location, this._radius, '#001800', '#cfe7cf', 0.3, 2
      );
    } else {
      this._radiusRef = undefined;
    }
  }

  private drawMarker(markerData: any): void {
    this._mapRef.pinMarkerOnClusterGroup(
      [markerData.latitude, markerData.longitude],
      markerData.name,
      '<b>' +
      markerData.name +
      '</b><br>' +
      markerData.description,
      IconKey.EV
    );
  }
}
