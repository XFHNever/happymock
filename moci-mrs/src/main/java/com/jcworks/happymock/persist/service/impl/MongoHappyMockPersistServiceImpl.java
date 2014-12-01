package com.jcworks.happymock.persist.service.impl;

import com.jcworks.happymock.persist.dao.DomainDao;
import com.jcworks.happymock.persist.dao.MockItemDao;
import com.jcworks.happymock.persist.dao.ResourceDao;
import com.jcworks.happymock.persist.dao.UserDao;
import com.jcworks.happymock.persist.dao.impl.*;
import com.jcworks.happymock.persist.model.*;
import com.jcworks.happymock.persist.service.HappyMockPersistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fuxie on 2014/9/23  14:11.
 *
 *
 * @auth fuxie
 * @auth jicui
 */
public class MongoHappyMockPersistServiceImpl implements HappyMockPersistService {
    private static final Logger LOG = LoggerFactory.getLogger(MongoHappyMockPersistServiceImpl.class);

    private File mongoFile;
    private MockItemDao mockItemDao;
    private DomainDao domainDao;
    private ResourceDao resourceDao;
    private UserDao userDao;

    /**
     * Load mongo property by default settings
     */
    public MongoHappyMockPersistServiceImpl() {
        //MongoConn.getInstance().init();
        initDao();
    }

    private void initDao() {
        mockItemDao = new SMockItemDaoImpl();
        domainDao = new SDomainDaoImpl();
        resourceDao = new SResourceDaoImpl();
        userDao = new SUserDaoImpl();
    }

    /**
     * find all active items.
     *
     * @return the list of all active items.
     */
    @Override
    public List<MockItem> findAllActive() {
        LOG.info("find all active items");

        return mockItemDao.findAllActive();
    }

    /**
     * find all active items with given domainName.
     *
     * @param domainName the name of domain you want to search.
     * @return the list of all active items satisfies requirements.
     */
    @Override
    public List<MockItem> findActive(String domainName) {
        LOG.debug("find all active items with given domainName= " + domainName);
        List list = new ArrayList();
        //get domain with given domainName
        List domainList = domainDao.findByAttr("name", domainName);
        Boolean dataValidity = domainList != null && domainList.size() > 0;
        if (!dataValidity) {
            return list;
        }
        Domain selectedDomain = (Domain) domainList.get(0);

        //get resources with given domain_id
        List resourceList = resourceDao.findByAttr("domain_id", selectedDomain.getId());
        dataValidity = resourceList!= null && resourceList.size() > 0;
        if (!dataValidity) {
            return list;
        }

        //get all items with given resource_id
        Iterator iterator = resourceList.iterator();
        while (iterator.hasNext()) {
            Resource resource = (Resource) iterator.next();
            List items = mockItemDao.findActiveByAttr("resource_id", resource.getId());
            list.addAll(items);
        }

        return list;
    }

    /**
     * find all active items with given userName..
     *
     * @param userName the name of user you want to search.
     * @return the list of all active items satisfies requirements.
     */
    @Override
    public List<MockItem> findActiveByUser(String userName) {
        LOG.debug("find all active items with given userName= " + userName);

        List list = new ArrayList();

        //get domain with given domainName
        List userList = userDao.findByAttr("name", userName);
        Boolean dataValidity = userList != null && userList.size() > 0;
        if (!dataValidity) {
            return list;
        }
        User selectedUser = (User) userList.get(0);

        //get resources with given domain_id
        List resourceList = resourceDao.findByAttr("user_id", selectedUser.getId());
        dataValidity = resourceList!= null && resourceList.size() > 0;
        if (!dataValidity) {
            return list;
        }

        //get all items with given resource_id
        Iterator iterator = resourceList.iterator();
        while (iterator.hasNext()) {
            Resource resource = (Resource) iterator.next();
            List items = mockItemDao.findActiveByAttr("resource_id", resource.getId());
            list.addAll(items);
        }

        return list;
    }

    /**
     * find all active items with given domainName and resourceName.
     *
     * @param username the name of user created the items
     * @param domainName   the name of domain you want to search.
     * @param resourceName the name of resource you want to search.
     * @return the list of all active items satisfies requirements.
     */
    @Override
    public List<MockItem> findActive(String username, String domainName, String resourceName) {
        LOG.debug("find all active items with given domainName= " + domainName + " and resourceName= " + resourceName +
                "and username= " + username);

        List list = new ArrayList();

        List domainList = domainDao.findByAttr("name", domainName);
        List resourceList = resourceDao.findByAttr("name", resourceName);
        List userList = userDao.findByAttr("name", username);

        Boolean dataValidity = domainList != null && domainList.size() > 0
                && resourceList!= null && resourceList.size() > 0 && userList != null && userList.size() > 0;

        if (dataValidity) {
            Domain selectedDomain = (Domain) domainList.get(0);
            User selectedUser = (User) userList.get(0);
            Resource selectedResource = null;

            Iterator iterator = resourceList.iterator();
            while (iterator.hasNext()) {
                Resource resource = (Resource) iterator.next();
                if (resource.getDomain_id().equals(selectedDomain.getId()) &&
                        resource.getUser_id().equals(selectedUser.getId())) {
                    selectedResource = resource;
                    break;
                }
            }

            if (selectedResource == null) {
                return list;
            }

            list = mockItemDao.findActiveByAttr("resource_id", selectedResource.getId());

            return list;
        } else {
            return list;
        }
    }

    @Override
    public List<MockItem> find(String username) {
        LOG.debug("find all active items with given userName= " + username);
        checkNotNull(username);
        List list = new ArrayList();

        //get domain with given domainName
        LOG.info(userDao + "==" + username);
        List userList = userDao.findByAttr("name", username);
        Boolean dataValidity = userList != null && userList.size() > 0;
        if (!dataValidity) {
            return list;
        }
        User selectedUser = (User) userList.get(0);

        //get resources with given domain_id
        List resourceList = resourceDao.findByAttr("user_id", selectedUser.getId());
        dataValidity = resourceList!= null && resourceList.size() > 0;
        if (!dataValidity) {
            return list;
        }

        //get all items with given resource_id
        Iterator iterator = resourceList.iterator();
        while (iterator.hasNext()) {
            Resource resource = (Resource) iterator.next();
            List items = mockItemDao.findActiveByAttr("resource_id", resource.getId());
            list.addAll(items);
        }

        return list;
    }
}
