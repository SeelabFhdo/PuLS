import { Injectable } from "@angular/core";
import { ParameterFilterTabInterface } from '@shared/interfaces/param-filter-tab.interface';

@Injectable({ providedIn: "root" })
export class DetailsPanelMonitorService {

    constructor() {}

    paramFilterTabs(filter: string): ParameterFilterTabInterface[] {
        return this._paramFilterTabs.filter(tab => tab.filter == filter);
    }

    private _paramFilterTabs: ParameterFilterTabInterface[] = [
        {
            label: 'device-browser.details-monitor.psu.tabs.telemetry',
            icon: 'timeline',
            filter: 'psu',
            context: 'psuMonitor'
        },
        {
            label: 'device-browser.details-monitor.ev.tabs.overview',
            icon: 'ev_station',
            filter: 'ev',
            context: 'evMonitor'
        },
        {
            label: 'device-browser.details-monitor.ev.tabs.statistics',
            icon: 'wysiwyg',
            filter: 'ev',
            context: 'evStatistics'
        }
    ];

    // the backend does not return a unit at the moment.
    // As a quick solution we return the units hardcoded until the problem is solved
    getSensorInfoHardCoded(sensorType: string): any {
        let result = { unit: 'unit', min: 0, max: 0 };
        switch (sensorType) {
        case 'humidity': {
            result = { unit: '%', min: 0, max: 100 };
            break;
        }
        case 'temperature': {
            result = { unit: '°C', min: 0, max: 0 };
            break;
        }
        case 'particulate2': {
            result = { unit: 'ppm', min: 0, max: 0 };
            break;
        }
        case 'particulate10': {
            result = { unit: 'ppm', min: 0, max: 0 };
            break;
        }
        case 'light': {
            result = { unit: '?', min: 0, max: 0 };
            break;
        }
        }
        return result;
    }

}