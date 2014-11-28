package com.ebay.happymock.persist.dao;


import com.ebay.happymock.persist.model.User;

import java.util.List;

/**
 * Interface handling {@link User} Data persistence.
 *
 * Created by fuxie on 2014/7/10  12:47.
 */
public interface UserDao {
    /**
     * Save the given {@link User}. #
     *
     * @param user the user to save.
     */
    public void save(User user);

    /**
     * delete the {@link User} with given identifier.
     *
     * @param id the identifier of {@link User} which to be deleted.
     */
    public void delete(String id);

    /**
     * update the {@link User} with a unchanged identifier {@link User#id}.
     *
     * @param user the user to update.
     */
    public void update(User user);

    /**
     * find all {@link User}s.
     *
     * @return the list of all users.
     */
    public List<User> findAll();

    /**
     * find only one {@link User} with a identifier {@link User#id}
     *
     * @param id the identifier of {@link User}.
     * @return one user.
     */
    public User findById(String id);

    /**
     * find all {@link User} with given attribute and specific value.
     *
     * @param attrName the name of attribute.
     * @param value the value of given attribute.
     * @return a list of all users satisfies the conditions.
     */
    public List<User> findByAttr(String attrName, String value);
}
