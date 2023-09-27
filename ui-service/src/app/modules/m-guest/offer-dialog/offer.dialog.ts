import { SelectionModel } from "@angular/cdk/collections";
import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { MatDialogRef, MatPaginator, MatTableDataSource, MAT_DIALOG_DATA } from "@angular/material";
import { Router } from "@angular/router";
import { ElectricSpaceInterface } from "@shared/interfaces/electric-space.interface";
import { CniParkingSpacesService } from "@shared/services/backend-data/cni-parkingspaces.service";

@Component({
    selector: "offer-parking-space-dialog",
    templateUrl: './offer.dialog.html',
    styleUrls: ['./offer.dialog.scss'],
  })
  export class OfferParkingSpaceDialog implements OnInit {

  dataSourceOwnSpots: MatTableDataSource<any>;
  ownSpots: any[] = [];

  selectionModel: SelectionModel<ElectricSpaceInterface>;

  displayedColumnsOwnSpots: string[] = [
    'selection',
    'icon',
    'offered',
    'parkingSpaceLink',
    'action',
  ];

  private _filterValue: string = '';
  
  @ViewChild('ownSpotsTablePaginator', { static: false })
  ownSpotsTablePaginator: MatPaginator;

  constructor(
      public dialogRef: MatDialogRef<OfferParkingSpaceDialog>,
      private bookingService: CniParkingSpacesService,
      private router: Router,
      @Inject(MAT_DIALOG_DATA) public data: any
      ) {
    this.dataSourceOwnSpots = new MatTableDataSource([]);
  }

  ngOnInit(): void {
    this.selectionModel = new SelectionModel<ElectricSpaceInterface>(true, []);
  }

  applyFilter(filterValue: string) {
    this._filterValue = filterValue;

    this.dataSourceOwnSpots.filter = filterValue.trim().toLowerCase();

    if (this.dataSourceOwnSpots.paginator) {
      this.dataSourceOwnSpots.paginator.firstPage();
    }
  }

  isAllSelected() {
    const numSelected = this.selectionModel.selected.length;
    const numRows = this.dataSourceOwnSpots.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected()
      ? this.selectionModel.clear()
      : this.dataSourceOwnSpots.data.forEach((row) => this.selectionModel.select(row));
  }

  getFormattedTime(isoTime: string): string {
    return new Date(isoTime).toLocaleString();
  }

  navigateToSpot(spotId: string) {
    this.dialogRef.close();
    this.router.navigateByUrl("/devicebrowser/ev/" + spotId + "/electrified");
  }

  onBack(): void {
    this.dialogRef.close();
  }

  openSelectedParkingSpaceDeleteConfirmDialog() {

  }

  openParkingSpaceProfileDialog() {

  }

  openParkingSpaceDeleteConfirmDialog() {
    
  }

  openAddUserDialog() {

  }

}