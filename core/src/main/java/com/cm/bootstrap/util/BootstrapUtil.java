package com.cm.bootstrap.util;

import javafx.scene.effect.Reflection;
import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Çelebi Murat on 12/11/15.
 */
public class BootstrapUtil {

    public static String resolveName(String elementName) {
        String componentName = "";
        for (char c : elementName.toCharArray()) {
            if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
                if (componentName.length() > 0) {
                    componentName += "_";
                }
            }
            componentName += c;
        }
        return componentName;
    }

    public static <A extends Annotation> A findAnnotation(AnnotatedElement annotatedElement, Class<A> annotationType) {
        Annotation annotation = annotatedElement.getAnnotation(annotationType);
        if (annotation != null) {
            return (A) annotation;
        }
        return null;
    }
}
