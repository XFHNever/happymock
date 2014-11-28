package com.ebay.happymock.persist.dao;


import com.ebay.happymock.persist.model.MockItem;

import java.util.List;

/**
 * Interface handling {@link MockItem} Data persistence
 *
 * Created by fuxie on 2014/7/10  16:00.
 */
public interface MockItemDao {
    /**
     * Save the given {@link MockItem}. #
     *
     * @param mockItem the mockItem to save.
     */
    public void save(MockItem mockItem);

    /**
     * delete the {@link MockItem} with given identifier.
     *
     * @param id the identifier of {@link MockItem} which to be deleted.
     */
    public void delete(String id);

    /**
     * update the {@link MockItem} with a unchanged identifier {@link MockItem#id}.
     *
     * @param mockItem the mockItem to update.
     */
    public void update(MockItem mockItem);

    /**
     * find all {@link MockItem}s.
     *
     * @return the list of all mocoItems.
     */
    public List<MockItem> findAll();

    /**
     * find all {@link MockItem} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value the value of given attribute.
     * @return a list of all {@link MockItem}s satisfies the conditions.
     */
    public List<MockItem> findByAttr(String attrName, Object value);

    /**
     * find only one {@link MockItem} with a identifier {@link MockItem#id}
     *
     * @param id the identifier of {@link MockItem}.
     * @return one mocoItem.
     */
    public MockItem findById(String id);

    /**
     * find all active {@link MockItem}s.
     *
     * @return the list of all active {@link MockItem}s.
     */
    public List<MockItem> findAllActive();

    /**
     * find all active {@link MockItem}s with given attrName and value.
     *
     * @param attrName the name of attribute.
     * @param value the value of given attribute.
     * @return a list of all active {@link MockItem}s satisfies the conditions.
     */
    public List<MockItem> findActiveByAttr(String attrName, Object value);
}
