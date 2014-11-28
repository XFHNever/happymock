package com.ebay.happymock.persist.dao;

/**
 * Created by fuxie on 2014/9/23  9:50.
 */
public class SResourceDaoImplTest {
    /**
    private ResourceDao resourceDao;
    @BeforeTest
    public void setup() {
        resourceDao = new SResourceDaoImpl();
    }

    @Test
    public void testSaveAndDelete() throws Exception {
        Resource resource = new Resource();
        resource.setName("aaaaa");
        resource.setDomain_id("53fed385302773802d85cebf");
        resourceDao.save(resource);

        List<Resource> resources = resourceDao.findByAttr("name", "aaaaa");
        assertEquals(resources.size(), 1);

        resourceDao.delete(resources.get(0).getId());
        List<Resource> resources2 = resourceDao.findByAttr("name", "aaaaa");
        assertEquals(resources2.size(), 0);
    }

    @Test
    public void testUpdate() throws Exception {
        String id = "54003ef58ec7778c1ad07306";
        Resource oldResource = resourceDao.findById(id);
        assertEquals(oldResource.getName(), "barcode");
        assertEquals(oldResource.getId(), id);
        Resource newResource = new Resource();
        newResource.setId(oldResource.getId());
        newResource.setName(oldResource.getName());
        newResource.setName("Test");
        resourceDao.update(newResource);
        assertEquals(resourceDao.findById(id).getName(), "Test");
        resourceDao.update(oldResource);
        assertEquals(oldResource.getName(), "barcode");
    }

    @Test
    public void testFindAll() throws Exception {
        List<Resource> resources = resourceDao.findAll();
        assertEquals(resources.size(), 15);
    }

    @Test
    public void testFindById() throws Exception {
        Resource resource = resourceDao.findById("54003ef58ec7778c1ad07306");
        assertEquals(resource.getName(), "barcode");
    }
    @Test
    public void testFindByIdWithWrongId() throws Exception {
        Resource resource = resourceDao.findById("63fed4fa302773802d85cec7");
        assertNull(resource);
    }

    @Test
    public void testFindByAttr() throws Exception {
        List<Resource> resources = resourceDao.findByAttr("name", "barcode");
        assertEquals(resources.size(), 1);
    }

    @Test
    public void testFindByAttrWithWrongAttr() throws Exception {
        List<Resource> resources = resourceDao.findByAttr("time", "barcode");
        assertEquals(resources.size(), 0);
    }

    @Test
    public void testFindByAttrWithWrongValue() throws Exception {
        List<Resource> resources = resourceDao.findByAttr("name", "never");
        assertEquals(resources.size(), 0);
    }
    **/
}
