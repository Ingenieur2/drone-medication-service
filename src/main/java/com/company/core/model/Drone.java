package com.company.core.model;

import com.company.exception.DataValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.company.constants.Param.*;

/**
 * Drone parameters:
 * <li>Serial number (100 characters max);</li>
 * <li>Model (Lightweight, Middleweight, Cruiserweight, Heavyweight);</li>
 * <li>Weight limit (500gr max);</li>
 * <li>Battery capacity (percentage);</li>
 * <li>State (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).</li>
 * <p>
 *
 * @see DroneModel
 * @see DroneState
 * @see com.company.core.service.DroneServiceImpl
 */

@Table
public class Drone {
    private static final Logger log = LoggerFactory.getLogger(Drone.class);
    @Id
    @Column("serial_number")
    private String serialNumber;
    @Column("model")
    private DroneModel droneModel;
    @Column("weight")
    private Double weight;
    @Column("battery_capacity")
    private Double batteryCapacity;
    @Column("state")
    private DroneState droneState;

    @Override
    public String toString() {
        return "Drone{" +
                "serialNumber='" + serialNumber + '\'' +
                ", droneModel=" + droneModel +
                ", weight=" + weight +
                ", batteryCapacity=" + batteryCapacity +
                ", droneState=" + droneState +
                '}';
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        if (serialNumber.length() > DRONE_SERIAL_NUMBER_LENGTH) {
            log.error("Serial number must be shorter than {} symbols. Current value is: {}",
                    DRONE_SERIAL_NUMBER_LENGTH, serialNumber.length());
            throw new DataValueException(new Exception());
        }
        this.serialNumber = serialNumber;
    }

    public DroneModel getDroneModel() {
        return droneModel;
    }

    public void setDroneModel(DroneModel droneModel) {
        this.droneModel = droneModel;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        if (weight > DRONE_WEIGHT_LIMIT) {
            log.error("Weight limit is {} gram. Current value is: {}", DRONE_WEIGHT_LIMIT, weight);
            throw new DataValueException(new Exception());
        }
        this.weight = weight;
    }

    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Double batteryCapacity) {
        if (batteryCapacity > DRONE_BATTERY_CAPACITY_LEVEL) {
            log.error("Battery capacity limit is {}%. Current value is: {}",
                    DRONE_BATTERY_CAPACITY_LEVEL, batteryCapacity);
            throw new DataValueException(new Exception());
        }
        this.batteryCapacity = batteryCapacity;
    }

    public DroneState getDroneState() {
        return droneState;
    }

    public void setDroneState(DroneState droneState) {
        this.droneState = droneState;
    }
}