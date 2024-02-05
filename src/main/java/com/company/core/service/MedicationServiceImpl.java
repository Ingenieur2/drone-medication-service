package com.company.core.service;

import com.company.constants.Param;
import com.company.core.model.Medication;
import com.company.core.query.MedicationQuery;
import com.company.core.repository.DbRepository;
import com.company.exception.DataValueException;
import com.company.utils.UtilityServiceImpl;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MedicationServiceImpl implements MedicationService {
    private static final Logger log = LoggerFactory.getLogger(MedicationServiceImpl.class);

    @Autowired
    private DbRepository dbRepository;

    @Override
    public boolean medicationRegister(String medicationString, MultipartFile image) {
        Gson gson = new Gson();
        Medication medication = gson.fromJson(medicationString, Medication.class);
        try {
            medication.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (checkMedicationValidity((medication)) == false) {
            return false;
        }

        /**
         *  Could be used this implementation of UUID:
         *  medication.setId(UUID.fromString(ZonedDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))));
         */

        if (!ifMedicationExists(medication.getId())) {
            dbRepository.putDbData(String.format(MedicationQuery.MEDICATION_UPDATE,
                    medication.getName(),
                    medication.getCode(),
                    Arrays.toString(medication.getImage()),
                    medication.getId()));
        } else {
            dbRepository.putDbData(String.format(MedicationQuery.MEDICATION_REGISTER,
                    medication.getId(),
                    medication.getName(),
                    medication.getWeight(),
                    medication.getCode(),
                    Arrays.toString(medication.getImage())));
        }
        return true;
    }

    @Override
    public List<Medication> checkLoadedMedicationForDrone(String serialNumber) {
        List<Map<String, Object>> fromAllDb = dbRepository.getDbData(String.format(MedicationQuery.GET_LOADED_MEDICATIONS_FOR_DRONE, serialNumber));
        return fromAllDb.stream().map(this::createDto).toList();
    }

    private boolean ifMedicationExists(UUID id) {
        List<Map<String, Object>> fromAllDb = dbRepository.getDbData(String.format(MedicationQuery.MEDICATION_GET, id));
        List<Medication> medicationList = fromAllDb.stream().map(this::createDto).toList();

        return medicationList.isEmpty();
    }

    Medication createDto(Map<String, Object> map) {
        Medication medication = new Medication();
        medication.setId(UUID.fromString(UtilityServiceImpl.getStringFromObject(map.get(Param.ID))));
        medication.setName(UtilityServiceImpl.getStringFromObject(map.get(Param.NAME)));
        medication.setWeight(Double.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.WEIGHT))));
        medication.setCode(UtilityServiceImpl.getStringFromObject(map.get(Param.CODE)));
        medication.setImage(UtilityServiceImpl.getStringFromObject(map.get(Param.IMAGE)).getBytes());
        return medication;
    }

    private boolean checkMedicationValidity(Medication medicationSrc) {
        Medication medication = new Medication();
        try {
            medication.setId(medicationSrc.getId());
            medication.setName(medicationSrc.getName());
            medication.setWeight(medicationSrc.getWeight());
            medication.setCode(medicationSrc.getCode());
            medication.setImage(medicationSrc.getImage());
        } catch (DataValueException ex) {
            return false;
        }
        return true;
    }
}