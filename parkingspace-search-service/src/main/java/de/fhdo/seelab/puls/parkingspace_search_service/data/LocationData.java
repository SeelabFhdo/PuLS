/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.data;

import de.fhdo.seelab.puls.parkingspace_search_service.dtos.LocationDataDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Location data document structure for:
 * mongodb
 *
 * The collection name is geo_locations.
 * The database name is puls.
 * The geo coordinates are described as GeoJsonPoint in the
 * following data structure:
 * location: {
 *     type: "Point",
 *     coordinates: [ lng, lat ]
 * }
 *
 * (be aware that coordinates are in reverse order : longitude, latitude
 * as documented in https://docs.spring.io/spring-data/mongodb/docs/current
 * /api/org/springframework/data/mongodb/core/geo/GeoJson.html)
 *
 * The annotation GeoSpatialIndexed on the location field allows
 * mongodb to perform complex "geo-spacial" queries on the location data
 * (To enable this annotation "spring.data.mongodb.auto-index-creation" must be
 * activated in the spring service application properties)
 */
@Document(collection = "geo_locations")
public class LocationData {

    @Id
    private String id;

    private String refId;
    private String address;
    private String extFilter;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    public LocationData() {}

    public LocationData(
            String refId,
            String address,
            String extFilter,
            String lat,
            String lng) {
        this.refId = refId;
        this.address = address;
        this.extFilter = extFilter;

        this.location = new GeoJsonPoint(
                Double.valueOf(lng),
                Double.valueOf(lat)
        );
    }

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

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    /**
     * Feeds this object instance with data from
     * the given data transfer object representation
     * @param dto LocationDataDto
     */
    public void fromDto(LocationDataDto dto) {
        //this.setId(dto.getId());
        this.setRefId(dto.getRefId());
        this.setAddress(dto.getAddress());
        this.setExtFilter(dto.getExtFilter());

        this.setLocation(new GeoJsonPoint(
                Double.valueOf(dto.getLng()),
                Double.valueOf(dto.getLat())
        ));
    }

    /**
     * Converts the current LocationData object to
     * its data transfer object representation
     * @return LocationDataDto
     */
    public LocationDataDto toDto() {
        LocationDataDto dto = new LocationDataDto();
        dto.setId(this.id);
        dto.setRefId(this.refId);
        dto.setAddress(this.address);
        dto.setExtFilter(this.extFilter);

        dto.setLng(String.valueOf(this.location.getX()));
        dto.setLat(String.valueOf(this.location.getY()));

        return dto;
    }
}
