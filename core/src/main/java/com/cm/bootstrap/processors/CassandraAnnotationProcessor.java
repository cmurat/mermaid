package com.cm.bootstrap.processors;

import com.cm.cassandra.persistence.model.Keyspace;
import org.reflections.Reflections;

import javax.persistence.Table;
import java.util.Set;


/**
 * Created by Çelebi Murat on 05/11/15.
 */

public class CassandraAnnotationProcessor {

    public static Keyspace processPersistanceAnnotations(String basePackage) {
        Reflections reflections = getPackageScanner(basePackage);
        Set<Class<?>> tableClasses = reflections.getTypesAnnotatedWith(Table.class);

        return null;
    }

    private static Reflections getPackageScanner(String basePackage) {
        return new Reflections(basePackage);
    }
}
