package com.cm.bootstrap.annotations.annotation;

import com.cm.bootstrap.annotations.model.DataCenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Çelebi Murat on 12/11/15.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Keyspace {
    String name();
    Class[] entities();
}
