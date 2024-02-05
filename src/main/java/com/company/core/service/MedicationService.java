package com.company.core.service;

import com.company.core.model.Drone;
import com.company.core.model.Medication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * {@code MedicationService} allows to processing operations with medications:
 * <li>Creates, updates medications; </li>
 * <li></li>Loads list of medications for each drone</li>
 *
 * @see Drone
 * @see com.company.core.query.DroneQuery
 * @see com.company.controller.DroneController
 */
public interface MedicationService {
    /**
     * This method adds a new medication or updates the existing one.
     *
     * @param medication
     * @param image
     * @return whether {@code medication} was registered or not
     */
    boolean medicationRegister(String medication, MultipartFile image);

    /**
     * This method shows list of loaded medications for each drone.
     *
     * @param droneSerialNumber
     * @return List<Medication>
     */
    List<Medication> checkLoadedMedicationForDrone(String droneSerialNumber);
}