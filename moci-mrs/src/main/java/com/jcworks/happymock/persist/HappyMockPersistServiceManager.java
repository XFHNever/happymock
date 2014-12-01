package com.jcworks.happymock.persist;

import com.jcworks.happymock.persist.model.MockItem;
import com.jcworks.happymock.persist.service.HappyMockPersistService;
import com.jcworks.happymock.persist.service.impl.MongoHappyMockPersistServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fuxie on 2014/9/5  9:57.
 */
@Deprecated
public class HappyMockPersistServiceManager{
    private static final Logger LOG = LoggerFactory.getLogger(HappyMockPersistServiceManager.class);

 //   private static ApplicationContext context;

    private static HappyMockPersistService happyMockPersistService;

    private HappyMockPersistServiceManager() {
        if (happyMockPersistService == null) {
            happyMockPersistService = new MongoHappyMockPersistServiceImpl();
        }
    }

    private static class HappyMockPersistServiceManagerHolder {
        private static final HappyMockPersistServiceManager MANAGER_INSTANCE = new HappyMockPersistServiceManager();
    }

    public static HappyMockPersistServiceManager  getInstance() {
       return HappyMockPersistServiceManagerHolder.MANAGER_INSTANCE;
    }

    /**
     * get the list of DSLs with given domainName and resourceName.
     *
     * @param username the name of user created the item.
     * @param domainName the name of domain you want to search.
     * @param resourceName the name of resource you want to search.
     * @return the list of all DSLs satisfies requirements.
     */
    public List<String> getDSLs(String username, String domainName, String resourceName) {
        LOG.debug("get dsls with given username={} , domainName={} and resourceName={}",new Object[]{username,domainName,resourceName});

        List<String> dsls = new ArrayList<String>();


        if (username == null || domainName == null || resourceName == null) {
            return dsls;
        }

        List<MockItem> items = happyMockPersistService.findActive(username, domainName, resourceName);

        Iterator iterator = items.iterator();

        while (iterator.hasNext()) {
            MockItem item = (MockItem) iterator.next();
            dsls.add(item.getContent());
        }

        return dsls;
    }

    /**
     * get the list of DSLs with given domainName and resourceName.
     *
     * @param username the name of user created the item.
     * @return the list of all DSLs satisfies requirements.
     */
    public List<String> getDSLsByUser(String username) {
        LOG.debug("get dsls with given username={} ",new Object[]{username});

        List<String> dsls = new ArrayList<String>();


        if (username == null) {
            return dsls;
        }

        List<MockItem> items = happyMockPersistService.findActiveByUser(username);

        Iterator iterator = items.iterator();

        while (iterator.hasNext()) {
            MockItem item = (MockItem) iterator.next();
            dsls.add(item.getContent());
        }

        return dsls;
    }

}
