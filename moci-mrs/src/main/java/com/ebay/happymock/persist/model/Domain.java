package com.ebay.happymock.persist.model;

import org.bson.types.ObjectId;

/**
 * This class is a model for DBs and using to specify different context in moco server.
 *
 * Created by fuxie on 2014/7/10  15:16.
 */
//@Document(collection = "domains")
public class Domain {
   // @Id
    private ObjectId _id;
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Domain() {
    }

    public Domain(String name) {
        this.name = name;
    }

    public Domain(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
