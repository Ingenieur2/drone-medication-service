package com.company.core.model;

import java.util.HashMap;
import java.util.Map;

public enum DroneModel {
    LIGHT_WEIGHT("Lightweight"),
    MIDDLE_WEIGHT("Middleweight"),
    CRUISER_WEIGHT("Cruiserweight"),
    HEAVY_WEIGHT("Heavyweight");
    private final String abbreviation;

    private static final Map<String, DroneModel> lookup = new HashMap<>();

    static {
        for (DroneModel d : DroneModel.values()) {
            lookup.put(d.getAbbreviation(), d);
        }
    }

    DroneModel(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static DroneModel get(String abbreviation) {
        return lookup.get(abbreviation);
    }
}