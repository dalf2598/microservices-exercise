package com.banquito.microservicio1.utils;

import java.lang.reflect.Field;
import java.util.UUID;

public class Utils {
    public static boolean isNullOrEmpty(Object value){
        return (value == null || value.toString().isEmpty());
    }

    public static boolean isValidUUID(String uuidString) {
        if (uuidString == null || uuidString.isEmpty()) {
            return false;
        }

        try {
            UUID uuid = UUID.fromString(uuidString);
            return uuid.toString().equals(uuidString);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean hasAllAttributes(Object object){
        boolean result = true;
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if(value == null){
                    result = false;
                }
                else {
                    if(value.toString().isEmpty()){
                        result = false;
                    }
                }
            } catch (IllegalAccessException | NullPointerException e) {
                result = false;
            }
        }
        return result;
    }
}
