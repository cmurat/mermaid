package com.cm.cassandra.persistence.model;

/**
 * Created by Çelebi Murat on 18/11/15.
 */
public enum Strategy {
    NETWORK_TOPOLOGY("NetworkTopologyStrategy"),
    SIMPLE("SimpleStrategy");

    public final String className;
    private Strategy(String className) {
        this.className = className;
    }
}
