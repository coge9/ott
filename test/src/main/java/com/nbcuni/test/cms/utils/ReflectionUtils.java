package com.nbcuni.test.cms.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Ivan_Karnilau on 11-Apr-16.
 */
public class ReflectionUtils {

    public static <T> T getInstance(Class<T> clazz, Object... parameters) {
        try {
            Class[] parameterClasses = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterClasses[i] = parameters[i].getClass();
            }
            Constructor constructor = clazz.getConstructor(parameterClasses);
            try {
                return (T) constructor.newInstance(parameters);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unable to add new instance " + clazz.getName());
    }
}
