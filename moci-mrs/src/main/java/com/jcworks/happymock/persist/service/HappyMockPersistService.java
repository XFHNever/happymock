package com.jcworks.happymock.persist.service;

import com.jcworks.happymock.persist.model.MockItem;

import java.util.List;

/**
 * Interface providing service about data operations.
 *
 * Created by fuxie on 2014/9/4  9:16.
 */
public interface HappyMockPersistService {
    /**
     * find all active items.
     *
     * @return the list of all active items.
     */
    public List<MockItem> findAllActive();

    /**
     * find all active items with given domainName.
     *
     * @param domainName the name of domain you want to search.
     * @return the list of all active items satisfies requirements.
     */
    public List<MockItem> findActive(String domainName);

    /**
     * find all active items with given userName..
     *
     * @param userName the name of user you want to search.
     * @return the list of all active items satisfies requirements.
     */
    public List<MockItem> findActiveByUser(String userName);

    /**
     * find all active items with given domainName and resourceName.
     *
     * @param username the name of user created the items
     * @param domainName the name of domain you want to search.
     * @param resourceName the name of resource you want to search.
     * @return the list of all active items satisfies requirements.
     */
    public List<MockItem> findActive(String username, String domainName, String resourceName);

    /**
     * find all active items with given username.
     *
     * @param username the name of user created the items
     * @return the list of all active items satisfies requirements.
     */
    public List<MockItem> find(String username);

}
