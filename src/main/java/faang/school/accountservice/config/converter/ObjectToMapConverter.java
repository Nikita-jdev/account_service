package faang.school.accountservice.config.converter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapConverter {
    public static Map<String,Object> convertObjectToMap(Object object){
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value= null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (value != null) {
                map.put(field.getName(), value);
            }
        }
        return map;
    }
}
