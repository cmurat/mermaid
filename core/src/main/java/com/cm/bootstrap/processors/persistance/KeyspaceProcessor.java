package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.configuration.MermaidProperties;
import com.cm.bootstrap.util.BootstrapUtil;
import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.exception.MermaidCoreException;
import com.cm.exception.MultipleKeyspaceException;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Çelebi Murat on 12/11/15.
 */
public class KeyspaceProcessor implements AnnotationProcessor {

    @Override
    public void process(AnnotationProcessorManager processorManager) throws MermaidCoreException {
        String basePackage = processorManager.getContext().properties.getProperty(MermaidProperties.BASE_PACKAGE);
        Reflections reflections = processorManager.getPackageScanner(basePackage);
        Set<Class<?>> keyspaceClasses = reflections.getTypesAnnotatedWith(com.cm.bootstrap.annotations.annotation.Keyspace.class);

        if (keyspaceClasses.size() > 1) {
            throw new MultipleKeyspaceException();
        }

        Class klass = keyspaceClasses.iterator().next();
        Keyspace keyspace = new Keyspace();
        com.cm.bootstrap.annotations.annotation.Keyspace annotation = BootstrapUtil.findAnnotation(klass, com.cm.bootstrap.annotations.annotation.Keyspace.class);
        validate(annotation);
        keyspace.setName(annotation.name());
        keyspace.getEntities().addAll(Arrays.asList(annotation.entities()));

        processorManager.setKeyspace(keyspace);

    }

    private void validate(com.cm.bootstrap.annotations.annotation.Keyspace annotation) {
        //TODO Validate strategies
    }
}
