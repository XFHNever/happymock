package com.ebay.happymock.persist.dao;

import com.ebay.happymock.persist.dao.impl.MongoConn;
import com.ebay.happymock.persist.model.MongoProperties;
import com.mongodb.DB;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by fuxie on 2014/9/22  14:27.
 */
public class MongoConnTest {
    @Test
    public void testGetInstance() {
        MongoConn conn = MongoConn.getInstance();
        conn.init();
        assertNotNull(conn);
    }

    @Test
    public void testGetDB() {
        MongoConn conn = MongoConn.getInstance();
        conn.init();
        DB mock = conn.getDB();
        assertNotNull(mock);
        assertEquals(mock.getName(), "mock2");
    }
}
