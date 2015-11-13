package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.util.BootstrapUtil;
import com.cm.cassandra.persistence.model.Keyspace;
import com.cm.cassandra.persistence.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Çelebi Murat on 12/11/15.
 */
public class KeyspaceProcessor {

    public static List<Keyspace> process(List<Class> klassList) {
        List<Keyspace> keyspaces = new ArrayList<>();

        for (Class klass : klassList) {
            Keyspace keyspace = new Keyspace();
            com.cm.bootstrap.annotations.Keyspace annotation = BootstrapUtil.findAnnotation(klass, com.cm.bootstrap.annotations.Keyspace.class);
            keyspace.setName(annotation.name());
            keyspace.setTables(TableProcessor.process(Arrays.asList(annotation.tables()), keyspace));

            keyspaces.add(keyspace);
        }

        return keyspaces;
    }

}
