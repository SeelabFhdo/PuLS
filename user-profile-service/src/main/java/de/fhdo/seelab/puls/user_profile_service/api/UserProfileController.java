/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     User Profile Service
 * Author:          Alexander Stein
 * Dev. Date:       December 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.user_profile_service.api;

import de.fhdo.seelab.puls.user_profile_service.services.UserFavoriteLocationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@CrossOrigin(origins = "${api.cors.origins}", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping(value = "/resources/${api.version}/userprofileinterface", produces = "application/json")
public class UserProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    private UserFavoriteLocationService locationDataService;

    public UserProfileController() {}

    @PostConstruct
    private void init() { }

    @Autowired
    public void setLocationDataService(UserFavoriteLocationService locationDataService) {
        this.locationDataService = locationDataService;
    }

    //TODO: def requests.
}
