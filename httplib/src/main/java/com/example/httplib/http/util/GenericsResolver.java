package com.example.httplib.http.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Describe:解析泛型类型
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class GenericsResolver {

    /**
     * @return 解析泛型类型
     */
    public static Class<?> getObjectClass(Class getClass, Class dotClass) {
        Class<?> objectClass;
        Type objectType = getParameterizedType(getClass, dotClass, 0);
        if (objectType instanceof ParameterizedType) {
            objectClass = (Class<?>) ((ParameterizedType) objectType).getRawType();
        } else if (objectType instanceof TypeVariable) {
            throw new IllegalArgumentException("not support callback type " + objectType.toString());
        } else {
            objectClass = (Class<?>) objectType;
        }

        return objectClass;
    }


    private static Type getParameterizedType(

            final Type ownerType,
            final Class<?> declaredClass,
            int paramIndex) {

        Class<?> clazz;
        ParameterizedType pt;
        Type[] ats = null;
        TypeVariable<?>[] tps = null;
        if (ownerType instanceof ParameterizedType) {
            pt = (ParameterizedType) ownerType;
            clazz = (Class<?>) pt.getRawType();
            ats = pt.getActualTypeArguments();
            tps = clazz.getTypeParameters();
        } else {
            clazz = (Class<?>) ownerType;
        }
        if (declaredClass == clazz) {
            if (ats != null) {
                return ats[paramIndex];
            }
            return Object.class;
        }

        Type[] types = clazz.getGenericInterfaces();
        if (types != null) {
            for (Type t : types) {
                if (t instanceof ParameterizedType) {
                    Class<?> cls = (Class<?>) ((ParameterizedType) t).getRawType();
                    if (declaredClass.isAssignableFrom(cls)) {
                        try {
                            return getTrueType(getParameterizedType(t, declaredClass, paramIndex), tps, ats);
                        } catch (Throwable ignored) {
                        }
                    }
                }
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            if (declaredClass.isAssignableFrom(superClass)) {
                return getTrueType(
                        getParameterizedType(clazz.getGenericSuperclass(),
                                declaredClass, paramIndex), tps, ats);
            }
        }

        throw new IllegalArgumentException("FindGenericType:" + ownerType +
                ", declaredClass: " + declaredClass + ", index: " + paramIndex);

    }


    private static Type getTrueType(

            Type type,
            TypeVariable<?>[] typeVariables,
            Type[] actualTypes) {

        if (type instanceof TypeVariable<?>) {
            TypeVariable<?> tv = (TypeVariable<?>) type;
            String name = tv.getName();
            if (actualTypes != null) {
                for (int i = 0; i < typeVariables.length; i++) {
                    if (name.equals(typeVariables[i].getName())) {
                        return actualTypes[i];
                    }
                }
            }
            return tv;
        } else if (type instanceof GenericArrayType) {
            Type ct = ((GenericArrayType) type).getGenericComponentType();
            if (ct instanceof Class<?>) {
                return Array.newInstance((Class<?>) ct, 0).getClass();
            }
        }
        return type;
    }
}
