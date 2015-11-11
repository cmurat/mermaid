package com.cm.cassandra.persistence.model;

/**
 * Created by Çelebi Murat on 09/11/15.
 */
public class Column {
    private String name;
    private Class mappingObject;
    private Table table;
    private String definitionString;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Class getMappingObject() {
        return mappingObject;
    }

    public void setMappingObject(Class mappingObject) {
        this.mappingObject = mappingObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinitionString() {
        return definitionString;
    }

    public void setDefinitionString(String definitionString) {
        this.definitionString = definitionString;
    }
}
