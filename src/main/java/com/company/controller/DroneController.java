package com.company.controller;

import com.company.core.model.Drone;
import com.company.core.service.DroneService;
import com.company.core.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class {@code DroneController} allows to handle requests:
 *
 * <li>Registering (updating) a drone;</li>
 * <li>Loading a drone with medication items;</li>
 * <li>Checking available drones for loading;</li>
 * <li>Check drone battery level for a given drone.</li>
 * <p>
 *
 * @see Drone
 * @see com.company.core.model.Medication
 * @see com.company.core.service.DroneServiceImpl
 * @see com.company.core.service.MedicationServiceImpl
 */

@RestController
public class DroneController {
    private static final Logger log = LoggerFactory.getLogger(DroneController.class);
    @Autowired
    private DroneService droneService;
    @Autowired
    private MedicationService medicationService;

    @PostMapping("/rest/v1/drone")
    public ResponseEntity<String> droneRegister(@RequestBody String droneString) {
        log.info("Registering a drone: {}", droneString);
        try {
            if (droneService.droneRegister(droneString)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            log.error("Drone is not valid");
            return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        } catch (Exception ex) {
            log.error("Bad request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rest/v1/drone/load")
    public ResponseEntity<String> loadDroneWithMedications(@RequestParam(name = "serialNumber") String serialNumber, @RequestParam(name = "id") String id) {
        if (droneService.loadMedications(serialNumber, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        }
    }


    @GetMapping("/rest/v1/drones/available")
    @ResponseBody
    public List<Drone> checkForAvailableDrones() {
        return droneService.checkForAvailableDrones();
    }

    @GetMapping("/rest/v1/drone/check/battery")
    public Double checkDroneBattery(@RequestParam("serialNumber") String droneSerialNumber) {
        return droneService.checkDroneBattery(droneSerialNumber);
    }
}