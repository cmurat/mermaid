package com.cm.cassandra.persistence.model.element;

import com.cm.cassandra.persistence.model.element.Column;
import com.cm.cassandra.persistence.model.element.Keyspace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Çelebi Murat on 09/11/15.
 */
public class Table {
    private Keyspace keyspace;
    private String name;
    private Class mappignObject;
    private List<Column> columns;
    private String definitionString;

    public Table() {
        columns = new ArrayList<>();
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Class getMappignObject() {
        return mappignObject;
    }

    public void setMappignObject(Class mappignObject) {
        this.mappignObject = mappignObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Keyspace getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(Keyspace keyspace) {
        this.keyspace = keyspace;
    }

    public String getDefinitionString() {
        return definitionString;
    }

    public void setDefinitionString(String definitionString) {
        this.definitionString = definitionString;
    }
}
