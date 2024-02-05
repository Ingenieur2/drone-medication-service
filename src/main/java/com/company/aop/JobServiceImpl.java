package com.company.aop;

import com.company.constants.Param;
import com.company.core.query.DroneQuery;
import com.company.core.repository.DbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * {@code JobServiceImpl} runs regularly and checks battery state of drones and
 * saves current information into database.
 *
 * @see com.company.core.model.Drone
 */

@Service
public class JobServiceImpl {
    @Autowired
    private DbRepository dbRepository;

    @Scheduled(fixedDelay = Param.TIME_PERIOD_OF_CHECKING_BATTERY_STATES_IN_MILLIS)
    void executeCheckingBatteries() {
        dbRepository.putDbData(DroneQuery.LOG_DRONE_BATTERY_STATE);
    }
}
