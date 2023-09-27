import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { LocationInfoInterface } from '@shared/interfaces/location-info.interface';
import { OsmNominatimGeocoderService } from '@shared/services/external-data/osm-nominatim-geocoder.service';

import * as _ from 'lodash';
import { MapSearchService } from '@guestModule/map-search.service';
import { Router } from '@angular/router';
import { TranslocoService } from '@ngneat/transloco';
import { DateAdapter } from '@angular/material';

export enum ParkingSpaceType {
  ELECTRIFIED = 'ev-electrified',
  NONE_ELECTRIFIED = 'none',
}

export enum MetricType {
  KILOMETERS = 'KILOMETERS',
  MILES = 'MILES'
}

export enum PlugType {
  TYPE_P12 = 'P12'
}

// Validators for checking form content.
// Validators return null if there is no error. Otherwise, return a map of validation errors.
const dateValidator: ValidatorFn = (fg: FormGroup) => {
  const fromDate: string = fg.get('fromdate').value.toDateString();
  const fromTime: string = fg.get('fromtime').value;
  const toDate: string = fg.get('todate').value.toDateString();
  const toTime: string = fg.get('totime').value;
  const valid: boolean = (fromDate < toDate)
    || (fromDate === toDate && fromTime <= toTime);

  return valid
    ? null
    : { range: true };
};

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  private _currentDate: Date = new Date();

  // Format times as hh:mm so they work properly with the timepicker.
  private _currentTime: string = new Date().toLocaleString('de-DE').split(' ')[1].slice(0, 5);
  private _nowPlusTwoHours = new Date().getTime() + (2 * 60 * 60 * 1000);
  private _futureDefault = new Date(this._nowPlusTwoHours).toLocaleString('de-DE').split(' ')[1].slice(0, 5);

  destOptions: LocationInfoInterface[];
  selectedDestination: any = null;
  foundParkingSpaces: any[] = [];
  radiusOptions: number[] = [
    1, 5, 10, 20, 30, 50
  ];
  radius: number = 0;
  parkingSpaceTypeOptions: any[] = [
    { label: 'Parking-Spaces with EV-Station', value: ParkingSpaceType.ELECTRIFIED }, 
    { label: 'Parking-Spaces only', value: ParkingSpaceType.NONE_ELECTRIFIED }
  ];
  parkingSpaceType: ParkingSpaceType = null;
  plugTypeOptions: any[] = [
    { label: 'Plug-P12', value: PlugType.TYPE_P12 }
  ];
  plugType: PlugType = null;

  searchForm: FormGroup = this.formBuilder.group({
    destination: [''],
    fromdate: [new Date()],
    fromtime: [this._currentTime],
    todate: [new Date()],
    totime: [this._futureDefault],
  });

  constructor(
    private router: Router,
    private formBuilder: FormBuilder, 
    private geoCoderService: OsmNominatimGeocoderService,
    private mapSearchService: MapSearchService,
    //private translocoService: TranslocoService,
    //private adapter: DateAdapter<any>
    ) {}

  ngOnInit() {
    // debounce the "destination key input" method. (We should not call external servers for each key input!) 
    // (this method uses nominatim to request location information on the fly)
    // debouncing this method call delays the execution for a period of the specified time
    // to reduce excessive server calls
    this.onDestinationKeyInput = _.debounce(this.onDestinationKeyInput, 500);
  }

  get currentDate(): Date {
    return this._currentDate;
  }

  get currentTime(): string {
    return this._currentTime;
  }

  get futureDefault(): string {
    return this._futureDefault;
  }

  // Localizes calendar dates according to the selected language.
  /*
  private localizeCalendar(): void {
    this.translocoService.langChanges$.subscribe(lang => {
      switch (lang) {
        case 'en': {
          this.adapter.setLocale('en');
          break;
        }
        case 'de': {
          this.adapter.setLocale('de');
          break;
        }
      }
    });
  }
  */

  private getSelectedTimeSpan(): number[] {
    const startDate: Date = this.searchForm.get('fromdate').value;
    const startTime: string = this.searchForm.get('fromtime').value; // hh:mm
    const start: number = startDate.setHours(+startTime.substr(0, 2), +startTime.substr(3, 2));

    const endDate: Date = this.searchForm.get('todate').value;
    const endTime: string = this.searchForm.get('totime').value; // hh:mm
    const end: number = endDate.setHours(+endTime.substr(0, 2), +endTime.substr(3, 2));

    return [start, end];
  }

  /**
   * show possible destinations based on user input
   */
  onDestinationKeyInput() {
    const destination: string = this.searchForm.get('destination').value;
    if (destination != "") {
      // use the geo coder to get further location infos. 
      // Save the given destination options.
      this.geoCoderService.getLocationInfoByAddress(destination)
      .subscribe((destinations) => {
        this.destOptions = destinations;
      });
    } else {
      // unselect previous destination if there 
      // is no entry in the search form
      this.selectedDestination = null;
      this.radius = 0;
      this.updateMapRadiusDisplay(null);
      this.parkingSpaceType = null;
      this.plugType = null;
    }
  }

  onDestinationSelected(destination: any) {
    this.selectedDestination = destination;

    this.mapSearchService.setLocation({ 
      latitude: this.selectedDestination.lat, 
      longitude: this.selectedDestination.lon
    });
  }

  optionDisplayName(option: any) {
    return option.display_name;
  }

  updateMapRadiusDisplay(value: any) {
    if (value == null) {
      this.mapSearchService.setRadius(0);
      return;
    }

    const radius: number = +value.split(' ')[0];
    const radiusInMeters: number = radius * 1000;
    this.radius = radius;
    this.mapSearchService.setRadius(radiusInMeters);

    // try a submit on radius change (updates the search for parking spaces in range)
    if (this.parkingSpaceType != null || 
      (this.parkingSpaceType == "ev-electrified" && this.plugType != null)) {
      this.onSubmit();
    }
  }

  updateParkingSpaceTypeDisplay(value: ParkingSpaceType) {
    this.parkingSpaceType = value;
    this.plugType = null;
  }

  updatePlugTypeDisplay(value: PlugType) {
    this.plugType = value;
  }

  /**
   * search available parking spaces by the given criteria
   */
  onSubmit() {

    let selectedTimeSpan = this.getSelectedTimeSpan();

    this.router.navigate(['/search/results'], {
      queryParams: {
        destination: this.selectedDestination.display_name, 
        latitude: this.selectedDestination.lat, 
        longitude: this.selectedDestination.lon, 
        radius: this.radius, 
        from: selectedTimeSpan[0], 
        until: selectedTimeSpan[1],
        type: this.parkingSpaceType,
        plug: this.plugType
      }
    });
  }

}

