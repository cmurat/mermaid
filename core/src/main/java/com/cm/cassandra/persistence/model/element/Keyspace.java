package com.cm.cassandra.persistence.model.element;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Çelebi Murat on 09/11/15.
 */
public class Keyspace {
    private String name;
    private Set<Class> entities = new HashSet<>();
    private Set<Table> tables = new HashSet<>();

    public Set<Table> getTables() {
        return tables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Class> getEntities() {
        return entities;
    }

    public void setEntities(Set<Class> entities) {
        this.entities = entities;
    }
}
