package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.util.BootstrapUtil;
import com.cm.bootstrap.util.Queries;
import com.cm.cassandra.persistence.model.Column;
import com.cm.cassandra.persistence.types.CqlType;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import javax.persistence.CollectionTable;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Çelebi Murat on 05/11/15.
 */
public class ColumnProcessor {

    public static List<Column> process(Class type) {
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(Transient.class)) {
                continue;
            }

            Column column = new Column();

            if(field.isAnnotationPresent(javax.persistence.Column.class)) {
                javax.persistence.Column jxColumn = BootstrapUtil.findAnnotation(field, javax.persistence.Column.class);
            }
        }

        return null;
    }

    private static Column processColumn(Column column, javax.persistence.Column jxColumn, Field field) {
        String fieldName = field.getType().getSimpleName();
        String columnName;
        if(!jxColumn.name().equals("")) {
            columnName = jxColumn.name();
        } else {
            columnName = BootstrapUtil.resolveName(fieldName);
        }


        column.setName(columnName);
        column.setDefinitionString(Queries.getColumnDefinition(columnName, jxColumn, field));

        return null;
    }
}
