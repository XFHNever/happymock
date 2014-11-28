package com.ebay.happymock.persist.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by fuxie on 2014/11/6  9:54.
 */
public abstract class MongoProperties {
    private static final Logger LOG = LoggerFactory.getLogger(MongoProperties.class);
    private static final Properties properties=new Properties();

    /**
     * Load key from a global mongo properties
     *
     * @param key
     * @return
     */
    public static String getProperty(String key){
      return String.valueOf(properties.get(key));
    }

    public static void init(File file) {
        LOG.info("init mongo connect with file " + file.getAbsolutePath());
        try {
            FileInputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("loading property fails file=" + file.getAbsolutePath());
        }
    }

    public static void init() {
        LOG.info("init init mongo connect with default settings" + "/meta/mongo.properties");
        try {
            InputStream inputStream = MongoProperties.class.getResourceAsStream("/meta/mongo.properties");
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
