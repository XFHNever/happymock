package com.jcworks.happymock.persist.dao.impl;

import com.jcworks.happymock.persist.dao.ResourceDao;
import com.jcworks.happymock.persist.model.Resource;
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
 * Created by fuxie on 2014/9/23  9:52.
 */
public class SResourceDaoImpl implements ResourceDao {
    private static final Logger LOG = LoggerFactory.getLogger(SResourceDaoImpl.class);

    private MongoConn conn = MongoConn.getInstance();
    private DBCollection resourceCol = null;

    private DBCollection getResourceCollection(){
        if(resourceCol==null){
            resourceCol=conn.getDB().getCollection("resources");
        }
        return resourceCol;
    }

   /**
     * save the given {@link com.jcworks.happymock.persist.model.Resource}.
     *
     * @param resource the resource to save.
     */
    @Override
    public void save(Resource resource) {
        LOG.debug("save Resource " + resource.getName());

        getResourceCollection().insert(MongoUtil.toDBObject(resource));
    }

    /**
     * delete the {@link com.jcworks.happymock.persist.model.Resource} with given identifier.
     *
     * @param id the identifier of {@link com.jcworks.happymock.persist.model.Resource} which to be deleted.
     */
    @Override
    public void delete(String id) {
        LOG.debug("delete Resource with given id = " + id);

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        getResourceCollection().remove(query);
    }

    /**
     * update the {@link com.jcworks.happymock.persist.model.Resource} with a unchanged identifier {@link com.jcworks.happymock.persist.model.Resource#id}
     *
     * @param resource the resource to udpate.
     */
    @Override
    public void update(Resource resource) {
        LOG.debug("update Resource " + resource.getName());

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(resource.getId()));

        DBObject dbObject =  MongoUtil.toDBObject(resource);

        getResourceCollection().findAndModify(query, dbObject);
    }

    /**
     * find all {@link com.jcworks.happymock.persist.model.Resource}s.
     *
     * @return the list of all resources.
     */
    @Override
    public List<Resource> findAll() {
        LOG.debug("find all  Resources ");

        ArrayList<Resource> resources = new ArrayList<Resource>();
        DBCursor cursor = getResourceCollection().find();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            Resource resource = MongoUtil.toObject(object, Resource.class);
            resource.setId(id);
            resources.add(resource);
        }
        return resources;
    }

    /**
     * find only one {@link com.jcworks.happymock.persist.model.Resource} with a identifier {@link com.jcworks.happymock.persist.model.Resource#id}
     *
     * @param id the identifier of {@link com.jcworks.happymock.persist.model.Resource}.
     * @return one resource satisfies the requirements.
     */
    @Override
    public Resource findById(String id) {
        LOG.debug("find a Resource with given id = " + id);
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject result = null;
        DBCursor cursor = getResourceCollection().find(query);
        while (cursor.hasNext()) {
            result = cursor.next();
        }

        if (result == null) {
            return null;
        }

        Resource resource = MongoUtil.toObject(result, Resource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * find all {@link com.jcworks.happymock.persist.model.Resource} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value    the value of given attribute.
     * @return a list of all resources satisfies the conditions.
     */
    @Override
    public List<Resource> findByAttr(String attrName, String value) {
        LOG.debug("find all  Resources with given attrName = " + attrName + " and value = " + value);

        ArrayList<Resource> resources = new ArrayList<Resource>();

        Pattern valuePattern = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
        DBObject query = new BasicDBObject(attrName, valuePattern);

        DBCursor cursor = getResourceCollection().find(query);
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            Resource resource = MongoUtil.toObject(object, Resource.class);
            resource.setId(id);
            resources.add(resource);
        }
        return resources;
    }
}
