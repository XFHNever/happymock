package com.ebay.happymock.persist.dao;

import com.ebay.happymock.persist.model.Resource;

import java.util.List;

/**
 * Interface handling {@link Resource} Data persistence.
 *
 * Created by fuxie on 2014/9/4  9:46.
 */
public interface ResourceDao {
    /**
     * save the given {@link Resource}.
     *
     * @param resource the resource to save.
     */
    public void save(Resource resource);

    /**
     * delete the {@link Resource} with given identifier.
     *
     * @param id the identifier of {@link Resource} which to be deleted.
     */
    public void delete(String id);

    /**
     * update the {@link Resource} with a unchanged identifier {@link Resource#id}
     *
     * @param resource the resource to udpate.
     */
    public void update(Resource resource);

    /**
     * find all {@link Resource}s.
     *
     * @return the list of all resources.
     */
    public List<Resource> findAll();

    /**
     * find only one {@link Resource} with a identifier {@link Resource#id}
     *
     * @param id the identifier of {@link Resource}.
     * @return one resource satisfies the requirements.
     */
    public Resource findById(String id);

    /**
     * find all {@link Resource} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value the value of given attribute.
     * @return a list of all resources satisfies the conditions.
     */
    public List<Resource> findByAttr(String attrName, String value);
}
