package com.macbeth.util;

import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class ReflectUtils {
    /**
     * 获取指定对象的
     * @return
     */
    public static Map<String,Object> getBeanDeclaredFields(Object target) throws IllegalAccessException {
        boolean flag = true;
        Class<?> temp = target.getClass();
        Map<String,Object> result = Maps.newHashMap();
        while (flag) {
            Field[] declaredFields = temp.getDeclaredFields();
            final Class<?> superclass = temp.getSuperclass();
            if (superclass == Object.class) {
                flag = false;
            }
            String fieldName = null;
            for (int i = declaredFields.length - 1; i >= 0; i--) {
                Field field = declaredFields[i];
                if (result.containsKey(fieldName = field.getName())) {
                    field = null;
                    continue;
                }
                field.setAccessible(true);
                Object value = null;
                if (Objects.nonNull(value = field.get(target))) {
                    result.put(fieldName, value);
                }
            }
            declaredFields = null;
            temp = temp.getSuperclass();
        }

        return result;
    }
}
