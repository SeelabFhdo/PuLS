import { Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { DeviceBrowserListService } from '@deviceBrowserModule/list-view/device-list.service';

import { DetailsPanelMonitorService } from './details-monitor.service';

@Component({
  selector: 'details-monitor-component',
  templateUrl: './details-monitor.component.html',
  styleUrls: ['./details-monitor.component.scss'],
})
export class DeviceBrowserDetailsPanelMonitorComponent implements OnInit, OnChanges {

  private _deviceType: string;
  private _data: any;

  // availability table fields
  dataSourceAvailabilityInfo: MatTableDataSource<any>;
  availabilityInfoTableColumns: string[] = ["index", "from", "to"];

  @Input()
  set data(data: any) {
    this._data = data;
  }
  get data(): any {
    return this._data;
  }

  get deviceBrowserListService(): DeviceBrowserListService {
    return this._deviceBrowserListService;
  }

  @Input()
  set deviceType(deviceType: string) {
    this._deviceType = deviceType;
  }
  get deviceType(): string {
    return this._deviceType;
  }

  @ViewChild("availabilityInfoTablePaginator", { static: false })
  availabilityInfoTablePaginator: MatPaginator;

  constructor(private detailsPanelMonitorService: DetailsPanelMonitorService,
    private _deviceBrowserListService: DeviceBrowserListService) {
      this.dataSourceAvailabilityInfo = new MatTableDataSource([]);
  }

  ngOnInit() {}

  ngOnChanges() {
    // refresh availability info table
    this.refreshAvailabilityInfoDataSource(
      this._data.device.availabilityPeriods
    );
  }

  private refreshAvailabilityInfoDataSource(availabilityInfos: any[]) {
    this.dataSourceAvailabilityInfo = new MatTableDataSource(availabilityInfos);
    this.dataSourceAvailabilityInfo.paginator = this.availabilityInfoTablePaginator;
  }

  /**
   * returns an instance of the core service of this component
   */
  get $(): DetailsPanelMonitorService {
    return this.detailsPanelMonitorService;
  }
}
