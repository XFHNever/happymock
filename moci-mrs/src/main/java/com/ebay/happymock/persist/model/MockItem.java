package com.ebay.happymock.persist.model;

/**
 * This class is a model and records information about the centralized server configurations.
 *
 * Created by fuxie on 2014/7/10  15:24.
 */
//@Document(collection = "mockitems")
public class MockItem {
    //@Id
    private String id;
    private String resource_id;
    private String name;
    private String content;
    private String description;
    private boolean active = false;

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public MockItem() {
    }

    public MockItem(String resource_id, String name, String content, String description, boolean active) {
        this.resource_id = resource_id;
        this.name = name;
        this.content = content;
        this.description = description;
        this.active = active;
    }
}
