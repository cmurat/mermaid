package com.cm.cassandra.persistence.types;
/**
 * Created by Çelebi Murat on 12/11/15.
 */
import com.cm.bootstrap.util.BootstrapUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.*;

public class ColumnData {
    private static Map<Class, CqlType> javaCqlMapping = new HashMap<>();

    static {
        for (CqlType cqlType : CqlType.values()) {
            for (Class klass : cqlType.getJavaTypes()) {
                javaCqlMapping.put(klass, cqlType);
            }
        }
    }

    /**
     * This list contains the generic types in a ParameterizedType.
     * It is filled with Depth-first search and in-order traversal
     * excluding the root.
     */
    public List<CqlType> inOrderedGenerics = new ArrayList<>();

    private CqlType cqlType;

    public ColumnData () {

    }

    public CqlType initWithCqlName(String cqlName) {
        for(CqlType cqlType : CqlType.values()) {
            if(cqlType.getCqlName().equalsIgnoreCase(cqlName)) {
                return cqlType;
            }
        }
        return null;
    }

    public ColumnData getByField(Field field) {
        return initWithFieldGenericType(field.getGenericType());
    }

    public ColumnData initWithFieldGenericType(Type type) {
        CqlType cqlType = initWithJavaQualifyingName(type.getTypeName()).getCqlType();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] generics = parameterizedType.getActualTypeArguments();
            for (Type genericType : generics) {
                CqlType genericCqlType = initWithFieldGenericType(genericType).getCqlType();
                inOrderedGenerics.add(genericCqlType);
            }
        }
        this.setCqlType(cqlType);
        return this;
    }

    private ColumnData initWithObject(Object o) {
        Set<Class> javaTypes = javaCqlMapping.keySet();
        for (Class klass : javaTypes) {
            if(klass.isInstance(o)) {
                CqlType cqlType = javaCqlMapping.get(klass);
                this.setCqlType(cqlType);
                return this;
            }
        }

        return null;
    }

    public ColumnData initWithClass(Class klass) {
        CqlType cqlType = javaCqlMapping.get(klass);
        if(cqlType != null) {
            this.setCqlType(cqlType);
            return this;
        }

        try {
            return initWithObject(klass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ColumnData initWithJavaQualifyingName(String typeName) {
        try {
            return initWithClass(Class.forName(getQualifyingNameForClass(typeName)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getQualifyingNameForClass(String name) {
        if(name.contains("<")) {
            name = name.split("<")[0];
        }

        if(name.contains("class")) {
            name = name.replace("class", "");
        }

        if(name.contains("interface")) {
            name = name.replace("interface", "");
        }

        return name.trim();
    }

    public String asCql() {
        String cql = "%s";

        List<CqlType> types = new ArrayList<>();;
        types.addAll(inOrderedGenerics);
        types.add(cqlType);
        Collections.reverse(types);
        for (int i = 0; i < types.size(); i++) {
            CqlType currentType = types.get(i);
            if (currentType.equals(CqlType.MAP)) {
                cql = BootstrapUtil.replaceLastOccurance(cql, "%s", currentType.getCqlName());

                if(types.get(i + 1).equals(CqlType.MAP)) {
                    continue;
                }

                i++;
                currentType = types.get(i);
                cql = BootstrapUtil.replaceLastOccurance(cql, "%s", currentType.getCqlName());
                i++;
                currentType = types.get(i);
                cql = BootstrapUtil.replaceLastOccurance(cql, "%s", currentType.getCqlName());
            } else if(currentType.equals(CqlType.SET) || currentType.equals(CqlType.LIST)) {


            } else {
                cql = BootstrapUtil.replaceLastOccurance(cql, "%s", currentType.getCqlName());
            }
        }

        return cql;
    }

    public CqlType getCqlType() {
        return cqlType;
    }

    public void setCqlType(CqlType cqlType) {
        this.cqlType = cqlType;
    }

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

        LIST("list<%s>", List.class),
        MAP("map<%s, %s>", Map.class),
        SET("set<%s>", Set.class),

        TIME_STAMP("timestamp", Date.class),
        TIME_UUID("timeuuid", UUID.class);

        private String cqlName;
        private List<Class> javaTypes;

        private CqlType(String cqlName, Class... javaTypes) {
            this.javaTypes = Arrays.asList(javaTypes);
            this.cqlName = cqlName;
        }

        public String getCqlName() {
            return cqlName;
        }

        public List<Class> getJavaTypes() {
            return javaTypes;
        }
    }
}
