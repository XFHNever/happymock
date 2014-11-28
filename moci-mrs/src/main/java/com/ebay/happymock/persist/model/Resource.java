package com.ebay.happymock.persist.model;

/**
 *
 * This class is a model for DBs and using to specify different resource in the same domain.
 *
 * Created by fuxie on 2014/8/19  8:38.
 */
//@Document(collection = "resources")
public class Resource {
 //   @Id
    private String id;
    private String name;
    private String domain_id;
    private String user_id;

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

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Resource() {
    }

    public Resource(String name, String domain_id, String user_id) {
        this.name = name;
        this.domain_id = domain_id;
        this.user_id = user_id;
    }

    public Resource(String id, String name, String domain_id, String user_id) {
        this.id = id;
        this.name = name;
        this.domain_id = domain_id;
        this.user_id = user_id;
    }
}
