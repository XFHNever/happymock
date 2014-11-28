package com.ebay.happymock.persist.dao.impl;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Created by fuxie on 2014/9/22  15:07.
 */
public class MongoUtil<T> {
    public static DBObject toDBObject(Object object) {
        DBObject dbObject = (DBObject) JSON.parse(com.alibaba.fastjson.JSON.toJSONString(object));
        return  dbObject;
    }

    public static  <T> T toObject(DBObject object, Class<T> tClass) {
        T t = com.alibaba.fastjson.JSON.parseObject(object.toString(), tClass);
        return  t;
    }


}
