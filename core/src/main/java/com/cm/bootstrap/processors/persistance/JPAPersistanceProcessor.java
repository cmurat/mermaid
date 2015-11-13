package com.cm.bootstrap.processors.persistance;

import com.cm.cassandra.persistence.model.Column;
import com.cm.cassandra.persistence.model.Keyspace;
import org.reflections.Reflections;

import javax.inject.Inject;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Set;


/**
 * Created by Çelebi Murat on 05/11/15.
 */

public class JPAPersistanceProcessor {

    public static Keyspace processPersistanceAnnotations(String basePackage) {
        Reflections reflections = getPackageScanner(basePackage);
        Set<Class<?>> tableClasses = reflections.getTypesAnnotatedWith(com.cm.bootstrap.annotations.Keyspace.class);

        return null;
    }

    private static Reflections getPackageScanner(String basePackage) {
        return new Reflections(basePackage);
    }
}
