package com.company.controller;

import com.company.core.model.Medication;
import com.company.core.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Class {@code MedicationController} allows to handle requests:
 *
 * <li>Registering (updating) a medication;</li>
 * <li>Check loaded medication for a drone</li>
 * <p>
 *
 * @see com.company.core.model.Drone
 * @see Medication
 * @see com.company.core.service.DroneServiceImpl
 * @see com.company.core.service.MedicationServiceImpl
 */

@RestController
public class MedicationController {
    private static final Logger log = LoggerFactory.getLogger(MedicationController.class);

    @Autowired
    private MedicationService medicationService;

    @PostMapping(path = "/rest/v1/medication")
    @ResponseBody
    public ResponseEntity<String> medicationRegister(@RequestParam(name = "medication") String medicationString, @RequestParam(name = "image") MultipartFile image) {
        log.info("Registering a medication: {}", medicationString);
        try {
            if (medicationService.medicationRegister(medicationString, image)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            log.error("Medication is not valid");
            return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Bad request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/rest/v1/medication", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Medication> checkLoadedMedicationForDrone(@RequestParam("droneSerialNumber") String droneSerialNumber) {
        return medicationService.checkLoadedMedicationForDrone(droneSerialNumber);
    }
}