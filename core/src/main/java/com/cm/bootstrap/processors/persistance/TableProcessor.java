package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.util.BootstrapUtil;
import com.cm.bootstrap.util.Queries;
import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.cassandra.persistence.model.element.Table;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Çelebi Murat on 05/11/15.
 */
public class TableProcessor {

    public static List<Table> process(List<Class> types, Keyspace keyspace) {
        List<Table> tables = new ArrayList<>();

        for (Class type : types) {
            Annotation annotation = type.getAnnotation(javax.persistence.Table.class);
            if (annotation instanceof javax.persistence.Table) {
                javax.persistence.Table jxTable = (javax.persistence.Table) annotation;

                Table table = new Table();
                table.setName(StringUtils.isNotEmpty(jxTable.name()) ? jxTable.name() : BootstrapUtil.resolveName(type.getSimpleName()));
                table.setColumns(ColumnProcessor.process(type, table));
                table.setKeyspace(keyspace);
                table.setMappignObject(type);
                table.setDefinitionString(Queries.getTableDefinition(table));

                tables.add(table);
            }
        }

        return tables;
    }
}
