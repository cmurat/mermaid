package com.cm.bootstrap.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Çelebi Murat on 12/11/15.
 */
public class BootstrapUtil {

    public static String resolveName(String elementName) {
        String componentName = "";
        for (char c : elementName.toCharArray()) {
            if(Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
                componentName += "_";
            }
            componentName += c;
        }
        return componentName;
    }

    public static <A extends Annotation> A findAnnotation(Class type, Class<A> annotationType) {

        return null;
    }

    public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {

        return null;
    }

    public static <A extends Annotation> A findAnnotation(Field field, Class<A> annotationType) {

        return null;
    }

}
