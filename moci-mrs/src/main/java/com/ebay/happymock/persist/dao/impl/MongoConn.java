package com.ebay.happymock.persist.dao.impl;

import com.ebay.happymock.persist.model.MongoProperties;
import com.google.common.base.Preconditions;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.UnknownHostException;
import java.util.Properties;

import static com.ebay.happymock.persist.model.MongoProperties.getProperty;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * MongoDB connector
 *
 * @auth fuxie
 * @auth jicui
 */
public class  MongoConn {
    private static final Logger LOG = LoggerFactory.getLogger(MongoConn.class);
    private static MongoClient mongoClient;
    private boolean initialized=false;

    /**
     * Init Mongo client by specific config
     */
    public void init(File file){
        MongoProperties.init(checkNotNull(file));
        buildMongoClient();
    }

    /**
     * Init Mongo client by default config
     */
    public void init(){
        MongoProperties.init();
        buildMongoClient();
    }

    /**
     * init mongo client
     */
    public void buildMongoClient(){
        LOG.debug("MongoConn instructor");
        if (mongoClient == null) {
            try {
                //config connection
                MongoClientOptions options = MongoClientOptions.builder()
                        .connectionsPerHost(Integer.valueOf(getProperty("mongo.connectionsPerHost")))
                        .connectTimeout(Integer.valueOf(getProperty("mongo.connectTimeout")))
                        .build();
                ServerAddress address = new ServerAddress(getProperty("mongo.host"), Integer.valueOf(getProperty("mongo.port")));
                LOG.info("Host: " + getProperty("mongo.host"));
                mongoClient = new MongoClient(address, options);
                initialized=true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
    private MongoConn() {}

    private static class MongoConnHolder {
        private static final MongoConn CONN_INSTANCE = new MongoConn();
    }

    public static MongoConn getInstance() {
        return MongoConnHolder.CONN_INSTANCE;
    }

    public DB getDB(String dbName) {
        checkState(initialized, "Mongo state is not initialized");
        LOG.debug("get DB: " + dbName);
        return mongoClient.getDB(dbName);
    }

    public DB getDB() {
        checkState(initialized, "Mongo state is not initialized");
        String dbName = getProperty("mongo.dbName");
        LOG.debug("get DB: " + dbName);
        return mongoClient.getDB(dbName);
    }
}
