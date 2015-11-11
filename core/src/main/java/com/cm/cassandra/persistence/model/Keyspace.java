package com.cm.cassandra.persistence.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Çelebi Murat on 09/11/15.
 */
public class Keyspace {
    private List<Table> tables = new ArrayList<>();

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
