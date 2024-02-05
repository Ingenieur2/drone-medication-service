package com.company.core.query;

public interface DroneQuery {
    String DRONE_REGISTER = "insert into drone (serial_number, model, weight, battery_capacity, state)\n" +
            "values ('%s', '%s', %s, %s, '%s');";
    String DRONE_UPDATE = """
            update drone
            set
            \tmodel = '%s',
            \tweight = %s,
            \tbattery_capacity = %s,
            \tstate = '%s'
            where
            \tserial_number = '%s';""";
    String DRONE_GET = "select * from drone\n" +
            "where serial_number = '%s';";
    String DRONE_LOAD_WITH_MEDICATION = "insert into current_loading_information (serial_number, id)" +
            "values ('%s', '%s');";
    String GET_DRONE_STATE_INFO = "select\n" +
            "\tsum(md.weight)\n" +
            "from drone dr\n" +
            "\tleft join current_loading_information cli on (dr.serial_number = cli.serial_number)\n" +
            "\tleft join medication md on (cli.id = md.id)\n" +
            "where dr.serial_number = '%s';";
    String GET_AVAILABLE_FOR_LOADING_DRONES = "select * from drone\n" +
            "where battery_capacity > %s;";
    String GET_CURRENT_BATTERY_STATE = "select battery_capacity from drone\n" +
            "where serial_number = '%s';";

    String LOG_DRONE_BATTERY_STATE = "insert into drone_state_history (serial_number, battery_capacity, create_dt)\n" +
            "select \n" +
            "\tserial_number,\n" +
            "\tbattery_capacity,\n" +
            "\tnow()\n" +
            "from drone;";
}