package com.company.constants;

public interface Param {
    String SERIAL_NUMBER = "serial_number";
    String MODEL = "model";
    String WEIGHT = "weight";
    String BATTERY_CAPACITY = "battery_capacity";
    String STATE = "state";
    String ID = "id";
    String NAME = "name";
    String CODE = "code";
    String IMAGE = "image";

    double BATTERY_CAPACITY_LIMIT = 25.0;
    int TIME_PERIOD_OF_CHECKING_BATTERY_STATES_IN_MILLIS = 60_000;
    double DRONE_WEIGHT_LIMIT = 500.0;
    int DRONE_SERIAL_NUMBER_LENGTH = 100;
    double DRONE_BATTERY_CAPACITY_LEVEL = 100.0;
}