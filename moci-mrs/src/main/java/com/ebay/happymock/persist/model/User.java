package com.ebay.happymock.persist.model;

/**
 * This class is a model for DBs and records user's personal information.
 *
 * Created by fuxie on 2014/7/10  12:41.
 */
public class User {
    private String id;
    private String name;
    private String password;
    private String domain_id;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }

    public User() {
    }

    public User(String name, String password, String domain_id) {
        this.name = name;
        this.password = password;
        this.domain_id = domain_id;
    }

    public User(String id, String name, String password, String domain_id) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.domain_id = domain_id;
    }
}
