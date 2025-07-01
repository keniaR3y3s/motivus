package com.revoktek.motivus.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class ApplicationUtil {

    private final ObjectMapper objectMapper;

    public ApplicationUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean isNull(Object object) {
        return object == null;
    }

    public boolean nonNull(Object object) {
        return !isNull(object);
    }

    public boolean nonEmpty(String value) {
        return nonNull(value) && !value.isEmpty() && !"null".equals(value);
    }

    public boolean isEmpty(String value) {
        return !nonEmpty(value);
    }


    public boolean nonEmptyList(List<?> list) {
        return nonNull(list) && !list.isEmpty();
    }

    public boolean isAlphabetic(String cadena) {
        Pattern pat = Pattern.compile("[A-Za-z]");
        return pat.matcher(cadena).find();
    }

    public boolean isAlphaNumeric(String cadena) {
        Pattern pat = Pattern.compile("[A-Za-z0-9]");
        return pat.matcher(cadena).find();
    }

    public boolean isEmailValid(String email) {
        Pattern pat = Pattern
                .compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9\u002D]+(\\.[A-Za-z0-9\u002D]+)*(\\.[A-Za-z]{2,})$");
        return pat.matcher(email).find();
    }

    public boolean isNumber(String numero) {
        final Pattern pat = Pattern.compile("^\\d+|\\d+(\\.\\d{1,2})?$");
        return pat.matcher(numero).find();
    }


    public boolean isEmptyList(List<?> list) {
        return !nonEmptyList(list);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }


    public Method findSetterMethod(Object entityClass, String fieldName) {
        String setterMethodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        String isMethodName = "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        for (Method method : entityClass.getClass().getMethods()) {
            if ((method.getName().equals(setterMethodName) || method.getName().equals(isMethodName)) && method.getParameterCount() == 1) {
                return method;
            }
        }
        return null;

    }

    public void setDefaultValue(Object entityClass, String fieldName, Object... args) throws InvocationTargetException, IllegalAccessException {
        Method setEnabledMethod = findSetterMethod(entityClass, fieldName);
        if (setEnabledMethod != null) {
            setEnabledMethod.invoke(entityClass, args);
        }
    }

    public String getError(Exception e) {
        return e.getStackTrace()[0].getMethodName();
    }


    public String formatDate(Date date, int type) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public String formatMoney(BigDecimal bd) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX")); // Formato en pesos mexicanos
        BigDecimal bdMoneda = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
       return formatoMoneda.format(bdMoneda);
    }
    public String formatBigDecimal(BigDecimal bd) {
        return bd.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    public String toJson(Object object) {
        try {
            if(isNull(object)) {
                return objectMapper.writeValueAsString(new HashMap<String, Object>() {
                });
            }
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir objeto a JSON", e);
        }
    }

    private static final Set<String> SENSITIVE_FIELDS  = Set.of(
            "password", "pass", "contrasena", "contraseña"
    );


    public String sanitizeBody(String requestBody) {
        try {
            Map<String, Object> originalJson = objectMapper.readValue(requestBody, Map.class);
            Object sanitizedJson = sanitize(originalJson);
            return  objectMapper.writeValueAsString(sanitizedJson);
        } catch (JsonProcessingException e) {
            return "Error al convertir objeto a JSON: "+ e.getMessage();
        }

    }
    public  Object sanitize(Object input) {
        String MASKED_VALUE = "[PROTECTED]";
        if (input instanceof Map<?, ?> map) {
            Map<String, Object> sanitized = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String key = entry.getKey().toString();
                Object value = entry.getValue();

                if (SENSITIVE_FIELDS.contains(key.toLowerCase())) {
                    sanitized.put(key, MASKED_VALUE);
                } else {
                    sanitized.put(key, sanitize(value));
                }
            }
            return sanitized;
        } else if (input instanceof List<?> list) {
            List<Object> sanitizedList = new ArrayList<>();
            for (Object item : list) {
                sanitizedList.add(sanitize(item));
            }
            return sanitizedList;
        } else {
            return input;
        }
    }
}
