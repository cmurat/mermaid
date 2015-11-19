package com.cm.bootstrap.util;

import com.cm.cassandra.persistence.model.element.Column;
import com.cm.cassandra.persistence.model.element.Table;
import com.cm.cassandra.persistence.types.ColumnData;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Çelebi Murat on 13/11/15.
 */
public class Queries {
    private static final String CREATE_TABLE_IF_NOT_EXISTS = " CREATE TABLE IF NOT EXISTS ";
    private static final String CREATE_KEYSPACE = " CREATE KEYSPACE ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String STATIC = " STATIC ";

    private static final String COLUMN_DEF_SEPARATOR = ",";
    private static final String BLOCK_START = "(";
    private static final String BLOCK_END = ")";

    private static final String WITH = " WITH ";
    private static final String REPLICATION = " REPLICATION ";
    private static final String CLUSTRING_ORDER_BY = " CLUSTRING ORDER BY ";
    private static final String COMPACT_STORAGE = " COMPACT STORAGE ";

    public static String getTableDefinition(Table table) {
        String definition = CREATE_TABLE_IF_NOT_EXISTS + table.getKeyspace().getName() + "." + table.getName();
        String primaryKeyDefinition = PRIMARY_KEY + BLOCK_START;
        definition += BLOCK_START;
        List<Column> columns = table.getColumns();

        for (Column column : columns) {
            definition = StringUtils.join(definition, column.getDefinitionString(), COLUMN_DEF_SEPARATOR);

            if (column.isPrimaryKey()) {
                primaryKeyDefinition += column.getName();
            }
        }
        primaryKeyDefinition += BLOCK_END;
        definition += primaryKeyDefinition + BLOCK_END;

        return definition;
    }

    public static String getColumnDefinition(String columnName, javax.persistence.Column jxColumn, Field field) {
        String definitionString = "";
        if(StringUtils.isNotEmpty(jxColumn.columnDefinition())) {
            definitionString = columnName.concat(" ").concat(jxColumn.columnDefinition());
            if(definitionString.endsWith(".") || definitionString.endsWith(";") || definitionString.endsWith(";")) {
                definitionString.substring(0, definitionString.length() - 1);
            }
        } else {
            ColumnData data = new ColumnData().initWithFieldGenericType(field.getGenericType());

            definitionString = definitionString.concat(columnName).concat(" ").concat(data.asCql());
        }

        return definitionString;
    }
}
