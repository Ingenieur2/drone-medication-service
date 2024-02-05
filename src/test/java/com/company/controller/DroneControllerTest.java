package com.company.controller;

import com.company.core.model.Drone;
import com.company.core.model.DroneModel;
import com.company.core.model.DroneState;
import com.company.core.model.Medication;
import com.company.core.service.DroneService;
import com.company.core.service.MedicationService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DroneControllerTest {

    private MockMvc mockMvc;
    @Autowired
    DroneService droneService;
    @Autowired
    MedicationService medicationService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @DisplayName("Should register a new drone")
    @Transactional
    @Test
    void droneRegister() throws Exception {
        Drone drone = createDrone();
        Gson gson = new Gson();
        mockMvc.perform(post("/rest/v1/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(drone)))
                .andExpect(status().isOk());
    }

    @DisplayName("Should update a drone")
    @Transactional
    @Test
    void droneUpdate() throws Exception {
        Drone drone = createDrone();
        Gson gson = new Gson();
        droneService.droneRegister(gson.toJson(drone));

        drone.setWeight(100.0);
        drone.setBatteryCapacity(30.0);
        mockMvc.perform(post("/rest/v1/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(drone)))
                .andExpect(status().isOk());
    }

    @DisplayName("Should not update a drone with incorrect serialNumber length = 101. Should return Http error 416")
    @Transactional
    @Test
    void droneDoNotUpdate1() throws Exception {
        Drone drone = createDrone();

        Gson gson = new Gson();
        String str = gson.toJson(drone);
        droneService.droneRegister(str);

        String droneWithInvalidSerialNumber = str.replace("number1", "a1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        mockMvc.perform(post("/rest/v1/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(droneWithInvalidSerialNumber))
                .andExpect(status().is(416));
    }

    @DisplayName("Should not update a drone with incorrect weight > 500 gram. Should return Http error 416")
    @Transactional
    @Test
    void droneDoNotUpdate2() throws Exception {
        Drone drone = createDrone();

        Gson gson = new Gson();
        String str = gson.toJson(drone);
        droneService.droneRegister(str);
        String droneWithInvalidWeight = str.replace("400.0", "500.001");

        mockMvc.perform(post("/rest/v1/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(droneWithInvalidWeight))
                .andExpect(status().is(416));
    }

    @DisplayName("Should not update a drone with incorrect battery capacity > 100 %. Should return Http error 416")
    @Transactional
    @Test
    void droneDoNotUpdate3() throws Exception {
        Drone drone = createDrone();

        Gson gson = new Gson();
        String str = gson.toJson(drone);
        droneService.droneRegister(str);
        String droneWithInvalidBatteryCapacity = str.replace("95.4", "100.001");

        mockMvc.perform(post("/rest/v1/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(droneWithInvalidBatteryCapacity))
                .andExpect(status().is(416));
    }

    @DisplayName("Should return Http error 400 if body is not correct")
    @Transactional
    @Test
    void droneDoNotUpdate4() throws Exception {
        Drone drone = createDrone();

        Gson gson = new Gson();
        String str = gson.toJson(drone);
        droneService.droneRegister(str);
        String droneWithInvalidRequestBody = str.replace("95.4", "95,4");

        mockMvc.perform(post("/rest/v1/drone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(droneWithInvalidRequestBody))
                .andExpect(status().is(400));
    }

    @DisplayName("Should load medication")
    @Transactional
    @Test
    void loadMedications() throws Exception {
        Drone drone = createDrone();
        Medication medication = createMedication();
        Gson gson = new Gson();

        droneService.droneRegister(gson.toJson(drone));
        MultipartFile mockMultipartFile = new MockMultipartFile(medication.getName(), medication.getImage());
        medicationService.medicationRegister(gson.toJson(medication), mockMultipartFile);

        mockMvc.perform(post("/rest/v1/drone/load")
                        .param("serialNumber", drone.getSerialNumber())
                        .param("id", medication.getId().toString()))
                .andExpect(status().isOk());
    }

    @DisplayName("Should not load medications when drone is overloaded")
    @Transactional
    @Test
    void doNotLoadMedications1() throws Exception {
        Drone drone = createDrone();
        Medication medication = createMedication();
        medication.setWeight(drone.getWeight() + 0.001);
        Gson gson = new Gson();

        droneService.droneRegister(gson.toJson(drone));
        MultipartFile mockMultipartFile = new MockMultipartFile(medication.getName(), medication.getImage());
        medicationService.medicationRegister(gson.toJson(medication), mockMultipartFile);

        mockMvc.perform(post("/rest/v1/drone/load")
                        .param("serialNumber", drone.getSerialNumber())
                        .param("id", medication.getId().toString()))
                .andExpect(status().is(416));
    }

    @DisplayName("Should not load medications when drone has low battery level")
    @Transactional
    @Test
    void doNotLoadMedications2() throws Exception {
        Drone drone = createDrone();
        Medication medication = createMedication();
        drone.setBatteryCapacity(24.999);
        Gson gson = new Gson();

        droneService.droneRegister(gson.toJson(drone));
        MultipartFile mockMultipartFile = new MockMultipartFile(medication.getName(), medication.getImage());
        medicationService.medicationRegister(gson.toJson(medication), mockMultipartFile);

        mockMvc.perform(post("/rest/v1/drone/load")
                        .param("serialNumber", drone.getSerialNumber())
                        .param("id", medication.getId().toString()))
                .andExpect(status().is(416));
    }

    @DisplayName("Get the list of available drones")
    @Transactional
    @Test
    void checkForAvailableDrones() throws Exception {
        mockMvc.perform(get("/rest/v1/drones/available"))
                .andExpect(status().isOk());
    }

    @DisplayName("Should check a drone's battery")
    @Transactional
    @Test
    void droneCheckBattery() throws Exception {
        Drone drone = createDrone();

        Gson gson = new Gson();
        String str = gson.toJson(drone);
        droneService.droneRegister(str);

        mockMvc.perform(get("/rest/v1/drone/check/battery")
                        .param("serialNumber", drone.getSerialNumber()))
                .andExpect(status().isOk());
    }

    private Drone createDrone() {
        Drone drone = new Drone();
        drone.setSerialNumber("number1");
        drone.setDroneModel(DroneModel.HEAVY_WEIGHT);
        drone.setWeight(400.0);
        drone.setBatteryCapacity(95.4);
        drone.setDroneState(DroneState.DELIVERED);
        return drone;
    }

    private Medication createMedication() {
        Medication medication = new Medication();
        medication.setId(UUID.fromString("3422b448-2460-4fd2-9183-8000de6f7654"));
        medication.setName("Ibuprofen");
        medication.setWeight(50.0);
        medication.setCode("CODE_1");
        try {
            medication.setImage(Files.readAllBytes(Paths.get("src/test/resources/ibuprofen.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return medication;
    }
}