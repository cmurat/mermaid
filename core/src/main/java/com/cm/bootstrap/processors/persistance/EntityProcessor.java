package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.annotations.annotation.PrimaryKey;
import com.cm.bootstrap.util.BootstrapUtil;
import com.cm.bootstrap.util.Queries;
import com.cm.cassandra.persistence.model.element.Column;
import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.cassandra.persistence.model.element.Table;
import org.apache.commons.lang3.StringUtils;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Çelebi Murat on 05/11/15.
 */
public class EntityProcessor implements AnnotationProcessor {

    @Override
    public void process(AnnotationProcessorManager processorManager) {
        processEntities(processorManager.getKeyspace());
    }

    private void processEntities(Keyspace keyspace) {
        List<Table> tables = new ArrayList<>();

        for (Class klass : keyspace.getEntities()) {
            Annotation annotation = klass.getAnnotation(javax.persistence.Table.class);
            if (annotation instanceof javax.persistence.Table) {
                javax.persistence.Table jxTable = (javax.persistence.Table) annotation;

                Table table = new Table();
                table.setName(StringUtils.isNotEmpty(jxTable.name()) ? jxTable.name() : BootstrapUtil.resolveName(klass.getSimpleName()));
                table.setColumns(processEntities(klass, table));
                table.setKeyspace(keyspace);
                table.setMappignObject(klass);
                table.setDefinitionString(Queries.getTableDefinition(table));

                tables.add(table);
            }
        }

        keyspace.getTables().addAll(tables);
    }

    private List<Column> processEntities(Class klass, Table table) {
        Field[] fields = klass.getDeclaredFields();

        List<Column> columns = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }

            javax.persistence.Column jxColumn = null;
            if (field.isAnnotationPresent(javax.persistence.Column.class)) {
                jxColumn = BootstrapUtil.findAnnotation(field, javax.persistence.Column.class);
            }

            Column column = processColumnWithAnnotation(jxColumn, field);
            column.setTable(table);

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                column.setPrimaryKey(true);
            }

            columns.add(column);
        }
        return columns;
    }

    private Column processColumnWithAnnotation(javax.persistence.Column jxColumn, Field field) {
        Column column = new Column();
        String fieldName = field.getName();
        String columnName;
        if (jxColumn != null && StringUtils.isNotEmpty(jxColumn.name())) {
            columnName = jxColumn.name();
        } else {
            columnName = BootstrapUtil.resolveName(fieldName);
        }
        column.setMappingObject(field.getType());
        column.setName(columnName);
        column.setDefinitionString(Queries.getColumnDefinition(columnName, jxColumn, field));

        return column;
    }

}
