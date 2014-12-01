package com.jcworks.happymock.persist.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by fuxie on 2014/11/6  10:45.
 */
public class MongoPropertiesTest {
    @Test
    public void testInit() throws Exception {
        MongoProperties.init();
        assertEquals("27017", MongoProperties.getProperty("mongo.port"));
    }
}
