package com.cm.bootstrap.annotations.model;

/**
 * Created by Çelebi Murat on 18/11/15.
 */
public final class DataCenter {
    public final String name;
    public final int replicaCount;

    public DataCenter(String name, int replicaCount) {
        this.name = name;
        this.replicaCount = replicaCount;
    }
}
