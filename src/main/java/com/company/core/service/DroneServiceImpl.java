package com.company.core.service;

import com.company.constants.Param;
import com.company.core.model.Drone;
import com.company.core.model.DroneModel;
import com.company.core.model.DroneState;
import com.company.core.query.DroneQuery;
import com.company.core.query.MedicationQuery;
import com.company.core.repository.DbRepository;
import com.company.exception.DataValueException;
import com.company.utils.UtilityServiceImpl;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DroneServiceImpl implements DroneService {
    private static final Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);
    @Autowired
    private DbRepository dbRepository;

    @Override
    public boolean droneRegister(String droneString) {
        Gson gson = new Gson();
        Drone drone = gson.fromJson(droneString, Drone.class);
        if (checkDroneValidity(drone) == false) {
            return false;
        }
        if (!ifDroneExists(drone.getSerialNumber())) {
            dbRepository.putDbData(String.format(DroneQuery.DRONE_UPDATE,
                    drone.getDroneModel(),
                    drone.getWeight(),
                    drone.getBatteryCapacity(),
                    drone.getDroneState(),
                    drone.getSerialNumber()));
        } else {
            dbRepository.putDbData(String.format(DroneQuery.DRONE_REGISTER,
                    drone.getSerialNumber(),
                    drone.getDroneModel(),
                    drone.getWeight(),
                    drone.getBatteryCapacity(),
                    drone.getDroneState()));
        }
        return true;
    }

    @Override
    public boolean loadMedications(String serialNumber, String id) {
        Double summary = dbRepository.getDbValue(String.format(DroneQuery.GET_DRONE_STATE_INFO, serialNumber));
        Double currentWeight = dbRepository.getDbValue(String.format(MedicationQuery.MEDICATION_WEIGHT_GET, id));
        List<Map<String, Object>> fromAllDb = dbRepository.getDbData(String.format(DroneQuery.DRONE_GET, serialNumber));
        List<Drone> droneList = fromAllDb.stream().map(this::createDto).toList();

        if (((droneList.get(0).getWeight() - summary) > currentWeight)
                && (droneList.get(0).getBatteryCapacity() > Param.BATTERY_CAPACITY_LIMIT)) {
            dbRepository.putDbData(String.format(DroneQuery.DRONE_LOAD_WITH_MEDICATION, serialNumber, id));
            log.info("Loaded new medication ({}) to a drone ({})", id, serialNumber);
            return true;
        } else {
            log.error("Couldn't load medication ({}) to a drone ({})", id, serialNumber);
            return false;
        }
    }

    @Override
    public List<Drone> checkForAvailableDrones() {
        List<Map<String, Object>> fromAllDb = dbRepository.getDbData(String.format(DroneQuery.GET_AVAILABLE_FOR_LOADING_DRONES, Param.BATTERY_CAPACITY_LIMIT));
        return fromAllDb.stream().map(this::createDto).toList();
    }

    @Override
    public Double checkDroneBattery(String serialNumber) {
        return dbRepository.getDbValue(String.format(DroneQuery.GET_CURRENT_BATTERY_STATE, serialNumber));
    }

    private boolean ifDroneExists(String serialNumber) {
        List<Map<String, Object>> fromAllDb = dbRepository.getDbData(String.format(DroneQuery.DRONE_GET, serialNumber));
        List<Drone> droneList = fromAllDb.stream().map(this::createDto).toList();

        return droneList.isEmpty();
    }

    Drone createDto(Map<String, Object> map) {
        Drone drone = new Drone();
        drone.setSerialNumber(UtilityServiceImpl.getStringFromObject(map.get(Param.SERIAL_NUMBER)));
        drone.setDroneModel(DroneModel.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.MODEL))));
        drone.setWeight(Double.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.WEIGHT))));
        drone.setBatteryCapacity(Double.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.BATTERY_CAPACITY))));
        drone.setDroneState(DroneState.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.STATE))));
        return drone;
    }

    private boolean checkDroneValidity(Drone droneSrc) {
        Drone drone = new Drone();
        try {
            drone.setSerialNumber(droneSrc.getSerialNumber());
            drone.setDroneModel(droneSrc.getDroneModel());
            drone.setWeight(droneSrc.getWeight());
            drone.setBatteryCapacity(droneSrc.getBatteryCapacity());
            drone.setDroneState(drone.getDroneState());
        } catch (DataValueException ex) {
            return false;
        }
        return true;
    }
}