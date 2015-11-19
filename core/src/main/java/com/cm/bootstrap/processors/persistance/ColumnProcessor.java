package com.cm.bootstrap.processors.persistance;

import com.cm.bootstrap.annotations.annotation.PrimaryKey;
import com.cm.bootstrap.util.BootstrapUtil;
import com.cm.bootstrap.util.Queries;
import com.cm.cassandra.persistence.model.element.Column;
import com.cm.cassandra.persistence.model.element.Table;
import org.apache.commons.lang3.StringUtils;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Çelebi Murat on 05/11/15.
 */
public class ColumnProcessor {

    public static List<Column> process(Class type, Table table) {
        Field[] fields = type.getDeclaredFields();

        List<Column> columns = new ArrayList<>();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Transient.class)) {
                continue;
            }

            if(field.isAnnotationPresent(javax.persistence.Column.class)) {
                javax.persistence.Column jxColumn = BootstrapUtil.findAnnotation(field, javax.persistence.Column.class);
                Column column = processColumnWithAnnotation(jxColumn, field);
                column.setTable(table);

                if(field.isAnnotationPresent(PrimaryKey.class)) {
                    column.setPrimaryKey(true);
                }

                columns.add(column);
            }
        }

        return columns;
    }

    private static Column processColumnWithAnnotation(javax.persistence.Column jxColumn, Field field) {
        Column column = new Column();
        String fieldName = field.getName();
        String columnName;
        if(StringUtils.isNotEmpty(jxColumn.name())) {
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
