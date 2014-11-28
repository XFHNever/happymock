package com.ebay.happymock.persist.service;

/**
 * Created by fuxie on 2014/9/4  14:02.
 */

public class HappyMockPersistServiceTest {
/*    @InjectMocks
    MongoHappyMockPersistServiceImpl happyMockService;

    @Mock
    private DomainDao domainDao;
    @Mock
    private ResourceDao resourceDao;
    @Mock
    private MockItemDao mockItemDao;
    @Mock
    private UserDao userDao;

    User user;
    Domain domain;
    Resource resourceA, resourceB, resourceE;
    List items, domains, resources, anotherResources, users;

    @BeforeTest
    public void initMock() {
        MockitoAnnotations.initMocks(this);

        //initial test data
        domain = new Domain("User", "1");
        domains = new ArrayList();
        domains.add(domain);

        user = new User("1000", "fuxie", "123456", "1");
        users = new ArrayList();
        users.add(user);

        resourceA = new Resource("10", "resourceA", "1", "1000");
        resourceB = new Resource("11", "resourceB", "1", "1000");
        resourceE = new Resource("11", "resourceB", "2", "1001");
        resources = new ArrayList();
        resources.add(resourceA);
        resources.add(resourceB);

        anotherResources = new ArrayList();
        anotherResources.add(resourceB);
        anotherResources.add(resourceE);

        items = new ArrayList();
        for (int i =0; i < 10; i++) {
            MockItem item = new MockItem("11", "item" + i, "test", "test", true);
            item.setId("10" + i);
            items.add(item);
        }
    }

    @Test
    public void testFindAllActive() throws Exception {
        activeStubbing();

        //test
        List list = happyMockService.findAllActive();

        assertEquals(list.size(), 10);
        assertEquals(((MockItem)list.get(0)).getName(), "item0");
        verify(mockItemDao, times(1)).findAllActive();
    }

    @Test
    public void testFindActiveWithDomain() throws Exception {
        activeStubbing();

        //test
        List list = happyMockService.findActive("User");
        assertEquals(list.size(), 20);
        assertEquals(((MockItem)list.get(0)).getName(), "item0");
    }

    @Test
    public void testFindActiveWithNotExistingDomain() throws Exception {
        activeStubbing();

        //test
        List list = happyMockService.findActive("Buy");
        assertEquals(list.size(), 0);
    }

    @Test
    public void testFindActiveWithDomainResource() throws Exception {
        activeStubbing();

        List list = happyMockService.findActive("fuxie", "User", "ResourceB");

        assertEquals(list.size(), 10);
        assertEquals(((MockItem)list.get(0)).getName(), "item0");
    }

    @Test
    public void testFindActiveWithNotExistingDR() throws Exception {
        activeStubbing();

        List list = happyMockService.findActive("fuxie", "Buy", "ResourceC");

        assertEquals(list.size(), 0);
    }
    @Test
    public void testFindActiveWithNotExistingD() throws Exception {
        activeStubbing();

        List list = happyMockService.findActive("fuxie", "Buy", "ResourceB");

        assertEquals(list.size(), 0);
    }

    @Test
    public void testFindActiveWithNotExistingResource() throws Exception {
        activeStubbing();

        List list = happyMockService.findActive("fuxie", "User", "ResourceC");

        assertEquals(list.size(), 0);
    }
    @Test
    public void testFindActiveWithNotMatchDR() throws Exception {
        activeStubbing();

        List list = happyMockService.findActive("fuxie", "User", "ResourceE");

        assertEquals(list.size(), 0);
    }
    @Test
    public void testFindActiveWithNotExsitingUser() throws Exception {
        activeStubbing();

        List list = happyMockService.findActive("jicui", "User", "ResourceB");

        assertEquals(list.size(), 0);
    }

    @Test
    public void testFindActiveByUser() throws Exception {
        activeStubbing();
        List list = happyMockService.findActiveByUser("fuxie");

        assertEquals(list.size(), 20);
    }

    @Test
    public void testFindActiveByUserWithNotExisting() throws Exception {
        activeStubbing();
        List list = happyMockService.findActiveByUser("jicui");

        assertEquals(list.size(), 0);
    }

    private void activeStubbing() {
        //stubbing
        when(mockItemDao.findAllActive()).thenReturn(items);

        when(userDao.findByAttr("name", "fuxie")).thenReturn(users);

        when(domainDao.findByAttr("name", "User")).thenReturn(domains);
        when(resourceDao.findByAttr("domain_id", "1")).thenReturn(resources);
        when(resourceDao.findByAttr("name", "ResourceB")).thenReturn(anotherResources);
        when(mockItemDao.findActiveByAttr("resource_id", "10")).thenReturn(items);
        when(mockItemDao.findActiveByAttr("resource_id", "11")).thenReturn(items);
        when(resourceDao.findByAttr("user_id", "1000")).thenReturn(resources);
    }

*/
}
