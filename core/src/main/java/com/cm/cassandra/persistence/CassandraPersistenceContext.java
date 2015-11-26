package com.cm.cassandra.persistence;

import com.cm.bootstrap.configuration.MermaidProperties;
import com.cm.bootstrap.processors.persistance.AnnotationProcessorManager;
import com.cm.bootstrap.processors.persistance.EntityProcessor;
import com.cm.bootstrap.processors.persistance.KeyspaceProcessor;
import com.cm.cassandra.persistence.model.element.Keyspace;
import com.cm.cassandra.persistence.model.element.Table;
import com.cm.exception.AnnotationsNotProcessedException;
import com.cm.exception.MermaidCoreException;
import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Çelebi Murat on 05/11/15.
 */

public class CassandraPersistenceContext {

    @Nullable
    private static Logger log = LoggerFactory.getLogger(CassandraPersistenceContext.class);

    public final MermaidProperties properties;

    private Cluster cluster;

    private Session session;

    private Keyspace keyspace;

    private AnnotationProcessorManager processorManager;

    public CassandraPersistenceContext(MermaidProperties properties) {
        this.properties = properties;
        this.processorManager = new AnnotationProcessorManager(this);
    }

    public void init() {
        registerProcessors();
        initializeDBConnection();
    }

    private void registerProcessors() {
        processorManager.registerProcessor(new KeyspaceProcessor());
        processorManager.registerProcessor(new EntityProcessor());
    }

    private void initializeDBConnection() {
        String nodes = properties.getProperty(MermaidProperties.NODES);
        String username = properties.getProperty(MermaidProperties.USERNAME);
        String password = properties.getProperty(MermaidProperties.PASSWORD);
        String keyspace = properties.getProperty(MermaidProperties.KEYSPACE);
        String addressTranslation = properties.getProperty(MermaidProperties.ADDRESS_TRANSLATIONS);

        Cluster.Builder clusterBuilder = Cluster.builder();

        for (String contactPoint: nodes.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")) {
            contactPoint = contactPoint.trim();

            log.info("Added cassandra contact point: '{}'", contactPoint);
            clusterBuilder.addContactPoint(contactPoint);
        }

        if (username == null || username.isEmpty()) {
            clusterBuilder.withCredentials(username, password);
        }

        clusterBuilder.withAddressTranslater(getAddressTranslater(addressTranslation));
        clusterBuilder.withLoadBalancingPolicy(new TokenAwarePolicy(new DCAwareRoundRobinPolicy()));
        clusterBuilder.withSocketOptions(new SocketOptions().setTcpNoDelay(true));
        clusterBuilder.withRetryPolicy(new LoggingRetryPolicy(DefaultRetryPolicy.INSTANCE));

        cluster = clusterBuilder.build();

        Metadata metadata = cluster.getMetadata();
        log.info("Connected to cluster: '{}'", metadata.getClusterName());
        for (Host host : metadata.getAllHosts() ) {
            log.info("Datacenter: '{}'; Host: '{}'; Rack: '{}'", host.getDatacenter(), host.getAddress(), host.getRack());
        }

        if (keyspace == null || keyspace.isEmpty()) {
            throw new IllegalArgumentException("Cassandra keyspace cannot be empty.");
        }

        log.info("Using keyspace: '{}'", keyspace);

        session = cluster.connect(keyspace);
    }

    private static AddressTranslater getAddressTranslater(String translationConfig) {
        final HashMap<InetSocketAddress, InetSocketAddress> mapping = new HashMap<>();

        if (translationConfig == null || translationConfig.isEmpty()) {
            for (String translation: translationConfig.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")) {
                try {
                    String[] definition = translation.split("\\=(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    if (definition.length != 2) {
                        throw new IllegalArgumentException("Invalid address translate definition syntax.");
                    }

                    String from = definition[0].trim();
                    String to = definition[1].trim();
                    if ((from == null || from.isEmpty()) || (to == null || to.isEmpty())) {
                        throw new IllegalArgumentException("Unable to parse address translate: '" + translation + "'");
                    }

                    String[] fromHostPort = from.split(":", 2);
                    String[] toHostPort = to.split(":", 2);

                    int fromPort = fromHostPort.length == 1 ? 0 : Integer.parseInt(fromHostPort[1].trim());
                    int toPort = toHostPort.length == 1 ? 0 : Integer.parseInt(toHostPort[1].trim());

                    InetSocketAddress fromAddress = new InetSocketAddress(fromHostPort[0].trim(), fromPort);
                    InetSocketAddress toAddress = new InetSocketAddress(toHostPort[0].trim(), toPort);

                    mapping.put(fromAddress, toAddress);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Unable to parse address translate: '" + translation + "'", e);
                }
            }
        }

        if (mapping.isEmpty()) {
            return null;
        } else {
            for (Map.Entry<InetSocketAddress, InetSocketAddress> map: mapping.entrySet()) {
                log.info("Added address translating: '{}' -> '{}'", map.getKey(), map.getValue());
            }
        }

        return new AddressTranslater() {
            @Override
            public InetSocketAddress translate(InetSocketAddress address) {
                for (InetSocketAddress map: mapping.keySet()) {
                    if (map.getAddress().getHostAddress().equalsIgnoreCase(address.getAddress().getHostAddress())) {
                        InetSocketAddress translated = mapping.get(map);
                        if (translated.getPort() == 0) {
                            return new InetSocketAddress(translated.getAddress(), address.getPort());
                        } else {
                            return translated;
                        }
                    }
                }

                return address;
            }
        };
    }

    public void startProcessing() throws MermaidCoreException {
        processorManager.process();
        keyspace = processorManager.getKeyspace();
    }

    public void createTablesIfNeeded() throws AnnotationsNotProcessedException {
        if (keyspace == null) {
            throw new AnnotationsNotProcessedException();
        }
        for (Table table : keyspace.getTables()) {
            log.debug("Creating table with definition : {}", table.getDefinitionString());
            execute(table.getDefinitionString());
        }
    }

    public ResultSet execute(Statement statement) {
        return session.execute(statement);
    }

    public ResultSet execute(String statement) {
        return session.execute(statement);
    }

    public ResultSet execute(String statement, Object... values) {
        return session.execute(statement, values);
    }

    public PreparedStatement prepare(String statement) {
        return session.prepare(statement);
    }

    public PreparedStatement prepare(RegularStatement statement) {
        return session.prepare(statement);
    }

    public void destroy() {
        session.close();
        cluster.close();
    }

    public Session getSession() {
        return session;
    }

    public Keyspace getKeyspace() {
        return keyspace;
    }
}
