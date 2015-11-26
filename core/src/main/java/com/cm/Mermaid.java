package com.cm;

import com.cm.bootstrap.configuration.MermaidProperties;
import com.cm.bootstrap.processors.persistance.AnnotationProcessorManager;
import com.cm.cassandra.persistence.CassandraPersistenceContext;
import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.exception.MermaidCoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Çelebi Murat on 09/11/15.
 */
public class Mermaid {

    private List<CassandraPersistenceContext> contexts = new ArrayList<>();

    public CassandraPersistenceContext initializeContextWithProperties(MermaidProperties properties) throws MermaidCoreException {
        CassandraPersistenceContext context = new CassandraPersistenceContext(properties);
        context.init();
        contexts.add(context);

        return context;
    }
}
