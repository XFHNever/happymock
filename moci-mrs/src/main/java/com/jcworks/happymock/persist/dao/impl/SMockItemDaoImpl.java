package com.jcworks.happymock.persist.dao.impl;

import com.jcworks.happymock.persist.dao.MockItemDao;
import com.jcworks.happymock.persist.model.MockItem;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by fuxie on 2014/9/23  11:09.
 */
public class SMockItemDaoImpl implements MockItemDao {
    private static final Logger LOG = LoggerFactory.getLogger(SMockItemDaoImpl.class);

    private MongoConn conn = MongoConn.getInstance();
    private DBCollection mockItemCol = null;//lazy load

    //TODO:concurrent issue goes here!!
    private DBCollection getItemCollection(){
        if(mockItemCol==null){
            mockItemCol=conn.getDB().getCollection("mockitems");
        }
        return mockItemCol;
    }
    /**
     * Save the given {@link com.jcworks.happymock.persist.model.MockItem}. #
     *
     * @param mockItem the mockItem to save.
     */
    @Override
    public void save(MockItem mockItem) {
        LOG.debug("save MockItem " + mockItem.getName());

        getItemCollection().insert(MongoUtil.toDBObject(mockItem));
    }

    /**
     * delete the {@link com.jcworks.happymock.persist.model.MockItem} with given identifier.
     *
     * @param id the identifier of {@link com.jcworks.happymock.persist.model.MockItem} which to be deleted.
     */
    @Override
    public void delete(String id) {
        LOG.debug("delete MockItem with given id = " + id);

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        getItemCollection().remove(query);
    }

    /**
     * update the {@link com.jcworks.happymock.persist.model.MockItem} with a unchanged identifier {@link com.jcworks.happymock.persist.model.MockItem#id}.
     *
     * @param mockItem the mockItem to update.
     */
    @Override
    public void update(MockItem mockItem) {
        LOG.debug("update MockItem " + mockItem.getName());

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(mockItem.getId()));

        DBObject dbObject =  MongoUtil.toDBObject(mockItem);

        getItemCollection().findAndModify(query, dbObject);
    }

    /**
     * find all {@link com.jcworks.happymock.persist.model.MockItem}s.
     *
     * @return the list of all mocoItems.
     */
    @Override
    public List<MockItem> findAll() {
        LOG.debug("find all  MockItems ");

        ArrayList<MockItem> mockItems = new ArrayList<MockItem>();
        DBCursor cursor = getItemCollection().find();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            MockItem mockItem = MongoUtil.toObject(object, MockItem.class);
            mockItem.setId(id);
            mockItems.add(mockItem);
        }
        return mockItems;
    }

    /**
     * find all {@link com.jcworks.happymock.persist.model.MockItem} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value    the value of given attribute.
     * @return a list of all {@link com.jcworks.happymock.persist.model.MockItem}s satisfies the conditions.
     */
    @Override
    public List<MockItem> findByAttr(String attrName, Object value) {
        LOG.debug("find all  MockItems with given attrName = " + attrName + " and value = " + value);

        ArrayList<MockItem> mockItems = new ArrayList<MockItem>();

        DBObject query = null;
        if (value instanceof String) {
            Pattern valuePattern = Pattern.compile(value.toString(), Pattern.CASE_INSENSITIVE);
            query = new BasicDBObject(attrName, valuePattern);
        } else {
            query = new BasicDBObject();
            query.put(attrName, value);
        }

        DBCursor cursor = getItemCollection().find(query);
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            MockItem mockItem = MongoUtil.toObject(object, MockItem.class);
            mockItem.setId(id);
            mockItems.add(mockItem);
        }
        return mockItems;
    }

    /**
     * find only one {@link com.jcworks.happymock.persist.model.MockItem} with a identifier {@link com.jcworks.happymock.persist.model.MockItem#id}
     *
     * @param id the identifier of {@link com.jcworks.happymock.persist.model.MockItem}.
     * @return one mocoItem.
     */
    @Override
    public MockItem findById(String id) {
        LOG.debug("find a MockItem with given id = " + id);
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject result = null;
        DBCursor cursor = getItemCollection().find(query);
        while (cursor.hasNext()) {
            result = cursor.next();
        }

        if (result == null) {
            return null;
        }

        MockItem mockItem = MongoUtil.toObject(result, MockItem.class);
        mockItem.setId(id);
        return mockItem;
    }

    /**
     * find all active {@link com.jcworks.happymock.persist.model.MockItem}s.
     *
     * @return the list of all active {@link com.jcworks.happymock.persist.model.MockItem}s.
     */
    @Override
    public List<MockItem> findAllActive() {
        return findByAttr("active", true);
    }

    /**
     * find all active {@link com.jcworks.happymock.persist.model.MockItem}s with given attrName and value.
     *
     * @param attrName the name of attribute.
     * @param value    the value of given attribute.
     * @return a list of all active {@link com.jcworks.happymock.persist.model.MockItem}s satisfies the conditions.
     */
    @Override
    public List<MockItem> findActiveByAttr(String attrName, Object value) {
        LOG.debug("find all active  MockItems with given attrName = " + attrName + " and value = " + value);

        ArrayList<MockItem> mockItems = new ArrayList<MockItem>();

        DBObject query = null;
        if (value instanceof String) {
            Pattern valuePattern = Pattern.compile(value.toString() , Pattern.CASE_INSENSITIVE);
            query = new BasicDBObject(attrName, valuePattern);
        } else {
            query = new BasicDBObject();
            query.put(attrName, value);
        }
        query.put("active", true);

        DBCursor cursor = getItemCollection().find(query);
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            MockItem mockItem = MongoUtil.toObject(object, MockItem.class);
            mockItem.setId(id);
            mockItems.add(mockItem);
        }
        return mockItems;
    }
}
