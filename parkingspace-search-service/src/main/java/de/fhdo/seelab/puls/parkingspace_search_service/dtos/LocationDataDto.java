/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.dtos;

/**
 * The data transfer object definition for:
 * LocationData
 */
public class LocationDataDto {

    private String id;
    private String refId;
    private String address;
    private String extFilter;
    private String lat;
    private String lng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExtFilter() {
        return extFilter;
    }

    public void setExtFilter(String extFilter) {
        this.extFilter = extFilter;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
