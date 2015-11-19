package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.util.BootstrapUtil;
import com.cm.cassandra.persistence.model.element.Keyspace;

import java.util.Arrays;

/**
 * Created by Çelebi Murat on 12/11/15.
 */
public class KeyspaceProcessor {

    public static Keyspace process(Class<?> klass) {
        Keyspace keyspace = new Keyspace();
        com.cm.bootstrap.annotations.annotation.Keyspace annotation = BootstrapUtil.findAnnotation(klass, com.cm.bootstrap.annotations.annotation.Keyspace.class);
        keyspace.setName(annotation.name());
        keyspace.setTables(TableProcessor.process(Arrays.asList(annotation.tables()), keyspace));

        return keyspace;
    }

    private static void validate(com.cm.bootstrap.annotations.annotation.Keyspace annotation) {

    }
}
