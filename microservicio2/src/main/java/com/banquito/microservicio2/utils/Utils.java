package com.banquito.microservicio2.utils;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
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

    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int randomDigit = random.nextInt(10);
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public static String formatUnixTimeToDate(long unixTime) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return dateTime.format(formatter);
    }

    public static Long getCurrentUnixTime() {
        return Instant.now().getEpochSecond();
    }

    public static boolean isValidTipoMovimiento(String value) {
        for (TipoMovimiento tipo : TipoMovimiento.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidUnixTimeRange(long start, long end) {
        if (start < 0 || end < 0 || end < start) {
            return false;
        }
        return true;
    }

    public enum TipoMovimiento {
        DEPOSITO,
        RETIRO
    }
}
