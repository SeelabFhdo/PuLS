import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ChartDataSets } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { CniSensorDataService } from '@shared/services/backend-data/cni-sensorbox-sensordata.service';
import { UtilsService } from '@shared/services/helper/utils.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'sensor-value-chart-component',
  templateUrl: './sensor-value-chart.component.html',
  styleUrls: ['./sensor-value-chart.component.scss'],
})
export class SensorValueChartComponent
  implements OnInit, OnDestroy {
  private _gaugeSizeModifier: number = 0.065;
  private _gaugeThicknessModifier: number = 0.003;

  private _label: string = 'Label';
  private _unit: string = 'Unit';

  private _gaugeSettings: any;
  private _gaugeThresholds: any = {};

  private _chartDataSets: ChartDataSets[] = [];
  private _chartOptions: any = {};
  private _chartType: string;
  private _chartLabels: string[];
  private _chartColors: any[];
  private _chartFontSize: number;

  private _values: any[] = [];

  private _min: number;
  private _max: number;

  // -------------------------------------------
  private _sensorBoxId: string = '';   // <--- TODO REMOVE THIS. HANDLE IT FROM OUTSIDE THE COMPONENT
  private _interval: any;
  private _isFirstStartup: boolean = true;
  // -------------------------------------------

  _utilsService: UtilsService;

  @ViewChild(BaseChartDirective, { static: true }) chart: BaseChartDirective;
  constructor(
    private router: Router,
    private cniSensorDataService: CniSensorDataService,
    private utilsService: UtilsService
  ) {}

  ngOnInit(): void {
    this._utilsService = this.utilsService;
    this._sensorBoxId = this.router.url.split('/')[3];

    this._isFirstStartup = true;

    // init gauge
    this._gaugeSettings = {
      type: 'semi',
      size: window.innerWidth * this._gaugeSizeModifier,
      thick: Math.round(window.innerWidth * this._gaugeThicknessModifier),
      min: this._min,
      max: this._max,
      animate: 'true',
      duration: '2000',
      cap: 'round',
      label: this._label,
      append: this._unit,
      foregroundColor: '#001800',
      thresholdConfig: this._gaugeThresholds,
    };

    this.refreshChartView();

    this._interval = setInterval(() => {
      this._isFirstStartup = false;
      this.reloadData();
    }, environment.backend_request_update_interval);
  }

  /**
   * TODO remove this. try to handle this with a callback function from outside the component!!!
   */
  reloadData() {
    // if a sensor id is given then try to load the data directly and override values
    if (this._sensorBoxId !== '') {
      this.cniSensorDataService
        .getByNumberOfValues(
          this._sensorBoxId,
          this.label,
          environment.default_sensor_value_request_number
        )
        .subscribe(
          (sensorData) => {
            this._values = sensorData;
            this.refreshChartView();
          },
          (error) => {
            // console.log(error);
          }
        );
    } else {
      this.refreshChartView();
    }
  }

  refreshChartView() {
    // init chart
    this._chartOptions = {
      title: {
        display: false,
        text: this._label,
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
        backgroundColor: 'rgb(207, 231, 207, 0.5)',
        borderColor: '#001800',
        pointBackgroundColor: '#001800',
        pointBorderColor: '#001800',
        pointHoverBackgroundColor: '#001800',
        pointHoverBorderColor: '#001800',
      },
    ];

    this._chartType = 'line';

    const values: number[] = [];
    for (let i = 0; i < this._values.length; i++) {
      values.push(Number(this._values[i].value));
    }
    const labels: string[] = [];
    for (let i = 0; i < this._values.length; i++) {
      labels.push(new Date(this._values[i].timestamp).toLocaleString());
    }

    // reset data
    this._chartLabels = labels;
    this._chartDataSets = [{ data: values, label: this._label }];
  }

  resizeGauge() {
    this._gaugeSettings.size = window.innerWidth * this._gaugeSizeModifier;
    this._gaugeSettings.thick = Math.round(
      window.innerWidth * this._gaugeThicknessModifier
    );
  }

  @Input()
  set label(label: string) {
    this._label = label;
  }

  get label(): string {
    return this._label;
  }

  @Input()
  set unit(unit: string) {
    this._unit = unit;
  }

  get unit(): string {
    return this._unit;
  }

  @Input()
  set values(values: any[]) {
    this._values = values;
  }

  get latestValue(): any {
    return this._values.length > 0 ? this._values[this._values.length - 1] : 0;
  }

  @Input()
  set min(min: number) {
    this._min = min;
  }

  @Input()
  set max(max: number) {
    this._max = max;
  }

  @Input()
  set gaugeThresholds(gaugeThresholds: any) {
    this._gaugeThresholds = gaugeThresholds;
  }

  @Input()
  set chartFontSize(chartFontSize: number) {
    this._chartFontSize = chartFontSize;
  }

  get gaugeSettings(): any {
    return this._gaugeSettings;
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

  ngOnDestroy() {
    clearInterval(this._interval);
  }
}
