package com.company.core.model;

import java.util.HashMap;
import java.util.Map;

public enum DroneState {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING");

    private final String abbreviation;

    private static final Map<String, DroneState> lookup = new HashMap<>();

    static {
        for (DroneState d : DroneState.values()) {
            lookup.put(d.getAbbreviation(), d);
        }
    }

    DroneState(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static DroneState get(String abbreviation) {
        return lookup.get(abbreviation);
    }
}