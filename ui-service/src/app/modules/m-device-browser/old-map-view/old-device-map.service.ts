import { Injectable } from "@angular/core";
import { OldLeafletMapComponent } from '@shared/components/old-leaflet-map/old-leaflet-map.component';
import { CniParkingSpacesService } from '@shared/services/backend-data/cni-parkingspaces.service';
import { CniDeviceService } from '@shared/services/backend-data/cni-sensorbox.service';

// DEPRECATED

@Injectable({ providedIn: "root" })
export class OldDeviceBrowserMapService {

    constructor(
        private cniDeviceService: CniDeviceService,
        private cniParkingSpacesService: CniParkingSpacesService
        ) {}

    getConfig(deviceType: string): any {
        let config = {};
        switch (deviceType) {
            case 'psu': {
                config = {
                    title: "device-browser.map.psu.title"
                };
                break;
            }
            case 'ev': {
                config = {
                    title: "device-browser.map.ev.title"
                };
                break;
            }
        }
        return config;
    }

    setup(leafletMap: OldLeafletMapComponent, deviceType: string, parkingOptionSelection: string): any {
        let config = {};
        switch (deviceType) {
            case 'psu': {
                // add custom marker icon
                leafletMap.produceCustomMarkerIcon(
                    'psuMarker',
                    'assets/graphics/pulsSensorUnitMarker.png',
                    'assets/graphics/markerShadow.png',
                    [32, 37],
                    [39, 37],
                    [16, 36],
                    [10, 31],
                    [0, -37]
                );
            
                leafletMap.produceCustomMarkerIcon(
                    'pulsSensorCombMarker',
                    'assets/graphics/pulsSensorCombMarker.png',
                    'assets/graphics/markerShadow.png',
                    [32, 37],
                    [39, 37],
                    [16, 36],
                    [10, 31],
                    [0, -37]
                );

                leafletMap.initMarkerClusterGroup({
                    iconCreateFunction(cluster) {
                      return leafletMap.getCustomMarkerIcon('pulsSensorCombMarker');
                    },
                    spiderfyOnMaxZoom: true,
                    showCoverageOnHover: false,
                    zoomToBoundsOnClick: false,
                    maxClusterRadius: 40,
                });

                // get all sensor devices
                this.cniDeviceService.getAll().subscribe(
                    (devices) => {
                    devices.forEach(device => {
                        // add marker
                        leafletMap.pinMarkerOnClusterGroup(
                        'psuMarker',
                        [device.latitude, device.longitude],
                        device.name,
                        '<b>' +
                            device.name +
                            '</b><br>' +
                            device.description +
                            "<br><a href='./devicebrowser/psu/" +
                            device.sensorBoxId +
                            "'>Open details</a>"
                        );
                    });
                    },
                    (error) => {}
                );
                break;
            }
            case 'ev': {
                // add custom marker icons
                leafletMap.produceCustomMarkerIcon(
                    'evMarker',
                    'assets/graphics/pulsEvStationMarker.png',
                    'assets/graphics/markerShadow.png',
                    [32, 37],
                    [39, 37],
                    [16, 36],
                    [10, 31],
                    [0, -37]
                );
            
                leafletMap.produceCustomMarkerIcon(
                    'parkingSpaceMarker',
                    'assets/graphics/pulsParkingSpaceMarker.png',
                    'assets/graphics/markerShadow.png',
                    [32, 37],
                    [39, 37],
                    [16, 36],
                    [10, 31],
                    [0, -37]
                );
            
                leafletMap.produceCustomMarkerIcon(
                    'pulsCombMarker',
                    'assets/graphics/pulsCombMarker.png',
                    'assets/graphics/markerShadow.png',
                    [24, 34],
                    [39, 37],
                    [12, 33],
                    [10, 31],
                    [0, -37]
                );
            
                leafletMap.initMarkerClusterGroup({
                    iconCreateFunction(cluster) {
                    return leafletMap.getCustomMarkerIcon('pulsCombMarker');
                    },
                    spiderfyOnMaxZoom: true,
                    showCoverageOnHover: false,
                    zoomToBoundsOnClick: false,
                    maxClusterRadius: 40,
                });

                // pin markers for all electrified parking spaces
                if (parkingOptionSelection === '-1' || parkingOptionSelection === '0') {
                    this.cniParkingSpacesService.getAllElectrified().subscribe(
                    (devices) => {
                        devices.forEach(device => {
                        // add marker
                        leafletMap.pinMarkerOnClusterGroup(
                            'evMarker',
                            [device.latitude, device.longitude],
                            device.name,
                            '<b>' +
                            device.name +
                            '</b><br>' +
                            device.description +
                            "<br><a href='./devicebrowser/ev/" +
                            device.id + "/electrified" +
                            "'>Open details</a>"
                        );
                        });
                    },
                    (error) => {}
                    );
                }
            
                // pin markers for all none electrified parking spaces
                if (parkingOptionSelection === '-1' || parkingOptionSelection === '1') {
                    this.cniParkingSpacesService.getAllNoneElectrified().subscribe(
                    (devices) => {
                        devices.forEach(device => {
                        // add marker
                        leafletMap.pinMarkerOnClusterGroup(
                            'parkingSpaceMarker',
                            [device.latitude, device.longitude],
                            device.name,
                            '<b>' +
                            device.name +
                            '</b><br>' +
                            device.description +
                            "<br><a href='./devicebrowser/ev/" +
                            device.id + "/none" +
                            "'>Open details</a>"
                        );
                        });
                    },
                    (error) => {}
                    );
                }
                break;
            }
        }
        
        return config;
    }

}