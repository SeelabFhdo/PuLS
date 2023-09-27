import { Component, Input, OnInit } from "@angular/core";
import { CniChargingInfoService } from "@shared/services/backend-data/cni-charginginfo.service";
import { environment } from "src/environments/environment.defaults";

@Component({
    selector: 'charging-spinner',
    templateUrl: './charging-spinner.component.html',
    styleUrls: ['./charging-spinner.component.scss'],
})
export class ChargingSpinnerComponent implements OnInit {

    componentSize: string = "200px";

    private _interval: any;
    private _stationId: string = "";

    private _status: string = "STATUS4";

    @Input()
    set stationId(stationId: string) {
        this._stationId = stationId;
    }

    get stationId(): string {
        return this._stationId;
    }

    get status(): string {
        return this._status;
    }

    constructor(private cniChargingInfoService: CniChargingInfoService) {}

    ngOnInit() {
        this.reload();

        this._interval = setInterval(() => {
            this.reload();
        }, environment.backend_request_update_interval);
    }

    reload() {
        this.cniChargingInfoService.getStatusInformation(this._stationId).subscribe((chargingSnapshot) => {
            this._status = chargingSnapshot.chargingStatus;
        });
    }

    ngOnDestroy() {
        clearInterval(this._interval);
    }
}