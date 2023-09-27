import { NgModule } from "@angular/core";

import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { TranslocoModule} from '@ngneat/transloco';


import { MaterialUiModule } from "@shared/modules/material-ui.module";

import { NgxGaugeModule } from "ngx-gauge";
import { ChartsModule } from "ng2-charts";
import { CountToModule } from "angular-count-to";

import { DeviceBrowserDetailsPanelComponent } from "./details-view/device-details.component";
import { DeviceBrowserDetailsPanelMonitorComponent } from "./details-monitor/details-monitor.component";
import { SensorValueChartComponent } from "@shared/components/sensor-value-chart/sensor-value-chart.component";
import { DeviceBrowserListComponent } from "./list-view/device-list.component";
import { DeviceBrowserMapViewComponent } from "./map-view/device-map.component";
import { StatisticsSubComponent } from './details-monitor/statistics/statistics-sub.component';

import { LeafletMapComponent } from "@shared/components/leaflet-map/leaflet-map.component";

import { DualGaugeComponent } from "@shared/components/dual-gauge/dual-gauge.component";
import { ThreePhaseDisplayComponent } from "@shared/components/three-phase-display/three-phase-display.component";
import { ChargingSpinnerComponent } from "@shared/components/charging-spinner/charging-spinner.component";

// only for the showcase
///////////////
import { OldLeafletMapComponent } from "@shared/components/old-leaflet-map/old-leaflet-map.component";
import { OldDeviceBrowserMapViewComponent } from "./old-map-view/old-device-map.component";
///////////////

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslocoModule,
    NgxGaugeModule,
    ChartsModule,
    CountToModule,
    MaterialUiModule,
  ],
  exports: [
    DeviceBrowserDetailsPanelComponent,
    DeviceBrowserDetailsPanelMonitorComponent,
    SensorValueChartComponent,
    DeviceBrowserListComponent,
    DeviceBrowserMapViewComponent,
    StatisticsSubComponent,
    LeafletMapComponent,
    DualGaugeComponent,
    ThreePhaseDisplayComponent,
    ChargingSpinnerComponent,

    // only for the showcase
    OldLeafletMapComponent,
    OldDeviceBrowserMapViewComponent,
  ],
  declarations: [
    DeviceBrowserDetailsPanelComponent,
    DeviceBrowserDetailsPanelMonitorComponent,
    SensorValueChartComponent,
    DeviceBrowserListComponent,
    DeviceBrowserMapViewComponent,
    StatisticsSubComponent,
    LeafletMapComponent,
    DualGaugeComponent,
    ThreePhaseDisplayComponent,
    ChargingSpinnerComponent,

    // only for the showcase
    OldLeafletMapComponent,
    OldDeviceBrowserMapViewComponent,
  ],
  entryComponents: [],
  providers: [],
})
export class DeviceBrowserModule {}
