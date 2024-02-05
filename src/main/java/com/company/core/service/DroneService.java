package com.company.core.service;

import com.company.core.model.Drone;

import java.util.List;

/**
 * {@code DroneService} allows to processing operations with drones:
 * <li>Creates, updates drones; </li>
 * <li>Checks and modifies it's states.</li>
 *
 * @see Drone
 * @see com.company.core.query.DroneQuery
 * @see com.company.controller.DroneController
 */
public interface DroneService {

    /**
     * This method adds a new drone or updates the existing one.
     *
     * @param drone
     * @return whether {@code Drone} was registered or not
     */
    boolean droneRegister(String drone);

    /**
     * This method tries to load medication. If a drone has enough space inside and it's battery capacity
     * has enough charged then result or loading is {@code true}.
     *
     * @param serialNumber
     * @param id
     * @return whether {@code Medication} was loaded od drone or not
     */
    boolean loadMedications(String serialNumber, String id);

    /**
     * Available drone must have enough charge.
     *
     * @return list of available drones
     */
    List<Drone> checkForAvailableDrones();

    /**
     * This method checks drone's battery capacity
     *
     * @param droneSerialNumber
     * @return value of battery capacity
     */
    Double checkDroneBattery(String droneSerialNumber);
}