package com.cm.cassandra.persistence.types;

/**
 * Created by Çelebi Murat on 12/11/15.
 */
public class Counter {
    private Long value;

    public Counter(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
