import { Component, Input, OnInit } from "@angular/core";
import { UtilsService } from "@shared/services/helper/utils.service";

@Component({
    selector: 'dual-gauge',
    templateUrl: './dual-gauge.component.html',
    styleUrls: ['./dual-gauge.component.scss'],
})
export class DualGaugeComponent implements OnInit {

    componentSize: string = "100px";

    // default settings
    v1Settings: any = {
        init: 0,
        max: 230,
        fullUnit: "Volts",
        unit: "V"
    };

    v2Settings: any = {
        init: 0,
        max: 16,
        fullUnit: "Ampere",
        unit: "A"
    };

    v1: number = 0;
    v2: number = 0;

    lastV1: number = 0;
    lastV2: number = 0;

    lbl: string = "";
    ftr: string = "---";

    constructor(private utilsService: UtilsService) {
        this.v1 = this.v1Settings.init;
        this.v2 = this.v2Settings.init;
    }

    ngOnInit() {}

    calcAngle(value: number, max: number): number {
        return (value * 180) / max;
    }

    calcAngleCss(value: number, max: number): string {
        return String("rotate(" + this.calcAngle(value, max) + "deg)");
    }

    @Input()
    set label(text: string) {
        this.lbl = text;
    }

    @Input()
    set value1(value: number) {
        let newValue = Number((value < 0) ? 0 : 
        ((value >= this.v1Settings.max) ? this.v1Settings.max : value));
        if (newValue != this.v1) {
            this.lastV1 = this.v1;
            this.v1 = this.utilsService.limitDigits(newValue);
        }
    }

    @Input()
    set value2(value: number) {
        let newValue = Number((value < 0) ? 0 : 
        ((value >= this.v2Settings.max) ? this.v2Settings.max : value));
        if (newValue != this.v2) {
            this.lastV2 = this.v2;
            this.v2 = this.utilsService.limitDigits(newValue);
        }
    }
}