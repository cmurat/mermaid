package com.cm;

import com.cm.bootstrap.configuration.MermaidProperties;
import com.cm.bootstrap.processors.persistance.JPAPersistanceProcessor;
import com.cm.cassandra.persistence.CassandraPersistenceContext;
import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.exception.MermaidCoreException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Çelebi Murat on 09/11/15.
 */
public class Mermaid {
    private Map<String, CassandraPersistenceContext> contexts = new HashMap<>();

    public Mermaid() {

    }

    public CassandraPersistenceContext initializeContextWithProperties(MermaidProperties properties) throws MermaidCoreException {
        Keyspace keyspace = JPAPersistanceProcessor.processPersistanceAnnotations(properties.getProperty(MermaidProperties.BASE_PACKAGE));
        CassandraPersistenceContext context = new CassandraPersistenceContext(properties, keyspace);
        context.init();

        contexts.put(keyspace.getName(), context);

        return context;
    }
}
