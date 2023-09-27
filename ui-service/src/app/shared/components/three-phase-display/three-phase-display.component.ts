import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { CniChargingInfoService } from "@shared/services/backend-data/cni-charginginfo.service";
import { environment } from "src/environments/environment.defaults";

@Component({
    selector: 'three-phase-display',
    templateUrl: './three-phase-display.component.html',
    styleUrls: ['./three-phase-display.component.scss'],
})
export class ThreePhaseDisplayComponent implements OnInit, OnDestroy {

    private _interval: any;
    private _stationId: string = "";

    l1: any = { v: 0, a: 0 };
    l2: any = { v: 0, a: 0 };
    l3: any = { v: 0, a: 0 };

    @Input()
    set stationId(stationId: string) {
        this._stationId = stationId;
    }

    get stationId(): string {
        return this._stationId;
    }

    constructor(private cniChargingInfoService: CniChargingInfoService) {}

    ngOnInit() {
        this.reload();

        this._interval = setInterval(() => {
            this.reload();
        }, environment.backend_request_update_interval);
    }

    reload() {
        this.cniChargingInfoService.getLatestSensorValue(this._stationId, "current_L1").subscribe((sensorSnapshot) => {
            this.l1.a = sensorSnapshot.value;
        });

        this.cniChargingInfoService.getLatestSensorValue(this._stationId, "current_L2").subscribe((sensorSnapshot) => {
            this.l2.a = sensorSnapshot.value;
        });

        this.cniChargingInfoService.getLatestSensorValue(this._stationId, "current_L3").subscribe((sensorSnapshot) => {
            this.l3.a = sensorSnapshot.value;
        });

        this.cniChargingInfoService.getLatestSensorValue(this._stationId, "voltage_L1").subscribe((sensorSnapshot) => {
            this.l1.v = sensorSnapshot.value;
        });

        this.cniChargingInfoService.getLatestSensorValue(this._stationId, "voltage_L2").subscribe((sensorSnapshot) => {
            this.l2.v = sensorSnapshot.value;
        });

        this.cniChargingInfoService.getLatestSensorValue(this._stationId, "voltage_L3").subscribe((sensorSnapshot) => {
            this.l3.v = sensorSnapshot.value;
        });
    }

    ngOnDestroy() {
        clearInterval(this._interval);
    }

}