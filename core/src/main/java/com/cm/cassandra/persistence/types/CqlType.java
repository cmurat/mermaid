package com.cm.cassandra.persistence.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by Çelebi Murat on 12/11/15.
 */
public enum CqlType {

    BIGINT("bigint", Long.class, long.class),
    VARINT("varint", BigInteger.class),
    INT("int", Integer.class, int.class),
    COUNTER("counter", Counter.class),

    DECIMAL("decimal", BigDecimal.class),
    DOUBLE("double", Double.class, double.class),
    FLOAT("float", Float.class, float.class),

    BOOLEAN("boolean", Boolean.class, boolean.class),

    INET("inet", InetAddress.class),
    BLOB("blob", ByteBuffer.class),
    TEXT("text", String.class),

    LIST("list", List.class),
    MAP("map", Map.class),
    SET("set", Set.class),

    TIME_STAMP("timestamp", Date.class),
    TIME_UUID("timeuuid", UUID.class);

    private String cqlName;
    private List<Class> javaTypes;
    private CqlType(String cqlName, Class... javaTypes) {
        this.javaTypes = Arrays.asList(javaTypes);
        this.cqlName = cqlName;
    }

    private static Map<String, CqlType> javaCqlMapping = new HashMap<>();

    static {
        for (CqlType cqlType : CqlType.values()) {
            for (Class javaType : cqlType.getJavaTypes()) {
                javaCqlMapping.put(javaType.getName(), cqlType);
            }
        }
    }

    public static CqlType getByCqlName(String cqlName) {
        for(CqlType cqlType : CqlType.values()) {
            if(cqlType.getCqlName().equals(cqlName)) {
                return cqlType;
            }
        }
        return null;
    }

    public static CqlType getByJavaType(Class type) {
        return javaCqlMapping.get(type.getCanonicalName());
    }

    public static CqlType getByJavaCanonicalName(String typeName) {
        return javaCqlMapping.get(typeName);
    }

    public String getCqlName() {
        return cqlName;
    }

    public void setCqlName(String cqlName) {
        this.cqlName = cqlName;
    }

    public List<Class> getJavaTypes() {
        return javaTypes;
    }

    public void setJavaTypes(List<Class> javaTypes) {
        this.javaTypes = javaTypes;
    }
}
