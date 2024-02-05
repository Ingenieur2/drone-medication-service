package com.company.core.query;

public interface MedicationQuery {
    String MEDICATION_REGISTER = "insert into medication (id, name, weight, code, image)\n" +
            "values ('%s', '%s', %s, '%s', '%s');";
    /**
     * Weight shouldn't be updated. Weight value from http request is ignored
     */
    String MEDICATION_UPDATE = """
            update medication
            set
            \tname = '%s',
            \tcode = '%s',
            \timage = '%s'
            where
            \tid = '%s';""";
    String MEDICATION_GET = "select * from medication\n" +
            "where id = '%s';";
    String MEDICATION_WEIGHT_GET = "select weight from medication\n" +
            "where id = '%s';";
    String GET_LOADED_MEDICATIONS_FOR_DRONE = "select \n" +
            "\tmd.id,\n" +
            "\tmd.name,\n" +
            "\tmd.weight,\n" +
            "\tmd.code,\n" +
            "\tmd.image\n" +
            "from drone dr\n" +
            "left join current_loading_information cli on (dr.serial_number = cli.serial_number)\n" +
            "left join medication md on (cli.id = md.id)\n" +
            "where dr.serial_number = '%s';";
}