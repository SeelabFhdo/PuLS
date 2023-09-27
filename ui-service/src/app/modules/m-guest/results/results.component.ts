import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MapSearchService } from '@guestModule/map-search.service';
import { LocationDataInterface } from '@shared/interfaces/location-data.interface';
import { CniParkingSpacesSearchService } from '@shared/services/backend-data/cni-parkingspaces-search.service';
import { OpenRouteService } from '@shared/services/external-data/open-route.service';
import { CniParkingSpacesService } from '@sharedServices/backend-data/cni-parkingspaces.service';

enum Sort {
  HighPrice,
  LowPrice,
  HighDistance,
  LowDistance
}

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss']
})
export class ResultsComponent implements OnInit {

  queryParams: Map<string, any> = new Map();
  foundParkingSpaces: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private parkingSpaceService: CniParkingSpacesService,
    private parkingSpaceSearchService: CniParkingSpacesSearchService,
    private mapSearchService: MapSearchService
    ) {}

  ngOnInit(): void {
    this.getURLParamsAndSearchForResults();
  }

  private getURLParamsAndSearchForResults(): void {
    this.route.queryParamMap.subscribe(params => {
      
      this.queryParams.set('destination', params.get('destination'));
      this.queryParams.set('latitude', params.get('latitude'));
      this.queryParams.set('longitude', params.get('longitude'));
      this.queryParams.set('radius', params.get('radius'));
      this.queryParams.set('from', params.get('from'));
      this.queryParams.set('until', params.get('until'));
      this.queryParams.set('type', params.get('type'));
      this.queryParams.set('plug', params.get('plug'));

      this.searchForResults();
    });
  }

  /**
 * perform a backend request to get all corresponding parking space objects by their given locations
 * @param locations array of LocationDataInterface. The locations given by the location search service.
 * @param parkingSpaceType string filter to specify the parking space type. Options:
 * "ev-electrified" or "none"
 */
    private _fetchParkingSpaceObjectsByLocations(
    locations: LocationDataInterface[], parkingSpaceType: string): Promise<any> {
    let refIds = locations.map(loc => loc.refId);
      return this.parkingSpaceService.getByOriginalIds(refIds, parkingSpaceType);
  }

  private _applyLocalSearchCriteriaFilter() {

    // given time interval form the user
    let from = this.queryParams.get('from');
    let until = this.queryParams.get('until');

    if (!from || !until) return;
    
    this.foundParkingSpaces.forEach(function (availParkingSpot) {
      availParkingSpot.availabilityPeriods.forEach(function (availPeriod) {
        // time interval for each availability time period of the parking spots
        let start = new Date(availPeriod.start).getTime();
        let end = new Date(availPeriod.end).getTime();

        // TODO check overlapping intervals. Maybe use moment.js
        // TODO filter/remove foundParkingSpaces if given interval is not within availability period.

        // TODO check if there where already bookings for this parking spot in the same time period.
      });
    }); 
  }

  private searchForResults() {

    // metric is currently hard coded here. (add a drop down menu in the search component later)
    let metric = 'KILOMETERS';
    let lat = this.queryParams.get('latitude');
    let lon = this.queryParams.get('longitude')
    let rad = this.queryParams.get('radius');
    let type = this.queryParams.get('type');
    let plug = this.queryParams.get('plug');

    let radInMeters: number = rad * 1000;

    // perform a backend request to search the actual parking spaces by 
    // the given criteria within the puls platform.
    this.parkingSpaceSearchService
    .findNearFiltered(lat, lon, rad, metric, type + ((plug != null) ? "-" + plug : ""))
    .subscribe((foundLocations) => {
      // fetch parking spaces by given locations from the search service
      this._fetchParkingSpaceObjectsByLocations(foundLocations, type)
      .then((parkingSpaceObjects) => {
        this.foundParkingSpaces = parkingSpaceObjects;
        this._applyLocalSearchCriteriaFilter();
        this.mapSearchService.setRadius(radInMeters);
        this.mapSearchService.setLocation({ latitude: lat, longitude: lon});
        this.mapSearchService.setResults(this.foundParkingSpaces);
      });
    });
  }

  public goBack(): void {
    this.mapSearchService.clear();
    this.location.back();
  }

  /*
  private _destination: string;
  private _results: SpaceInterface[] | ElectricSpaceInterface[] = [];
  private _durations: Map<string, number> = new Map();
  Sort: typeof Sort = Sort;

  get destination() {
    return this._destination;
  }

  get results() {
    return this._results;
  }

  get durations() {
    return this._durations;
  }

  constructor(private parkingService: CniParkingSpacesService, private route: ActivatedRoute, private location: Location,
    private _openRouteService: OpenRouteService, private _mapSearchService: MapSearchService) { }

  ngOnInit() {
    this.getURLParams();
  }

  public goBack(): void {
    this.location.back();
  }

  public sort(sort: Sort): void {
    switch (sort) {
      case Sort.HighDistance: {
        this._results.sort((r1, r2) => {
          if (this._durations.get(r1.id) < this._durations.get(r2.id)) {
            return 1;
          }

          if (this._durations.get(r1.id) > this._durations.get(r2.id)) {
            return -1;
          }

          return 0;
        });
        break;
      }
      case Sort.LowDistance: {
        this._results.sort((r1, r2) => {
          if (this._durations.get(r1.id) > this._durations.get(r2.id)) {
            return 1;
          }

          if (this._durations.get(r1.id) < this._durations.get(r2.id)) {
            return -1;
          }

          return 0;
        });
        break;
      }
      case Sort.HighPrice: {
        this._results.sort((r1, r2) => {
          if (r1.parkingPricePerHour < r2.parkingPricePerHour) {
              return 1;
          }

          if (r1.parkingPricePerHour > r2.parkingPricePerHour) {
              return -1;
          }

          return 0;
        });
        break;
      }
      case Sort.LowPrice: {
        this._results.sort((r1, r2) => {
          if (r1.parkingPricePerHour > r2.parkingPricePerHour) {
              return 1;
          }

          if (r1.parkingPricePerHour < r2.parkingPricePerHour) {
              return -1;
          }

          return 0;
        });
        break;
      }
    }
  }

  public formatDuration(duration: number): string {
    const hours = Math.floor(duration / 60 / 60);
    const minutes = Math.floor(duration / 60) - (60 * hours);
    let timeString: string = '';

    if (hours) {
      timeString = hours + ' hr ';
    }

    return timeString + minutes + ' min walk';
  }

  private getURLParams(): void {
    let latitude: number;
    let longitude: number;
    let radius: number;
    let from: number;
    let until: number;
    let plugtype: string;

    this.route.queryParamMap.subscribe(params => {
      this._destination = params.get('destination');
      latitude = +params.get('latitude');
      longitude = +params.get('longitude');
      radius = +params.get('radius');
      from = +params.get('from');
      until = +params.get('until');
      plugtype = params.get('plug');

      this.getResults(latitude, longitude, radius, from, until, plugtype);
    });
  }

  private getResults(latitude: number, longitude: number, radius: number, from: number, until: number, plugtype: string): void {
    // Temporary until we have a method of searching by location.
    //this.parkingService.getAllNoneElectrified().then(spaces => {
    //  this._results = spaces;
    //
    //  this._mapSearchService.setResults(spaces);
    //},
    //null,
    //() => this.getWalkingTimes(latitude, longitude));
  }

  private getWalkingTimes(latitude: number, longitude: number): void {
    // Open Route Service expects an array of [long, lat].
    // Start the array with the source destination (the search query).
    const locations: number[][] = [[longitude, latitude]];

    this._results.forEach(result => {
      const location: number[] = [result.longitude, result.latitude];
      locations.push(location);
    });

    this._openRouteService.getWalkingTime(locations).subscribe(response => {
      for (let i = 0; i < this._results.length; i++) {
        // "response" returns an array of duration information in the same order as this._results but
        // includes the source destination (index 0) which we don't care about.
        const durationIndex = i + 1;
        this._durations.set(this._results[i].id, response.durations[0][durationIndex]);
      }
    });
  }
  */
}



// test the search service
//===================================
/*
this.parkingSpaceSearchService
.findNearFiltered(51.514244, 7.468429, 20, MetricType.KILOMETERS, ParkingSpaceType.ELECTRIFIED)
.subscribe((locations) => {
  this._fetchParkingSpaceObjectsByLocations(locations, ParkingSpaceType.ELECTRIFIED);
});
*/
//===================================