package com.cm.bootstrap.processors.persistance;

import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.exception.MultipleKeyspaceException;
import org.reflections.Reflections;

import java.util.Set;


/**
 * Created by Çelebi Murat on 05/11/15.
 */

public class JPAPersistanceProcessor {

    public static Keyspace processPersistanceAnnotations(String basePackage) throws MultipleKeyspaceException {
        Reflections reflections = getPackageScanner(basePackage);
        Set<Class<?>> keyspaceClasses = reflections.getTypesAnnotatedWith(com.cm.bootstrap.annotations.annotation.Keyspace.class);

        if(keyspaceClasses.size() > 1) {
            throw new MultipleKeyspaceException("Base package : " + basePackage +" containg more than one Keyspace class");
        }

        Class<?> klass = keyspaceClasses.iterator().next();
        Keyspace keyspace = KeyspaceProcessor.process(klass);

        return keyspace;
    }

    private static Reflections getPackageScanner(String basePackage) {
        return new Reflections(basePackage);
    }
}
