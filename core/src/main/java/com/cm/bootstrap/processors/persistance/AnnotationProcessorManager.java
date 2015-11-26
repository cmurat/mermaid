package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.configuration.MermaidProperties;
import com.cm.cassandra.persistence.CassandraPersistenceContext;
import com.cm.cassandra.persistence.model.element.Column;
import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.cassandra.persistence.model.element.Table;
import com.cm.exception.MermaidCoreException;
import com.cm.exception.MultipleKeyspaceException;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by Çelebi Murat on 05/11/15.
 */


public class AnnotationProcessorManager {

    private Keyspace keyspace;

    private List<AnnotationProcessor> processors = new ArrayList<>();

    private CassandraPersistenceContext context;

    public AnnotationProcessorManager(CassandraPersistenceContext context) {
        this.context = context;
    }

    public void process() throws MermaidCoreException {
        for (AnnotationProcessor processor : processors) {
            processor.process(this);
        }
    }

    public Reflections getPackageScanner(String basePackage) {
        return new Reflections(basePackage);
    }

    public void registerProcessor(AnnotationProcessor processor) {
        processors.add(processor);
    }

    public void removeProcessor(AnnotationProcessor processor) {
        processors.remove(processor);
    }

    public CassandraPersistenceContext getContext() {
        return context;
    }

    public Keyspace getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(Keyspace keyspace) {
        this.keyspace = keyspace;
    }
}
