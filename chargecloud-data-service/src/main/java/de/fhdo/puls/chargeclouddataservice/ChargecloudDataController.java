package de.fhdo.puls.chargeclouddataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources/v01")
public class ChargecloudDataController {

    private static final Logger INFO_LOGGER = LoggerFactory.getLogger(ChargecloudDataController.class);

    @PostMapping("/chargecloud/data")
    public String pushData(@RequestBody String data) {
        INFO_LOGGER.info(data);
        return data;
    }
}
