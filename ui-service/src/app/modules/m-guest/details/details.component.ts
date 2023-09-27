import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { MapSearchService } from '@guestModule/map-search.service';
import { ReserveSpotDialog } from '@guestModule/reserve-dialog/reserve.dialog';
import { ElectricSpaceInterface } from '@shared/interfaces/electric-space.interface';
import { UserItemInterface } from '@shared/interfaces/userItem.interface';
import { UserService } from '@shared/services/backend-data/user.service';
import { CniParkingSpacesService } from '@sharedServices/backend-data/cni-parkingspaces.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

  private _space: ElectricSpaceInterface;
  private _owner: UserItemInterface;
  private _favorited: boolean = false;

  private _from: number;
  private _until: number;

  loading: boolean = false;

  get spot() {
    return this._space;
  }

  get owner() {
    return this._owner;
  }

  get favorited() {
    return this._favorited;
  }

  constructor(
    private parkingService: CniParkingSpacesService, 
    private route: ActivatedRoute, 
    private location: Location,
    private mapSearchService: MapSearchService,
    private userService: UserService,
    public dialog: MatDialog
    ) { }

  public ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    this.route.queryParamMap.subscribe(params => {
      this._from = Number(params.get('from'));
      this._until = Number(params.get('until'));
    });

    this.retrieveData(id);
  }

  public toggleFavorite(): void {
    this._favorited = !this._favorited;
    // TODO: Update backend with favorited state.
  }

  private retrieveData(id: string): void {
    this.parkingService.getElectrifiedById(id).subscribe(result => {
      this._space = result;
      this.mapSearchService.clear();

      let loc = { 
        latitude: this._space.latitude, 
        longitude: this._space.longitude
      };

      // zoom in on marker position
      //this.mapSearchService.zoom(3);

      // move map to marker location
      this.mapSearchService.setLocation(loc);

      // show marker
      this.mapSearchService.setResults([this._space]);

      // TODO: this._favorited = result.favorited;

      // TODO: get owner data
      // this.userService.getByEmail(result.userId).subscribe(user => {
      //   this._owner = user;
      // });
    });
  }

  onReserveSpot(): void {
    // perform the booking process
    const dialogRef = this.dialog.open(ReserveSpotDialog, {
      disableClose: true,
      width: "40vw",
      height: "40vh",
      data: {
        spot: this._space,
        timeSpan: [
          new Date(this._from).toISOString(), 
          new Date(this._until).toISOString()
        ]
      },
    });
  }

  public goBack(): void {
    this.location.back();
  }
}
