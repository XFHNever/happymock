package com.ebay.happymock.persist.dao;


import com.ebay.happymock.persist.model.Domain;

import java.util.List;

/**
 * Interface handling {@link Domain} Data persistence
 *
 * Created by fuxie on 2014/7/10  15:37.
 */
public interface DomainDao {
    /**
     * save the given {@link Domain}.
     *
     * @param domain the domain to save.
     */
    public void save(Domain domain);

    /**
     * delete the {@link Domain} with given identifier.
     *
     * @param id the identifier of {@link Domain} which to be deleted.
     */
    public void delete(String id);

    /**
     * update the {@link Domain} with a unchanged identifier {@link Domain#id}
     *
     * @param domain the domain to udpate.
     */
    public void update(Domain domain);

    /**
     * find all {@link Domain}s.
     *
     * @return the list of all domains.
     */
    public List<Domain> findAll();

    /**
     * find only one {@link Domain} with a identifier {@link Domain#id}
     *
     * @param id the identifier of {@link Domain}.
     * @return one domain satisfies the requirements.
     */
    public Domain findById(String id);

    /**
     * find all {@link Domain} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value the value of given attribute.
     * @return a list of all domains satisfies the conditions.
     */
    public List<Domain> findByAttr(String attrName, String value);
}
