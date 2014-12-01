package com.jcworks.happymock.persist.dao.impl;

import com.jcworks.happymock.persist.dao.DomainDao;
import com.jcworks.happymock.persist.model.Domain;
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
 * Created by fuxie on 2014/9/22  14:48.
 */
public class SDomainDaoImpl implements DomainDao {
    private static final Logger LOG = LoggerFactory.getLogger(SDomainDaoImpl.class);

    private MongoConn conn = MongoConn.getInstance();
    private DBCollection domainCol;//lazy load this

    private DBCollection getDomainCollection(){
       if(domainCol==null){
           domainCol=conn.getDB().getCollection("domains");
       }
        return domainCol;
    }

    /**
     * save the given {@link com.jcworks.happymock.persist.model.Domain}.
     *
     * @param domain the domain to save.
     */
    @Override
    public void save(Domain domain) {
        LOG.debug("save Domain " + domain.getName());
        getDomainCollection().insert(MongoUtil.toDBObject(domain));
    }

    /**
     * delete the {@link com.jcworks.happymock.persist.model.Domain} with given identifier.
     *
     * @param id the identifier of {@link com.jcworks.happymock.persist.model.Domain} which to be deleted.
     */
    @Override
    public void delete(String id) {
        LOG.debug("delete Domain with given id = " + id);

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        getDomainCollection().remove(query);
    }

    /**
     * update the {@link com.jcworks.happymock.persist.model.Domain} with a unchanged identifier {@link com.jcworks.happymock.persist.model.Domain#id}
     *
     * @param domain the domain to udpate.
     */
    @Override
    public void update(Domain domain) {
        LOG.debug("update Domain " + domain.getName());

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(domain.getId()));

        DBObject dbObject =  MongoUtil.toDBObject(domain);

        getDomainCollection().findAndModify(query, dbObject);
    }

    /**
     * find all {@link com.jcworks.happymock.persist.model.Domain}s.
     *
     * @return the list of all domains.
     */
    @Override
    public List<Domain> findAll() {
        LOG.debug("find all  Domains ");

        ArrayList<Domain> domains = new ArrayList<Domain>();
        DBCursor cursor = getDomainCollection().find();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            Domain domain = MongoUtil.toObject(object, Domain.class);
            domain.setId(id);
            domains.add(domain);
        }
        return domains;
    }

    /**
     * find only one {@link com.jcworks.happymock.persist.model.Domain} with a identifier {@link com.jcworks.happymock.persist.model.Domain#id}
     *
     * @param id the identifier of {@link com.jcworks.happymock.persist.model.Domain}.
     * @return one domain satisfies the requirements.
     */
    @Override
    public Domain findById(String id) {
        LOG.debug("find a Domain with given id = " + id);
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject result = null;
        DBCursor cursor = getDomainCollection().find(query);
        while (cursor.hasNext()) {
            result = cursor.next();
        }

        if (result == null) {
            return null;
        }

        Domain domain = MongoUtil.toObject(result, Domain.class);
        domain.setId(id);
        return domain;
    }

    /**
     * find all {@link com.jcworks.happymock.persist.model.Domain} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value    the value of given attribute.
     * @return a list of all domains satisfies the conditions.
     */
    @Override
    public List<Domain> findByAttr(String attrName, String value) {
        LOG.debug("find all  Domains with given attrName = " + attrName + " and value = " + value);

        ArrayList<Domain> domains = new ArrayList<Domain>();

        Pattern valuePattern = Pattern.compile(value , Pattern.CASE_INSENSITIVE);
        DBObject query = new BasicDBObject(attrName, valuePattern);
  //      query.put(attrName, value);

        DBCursor cursor = getDomainCollection().find(query);
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            String id = object.get("_id").toString();
            Domain domain = MongoUtil.toObject(object, Domain.class);
            domain.setId(id);
            domains.add(domain);
        }
        return domains;
    }
}
