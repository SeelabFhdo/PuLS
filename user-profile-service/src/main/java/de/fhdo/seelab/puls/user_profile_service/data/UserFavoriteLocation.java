/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     User Profile Service
 * Author:          Alexander Stein
 * Dev. Date:       December 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.user_profile_service.data;

import de.fhdo.seelab.puls.user_profile_service.dtos.UserFavoriteLocationDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_favorite_locations")
public class UserFavoriteLocation {

    @Id
    private String id;

    private String refId;

    public UserFavoriteLocation() {}

    public UserFavoriteLocation(
            String refId) {
        this.refId = refId;
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

    public void fromDto(UserFavoriteLocationDto dto) {
        //this.setId(dto.getId());
        this.setRefId(dto.getRefId());
    }

    public UserFavoriteLocationDto toDto() {
        UserFavoriteLocationDto dto = new UserFavoriteLocationDto();
        dto.setId(this.id);
        dto.setRefId(this.refId);

        return dto;
    }
}
