import { Component, Input, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { MatPaginator, MatTableDataSource } from "@angular/material";
import { CniChargingInfoService } from "@shared/services/backend-data/cni-charginginfo.service";
import { ChartDataSets } from "chart.js";
import { environment } from "src/environments/environment.defaults";

@Component({
    selector: 'statistics-sub-component',
    templateUrl: './statistics-sub.component.html',
    styleUrls: ['./statistics-sub.component.scss'],
})
export class StatisticsSubComponent implements OnInit, OnDestroy {

    public latestHeartBeat: string;
    public gridStatusCode: number = 10;

    public dataSourceChargingHistory: MatTableDataSource<any>;
    public displayedColumns: string[] = [
        //'chargingSnapshotNumber',
        'chargingSnapshotIcon',
        'chargingSnapshotTimestamp',
        'chargingSnapshotDetails',
    ];

    private _isFirstStartup = true;

    private _stationId: string;
    private _interval: any;

    private _chargingHistory: any[] = [];

    private _chartLabel: string = "";
    private _chartDataSets: ChartDataSets[] = [];
    private _chartOptions: any = {};
    private _chartType: string;
    private _chartLabels: string[];
    private _chartColors: any[];
    private _chartFontSize: number;

    @ViewChild('chargingHistoryTablePaginator', { static: false })
    chargingHistoryTablePaginator: MatPaginator;

    @Input()
    set stationId(stationId: string) {
        this._stationId = stationId;
    }

    get stationId(): string {
        return this._stationId;
    }

    get chartDataSets(): ChartDataSets[] {
        return this._chartDataSets;
    }
    
    get chartOptions(): any {
        return this._chartOptions;
    }

    get chartType(): string {
        return this._chartType;
    }

    get chartLabels(): string[] {
        return this._chartLabels;
    }

    get chartColors(): any[] {
        return this._chartColors;
    }

    constructor(private cniChargingInfoService: CniChargingInfoService) {

        // TEST TABLE DATA
        //this._chargingHistory.unshift({ timestamp: "1.9.2021, 15:01:09", amount: "10 kwh" });
        

        this.dataSourceChargingHistory = new MatTableDataSource(this._chargingHistory);

        setTimeout(() => {
            this.dataSourceChargingHistory.paginator = this.chargingHistoryTablePaginator;
        });
    }

    ngOnInit() {
        this._isFirstStartup = true;
        this.reload();

        this.refreshChartView();

        this._interval = setInterval(() => {
            this._isFirstStartup = false;
            this.reload();
        }, environment.backend_request_update_interval);
    }

    reload() {
        this.cniChargingInfoService.getKeepAliveInformation(this._stationId).subscribe((aliveMsg) => {  
            this.gridStatusCode = aliveMsg.gridStatus;
            this.latestHeartBeat = new Date(aliveMsg.timeStamp).toLocaleString();
        }); 

        // TODO: this.refreshChartView(); on fetched data ...
    }

    refreshChartView() {
        // init chart
        this._chartOptions = {
            title: {
            display: false,
            text: this._chartLabel,
            fontSize: this._chartFontSize,
            },
            legend: {
            display: false,
            position: 'bottom',
            },
            scales: {
            xAxes: [
                {
                ticks: {
                    fontSize: this._chartFontSize,
                },
                },
            ],
            yAxes: [
                {
                ticks: {
                    // max: 100,
                    // min: 0,
                    stepSize: 1000,
                    fontSize: this._chartFontSize,
                },
                },
            ],
            },
            responsive: true,
            maintainAspectRatio: false,
            animation: {
            duration: this._isFirstStartup ? 3000 : 0,
            },
        };

        this._chartColors = [
            {
            backgroundColor: '#cfe7cf',
            borderColor: '#001800',
            pointBackgroundColor: '#001800',
            pointBorderColor: '#001800',
            pointHoverBackgroundColor: '#001800',
            pointHoverBorderColor: '#001800',
            },
        ];

        this._chartType = 'bar';

        const values: number[] = [];
        /*
        for (let i = 0; i < this._values.length; i++) {
            values.push(Number(this._values[i].value));
        }
        */
        const labels: string[] = [];
        /*
        for (let i = 0; i < this._values.length; i++) {
            labels.push(new Date(this._values[i].timestamp).toLocaleString());
        }
        */

        // reset data
        this._chartLabels = labels;
        this._chartDataSets = [{ data: values, label: this._chartLabel }];
    }

    getChipIcon(gridStatusCode: number) {
        return (gridStatusCode <= 3 ? 'check_circle' : (gridStatusCode <= 6 ? 'warning_amber' : 'error_outline'));
    }

    getGridStatusPercentage(gridStatusCode: number): number {
        // min and max of the status code are swapped because the value decreases 
        // from 10 to 1 while 1 = 100% and 10 = 0%
        return ((gridStatusCode - 10) * 100) / (1 - 10);
    }

    ngOnDestroy() {
        clearInterval(this._interval);
    }
}