/*
import { Component, OnInit } from '@angular/core';
import { FormControl,  FormGroup,  ValidatorFn,  Validators } from '@angular/forms';
import { DateAdapter } from '@angular/material';
import { Router } from '@angular/router';
import { MapSearchService } from '@guestModule/map-search.service';
import { TranslocoService } from '@ngneat/transloco';
import { LocationInfoInterface } from '@shared/interfaces/location-info.interface';
import { LocationInterface } from '@shared/interfaces/location.interface';
import { CniParkingSpacesService } from '@shared/services/backend-data/cni-parkingspaces.service';
import { OsmNominatimGeocoderService } from '@shared/services/external-data/osm-nominatim-geocoder.service';
import { Observable } from 'rxjs';
import { debounceTime, map } from 'rxjs/operators';

// Validators for checking form content.
// Validators return null if there is no error. Otherwise, return a map of validation errors.
const dateValidator: ValidatorFn = (fg: FormGroup) => {
  const fromDate: string = fg.get('fromdate').value.toDateString();
  const fromTime: string = fg.get('fromtime').value;
  const toDate: string = fg.get('todate').value.toDateString();
  const toTime: string = fg.get('totime').value;
  const valid: boolean = (fromDate < toDate)
    || (fromDate === toDate && fromTime <= toTime);

  return valid
    ? null
    : { range: true };
};

const plugValidator: ValidatorFn = (fg: FormGroup) => {
  const electric: boolean = fg.get('electric').value;
  const plugType: string = fg.get('plugtype').value;
  const valid: boolean = !electric || (electric && plugType !== '');

  return valid
  ? null
  : { plugRequired: true };
};

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  private _currentDate: Date = new Date();

  // Format times as hh:mm so they work properly with the timepicker.
  private _currentTime: string = new Date().toLocaleString('de-DE').split(' ')[1].slice(0, 5);
  private _nowPlusTwoHours = new Date().getTime() + (2 * 60 * 60 * 1000);
  private _futureDefault = new Date(this._nowPlusTwoHours).toLocaleString('de-DE').split(' ')[1].slice(0, 5);

  private _plugTypes: string[] = [];
  private _radiusOptions: string[] = [];
  private _submitted: boolean = false;
  private _searchForm: FormGroup;

  private _options: Observable<LocationInfoInterface[]>;

  get currentDate(): Date {
    return this._currentDate;
  }

  get currentTime(): string {
    return this._currentTime;
  }

  get futureDefault(): string {
    return this._futureDefault;
  }

  get plugTypes(): string[] {
    return this._plugTypes;
  }

  get radiusOptions(): string[] {
    return this._radiusOptions;
  }

  get submitted(): boolean {
    return this._submitted;
  }

  get searchForm(): FormGroup {
    return this._searchForm;
  }

  get options(): Observable<LocationInfoInterface[]> {
    return this._options;
  }

  constructor(private _router: Router, private _parkingService: CniParkingSpacesService, private _geoService: OsmNominatimGeocoderService,
    private _translocoService: TranslocoService, private _adapter: DateAdapter<any>, private _mapSearchService: MapSearchService) {
    this.populatePlugList();
    this.populateRadiusList();
    this.localizeCalendar();
  }

  ngOnInit(): void {
    this.initForm();
  }

  // Updates the options available in the typeahead input.
  public updateOptions(): void {
    const destination: string = this._searchForm.get('destination').value;
    this._options = this._geoService.getLocationInfoByAddress(destination);
  }

  public updateMapLocation(value): void {
    this._options.pipe(
      map(locations => locations.filter(loc => loc.display_name === value))
    ).subscribe(result => {
      const lat: number = +result[0]['lat'];
      const lon: number = +result[0]['lon'];
      const location: LocationInterface = {longitude: lon, latitude: lat};
      this._mapSearchService.setLocation(location);
    });
  }

  public updateMapRadius(value: string): void {
    const radius: number = +value.split(' ')[0];
    const radiusInMeters: number = radius * 1000;
    this._mapSearchService.setRadius(radiusInMeters);
  }

  // Checks that the form is valid before navigating to the results page.
  public onSubmit(): void {
    this._submitted = true;

    if (this._searchForm.valid) {
      const dest = this._searchForm.get('destination').value;
      let lat: number;
      let lon: number;

      this._options.pipe(
        map(locations => locations.filter(loc => loc.display_name === dest))
      ).subscribe(result => {
        lat = result[0]['lat'];
        lon = result[0]['lon'];
      },
      null,
      () => this.buildLink(dest, lat, lon)
      );
    }
  }

  private buildLink(dest: string, lat: number, lon: number): void {
    const rad: number = this._searchForm.get('radius').value.split(' ')[0];

    // Create the start and end times in milliseconds based on the date and time inputs.
    const startDate: Date = this._searchForm.get('fromdate').value;
    const startTime: string = this._searchForm.get('fromtime').value; // hh:mm
    const start: number = startDate.setHours(+startTime.substr(0, 2), +startTime.substr(3, 2));

    const endDate: Date = this._searchForm.get('todate').value;
    const endTime: string = this._searchForm.get('totime').value; // hh:mm
    const end: number = endDate.setHours(+endTime.substr(0, 2), +endTime.substr(3, 2));

    let plugType: string = 'none';
    if (this._searchForm.get('electric').value) {
      plugType = this._searchForm.get('plugtype').value;
    }

    this._router.navigate(['/search/results'], {
        queryParams: {destination: dest, latitude: lat, longitude: lon, radius: rad, from: start, until: end, plug: plugType}
    });
  }

  // Creates an array of plug types based on spots.
  // TODO: replace this with a direct backend query for all plug types.
  private populatePlugList(): void {
    this._parkingService.getAllElectrified().subscribe(spaces => {
      spaces.forEach(space => {
        const alreadyExists = this._plugTypes.find(i => i === space.pluginType) !== undefined;
        if (!alreadyExists) {
          this._plugTypes.push(space.pluginType);
        }
      });
    });
  }

  // Create a list of possible radius values.
  private populateRadiusList(): void {
    const step: number = 0.5;
    const max: number = 2;
    let value: number = 0.5;

    while (value <= max) {
      this._radiusOptions.push(value + ' km');
      value = +(value + step).toFixed(12);
    }
  }

  // Localizes calendar dates according to the selected language.
  private localizeCalendar(): void {
    this._translocoService.langChanges$.subscribe(lang => {
      switch (lang) {
        case 'en': {
          this._adapter.setLocale('en');
          break;
        }
        case 'de': {
          this._adapter.setLocale('de');
          break;
        }
      }
    });
  }

  // Initializes the search form with default values and validators.
  private initForm(): void {
    this._searchForm = new FormGroup({
      destination: new FormControl('', Validators.required),
      radius: new FormControl('', Validators.required),
      fromdate: new FormControl(new Date()),
      fromtime: new FormControl(this._currentTime),
      todate: new FormControl(new Date()),
      totime: new FormControl(this._futureDefault),
      electric: new FormControl(false),
      plugtype: new FormControl(''),
    }, [dateValidator, plugValidator]);
  }
}
*/