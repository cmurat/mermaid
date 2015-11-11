package com.cm;

import com.cm.bootstrap.configuration.MermaidProperties;
import com.cm.bootstrap.processors.CassandraAnnotationProcessor;
import com.cm.cassandra.persistence.CassandraPersistenceContext;
import com.cm.cassandra.persistence.model.Keyspace;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Çelebi Murat on 09/11/15.
 */
public class Mermaid {
    private Map<String, CassandraPersistenceContext> contexts = new HashMap<>();

    public Mermaid() {

    }

    public CassandraPersistenceContext initializeContextWithProperties(MermaidProperties properties) {
        Keyspace keyspace = CassandraAnnotationProcessor.processPersistanceAnnotations(properties.getProperty(MermaidProperties.BASE_PACKAGE));
        CassandraPersistenceContext context = new CassandraPersistenceContext(properties, keyspace);
        context.init();

        return context;
    }
}
