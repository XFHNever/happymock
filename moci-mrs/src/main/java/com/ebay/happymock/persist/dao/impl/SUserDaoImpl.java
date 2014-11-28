package com.ebay.happymock.persist.dao.impl;

import com.ebay.happymock.persist.dao.UserDao;
import com.ebay.happymock.persist.model.User;
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
 * Created by fuxie on 2014/10/16  8:56.
 */
public class SUserDaoImpl implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(SUserDaoImpl.class);

    private MongoConn conn = MongoConn.getInstance();
    private DBCollection userCol = null;

    private DBCollection getUserCollection(){
        if(userCol==null){
            userCol=conn.getDB().getCollection("users");
        }
        return userCol;
    }

    /**
     * Save the given {@link com.ebay.happymock.persist.model.User}. #
     *
     * @param user the user to save.
     */
    @Override
    public void save(User user) {
        LOG.debug("save User " + user.getName());

        getUserCollection().insert(MongoUtil.toDBObject(user));
    }

    /**
     * delete the {@link com.ebay.happymock.persist.model.User} with given identifier.
     *
     * @param id the identifier of {@link com.ebay.happymock.persist.model.User} which to be deleted.
     */
    @Override
    public void delete(String id) {
        LOG.debug("delete User with given id = " + id);

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        getUserCollection().remove(query);
    }

    /**
     * update the {@link com.ebay.happymock.persist.model.User} with a unchanged identifier {@link com.ebay.happymock.persist.model.User#id}.
     *
     * @param user the user to update.
     */
    @Override
    public void update(User user) {
        LOG.debug("update User " + user.getName());

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(user.getId()));

        DBObject dbObject =  MongoUtil.toDBObject(user);

        getUserCollection().findAndModify(query, dbObject);
    }

    /**
     * find all {@link com.ebay.happymock.persist.model.User}s.
     *
     * @return the list of all users.
     */
    @Override
    public List<User> findAll() {
        LOG.debug("find all  Users ");

        ArrayList<User> users = new ArrayList<User>();
        DBCursor cursor = getUserCollection().find();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            User user = MongoUtil.toObject(object, User.class);
            user.setId(id);
            users.add(user);
        }
        return users;
    }

    /**
     * find only one {@link com.ebay.happymock.persist.model.User} with a identifier {@link com.ebay.happymock.persist.model.User#id}
     *
     * @param id the identifier of {@link com.ebay.happymock.persist.model.User}.
     * @return one user.
     */
    @Override
    public User findById(String id) {
        LOG.debug("find a User with given id = " + id);
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject result = null;
        DBCursor cursor = getUserCollection().find(query);
        while (cursor.hasNext()) {
            result = cursor.next();
        }

        if (result == null) {
            return null;
        }

        User user = MongoUtil.toObject(result, User.class);
        user.setId(id);
        return user;
    }

    /**
     * find all {@link com.ebay.happymock.persist.model.User} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value    the value of given attribute.
     * @return a list of all users satisfies the conditions.
     */
    @Override
    public List<User> findByAttr(String attrName, String value) {
        LOG.debug("find all  Users with given attrName = " + attrName + " and value = " + value);

        ArrayList<User> users = new ArrayList<User>();

        Pattern valuePattern = Pattern.compile(value , Pattern.CASE_INSENSITIVE);
        DBObject query = new BasicDBObject(attrName, valuePattern);

        DBCursor cursor = getUserCollection().find(query);
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            User user = MongoUtil.toObject(object, User.class);
            user.setId(id);
            users.add(user);
        }
        return users;
    }
}
