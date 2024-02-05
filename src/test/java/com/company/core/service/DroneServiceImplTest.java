//package com.company.core.service;
//
//import com.company.constants.Param;
//import com.company.core.model.Drone;
//import com.company.core.model.DroneModel;
//import com.company.core.model.DroneState;
//import com.company.core.query.DroneQuery;
//import com.company.core.repository.DbRepository;
//import com.company.utils.UtilityServiceImpl;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class DroneServiceImplTest {
//    private static final Logger log = LoggerFactory.getLogger(DroneServiceImplTest.class);
//
//    private DbRepository dbRepository;
//
//    @BeforeAll
//    public static void before() {
//        log.debug("Test com.company.core.service.DroneServiceImplTest.java is starting");
//    }
//
//    @DisplayName("Test registering a new drone")
//    @Test
//    public void droneRegister() {
//        Drone expectedDrone = new Drone();
//        expectedDrone.setSerialNumber("number1");
//        expectedDrone.setDroneModel(DroneModel.HEAVY_WEIGHT);
//        expectedDrone.setWeight(10.0);
//        expectedDrone.setBatteryCapacity(95.4);
//        expectedDrone.setDroneState(DroneState.DELIVERED);
//
////        dbRepository.jdbcTemplate1.putDbData(String.format(DroneQuery.DRONE_UPDATE,
////                expectedDrone.getDroneModel(),
////                expectedDrone.getWeight(),
////                expectedDrone.getBatteryCapacity(),
////                expectedDrone.getDroneState(),
////                expectedDrone.getSerialNumber()));
////
////
////        List<Map<String, Object>> fromAllDb = dbRepository.getDbData(String.format(DroneQuery.GET_AVAILABLE_FOR_LOADING_DRONES, Param.BATTERY_CAPACITY_LIMIT));
////        Drone drone = fromAllDb.stream().map(this::createDto).toList().get(0);
////        assertThat(drone).isNotNull();
////        assertThat(expectedDrone.getSerialNumber()).isEqualTo(drone.getSerialNumber());
////        assertThat(expectedDrone.getDroneModel()).isEqualTo(drone.getDroneModel());
////        assertThat(expectedDrone.getWeight()).isEqualTo(drone.getWeight());
////        assertThat(expectedDrone.getBatteryCapacity()).isEqualTo(drone.getBatteryCapacity());
////        assertThat(expectedDrone.getDroneState()).isEqualTo(drone.getDroneState());
////        dbRepository.deleteDbData(String.format("delete * from drone where serial_number = '%s'", expectedDrone.getSerialNumber()));
//    }
//
//
//    @AfterAll
//    public static void after() {
//        log.debug("Test com.company.core.service.DroneServiceImplTest.java finished");
//    }
//
//    private Drone createDto(Map<String, Object> map) {
//        Drone drone = new Drone();
//        drone.setSerialNumber(UtilityServiceImpl.getStringFromObject(map.get(Param.SERIAL_NUMBER)));
//        drone.setDroneModel(DroneModel.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.MODEL))));
//        drone.setWeight(Double.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.WEIGHT))));
//        drone.setBatteryCapacity(Double.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.BATTERY_CAPACITY))));
//        drone.setDroneState(DroneState.valueOf(UtilityServiceImpl.getStringFromObject(map.get(Param.STATE))));
//        return drone;
//    }
//}
