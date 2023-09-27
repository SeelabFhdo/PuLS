import { Injectable } from '@angular/core';
import { ElectricSpaceInterface } from '@shared/interfaces/electric-space.interface';
import { LocationInterface } from '@shared/interfaces/location.interface';
import { SpaceInterface } from '@shared/interfaces/space.interface';
import { Observable, Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MapSearchService {

  private _location$: Observable<LocationInterface>;
  private _locationSubject: Subject<LocationInterface>;

  private _results$: Observable<SpaceInterface[] | ElectricSpaceInterface[]>;
  private _resultSubject: Subject<SpaceInterface[] | ElectricSpaceInterface[]>;

  private _radius$: Observable<number>;
  private _radiusSubject: Subject<number>;

  private _clear$: Observable<any>;
  private _clearSubject: Subject<any>;

  private _zoom$: Observable<number>;
  private _zoomSubject: Subject<number>;

  get location$(): Observable<LocationInterface> {
    return this._location$;
  }

  get results$(): Observable<SpaceInterface[] | ElectricSpaceInterface[]> {
    return this._results$;
  }

  get radius$(): Observable<number> {
    return this._radius$;
  }

  get clear$(): Observable<any> {
    return this._clear$;
  }

  get zoom$(): Observable<number> {
    return this._zoom$;
  }

  constructor() {
    this._locationSubject = new Subject<LocationInterface>();
    this._location$ = this._locationSubject.asObservable();

    this._resultSubject = new Subject<SpaceInterface[] | ElectricSpaceInterface[]>();
    this._results$ = this._resultSubject.asObservable();

    this._radiusSubject = new Subject<number>();
    this._radius$ = this._radiusSubject.asObservable();

    this._clearSubject = new Subject<any>();
    this._clear$ = this._clearSubject.asObservable();

    this._zoomSubject = new Subject<number>();
    this._zoom$ = this._zoomSubject.asObservable();
  }

  public setLocation(location: LocationInterface): void {
    this._locationSubject.next(location);
  }

  public setResults(results: SpaceInterface[] | ElectricSpaceInterface[]) {
    this._resultSubject.next(results);
  }

  public setRadius(radius: number) {
    this._radiusSubject.next(radius);
  }

  public clear() {
    this._clearSubject.next();
  }

  public zoom(zoomLevel: number) {
    this._zoomSubject.next(zoomLevel);
  }
}
