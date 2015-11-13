package com.cm.bootstrap.util;

import com.cm.cassandra.persistence.model.Column;
import com.cm.cassandra.persistence.model.Table;
import com.cm.cassandra.persistence.types.CqlType;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Çelebi Murat on 13/11/15.
 */
public class Queries {
    private static final String CREATE_TABLE_IF_NOT_EXISTS = " CREATE TABLE IF NOT EXISTS ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String STATIC = " STATIC ";

    private static final String COLUMN_DEF_SEPARATOR = ",";
    private static final String BLOCK_START = "(";
    private static final String BLOCK_END = ")";

    private static final String WITH = " WITH ";
    private static final String CLUSTRING_ORDER_BY = " CLUSTRING ORDER BY ";
    private static final String COMPACT_STORAGE = " COMPACT STORAGE ";

    public static String getTableDefinition(Table table) {
        String definition = CREATE_TABLE_IF_NOT_EXISTS + table.getKeyspace().getName() + "." + table.getName();
        definition += BLOCK_START;
        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            definition += column.getDefinitionString() + COLUMN_DEF_SEPARATOR;
        }
        definition += BLOCK_END;

        return definition;
    }

    public static String getColumnDefinition(String columnName, javax.persistence.Column jxColumn, Field field) {
        String definitionString = "";
        if(jxColumn.columnDefinition() != null) {
            definitionString = jxColumn.columnDefinition();
            if(definitionString.endsWith(".") || definitionString.endsWith(";") || definitionString.endsWith(";")) {
                definitionString.substring(0, definitionString.length() - 1);
            }
        } else {
            CqlType cqlType = CqlType.getByJavaCanonicalName(field.getType().getCanonicalName());
            definitionString = definitionString.concat(columnName).concat(" ").concat(cqlType.name());
        }

        return definitionString;
    }

}
