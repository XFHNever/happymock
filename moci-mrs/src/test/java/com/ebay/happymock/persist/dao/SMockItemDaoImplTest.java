package com.ebay.happymock.persist.dao;

/**
 * Created by fuxie on 2014/9/23  11:08.
 */
public class SMockItemDaoImplTest {
    /**
    private MockItemDao mockItemDao;
    @BeforeTest
    public void setup() {
        mockItemDao = new SMockItemDaoImpl();
    }

    @Test
    public void testSaveAndDelete() throws Exception {
        MockItem mockItem = new MockItem();
        mockItem.setName("aaaaa");
        mockItem.setActive(true);
        mockItem.setContent("{\\n  \\\"request\\\": {\\n    \\\"uri\\\": \\\"/foo\\\"\\n  },\\n  \\\"response\\\": {\\n    \\\"text\\\": \\\"bar\\\"\\n  }\\n}");
        mockItem.setDescription("test");
        mockItem.setResource_id("63fed4fa302773802d85cec7");
        mockItemDao.save(mockItem);

        List<MockItem> mockItems = mockItemDao.findByAttr("name", "aaaaa");
        assertEquals(mockItems.size(), 1);

        mockItemDao.delete(mockItems.get(0).getId());
        List<MockItem> mockItems2 = mockItemDao.findByAttr("name", "aaaaa");
        assertEquals(mockItems2.size(), 0);
    }

    @Test
    public void testUpdate() throws Exception {
        String id = "53ffe9122c4e82842d8e072a";
        MockItem oldMockItem = mockItemDao.findById(id);
        assertEquals(oldMockItem.getName(), "Item");
        assertEquals(oldMockItem.getId(), id);
        MockItem newMockItem = new MockItem();
        newMockItem.setId(oldMockItem.getId());
        newMockItem.setName(oldMockItem.getName());
        newMockItem.setName("Test");
        newMockItem.setContent(oldMockItem.getContent());
        newMockItem.setResource_id(oldMockItem.getResource_id());
        newMockItem.setDescription(oldMockItem.getDescription());
        newMockItem.setActive(oldMockItem.isActive());
        mockItemDao.update(newMockItem);
        assertEquals(mockItemDao.findById(id).getName(), "Test");
        mockItemDao.update(oldMockItem);
        assertEquals(oldMockItem.getName(), "Item");
    }

    @Test
    public void testFindAll() throws Exception {
        List<MockItem> mockItems = mockItemDao.findAll();
        assertEquals(mockItems.size(), 9);
    }

    @Test
    public void testFindById() throws Exception {
        MockItem mockItem = mockItemDao.findById("53ffe9122c4e82842d8e072a");
        assertEquals(mockItem.getName(), "Item");
    }
    @Test
    public void testFindByIdWithWrongId() throws Exception {
        MockItem mockItem = mockItemDao.findById("63fed4fa302773802d85cec7");
        assertNull(mockItem);
    }

    @Test
    public void testFindByAttr() throws Exception {
        List<MockItem> mockItems = mockItemDao.findByAttr("name", "SearchInventoryAPIV2");
        assertEquals(mockItems.size(), 1);
    }

    @Test
    public void testFindAllActive() throws Exception {
        List<MockItem> mockItems = mockItemDao.findByAttr("active", true);
        assertEquals(mockItems.size(), 7);
    }

    @Test
    public void testFindByAttrWithWrongAttr() throws Exception {
        List<MockItem> mockItems = mockItemDao.findByAttr("time", "SearchInventoryAPIV2");
        assertEquals(mockItems.size(), 0);
    }

    @Test
    public void testFindByAttrWithWrongValue() throws Exception {
        List<MockItem> mockItems = mockItemDao.findByAttr("name", "never");
        assertEquals(mockItems.size(), 0);
    }
    **/
}
