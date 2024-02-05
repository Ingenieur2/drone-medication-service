package com.company.utils;

import org.slf4j.Logger;


public class UtilityServiceImpl {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UtilityServiceImpl.class);

    public static String getStringFromObject(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }
}