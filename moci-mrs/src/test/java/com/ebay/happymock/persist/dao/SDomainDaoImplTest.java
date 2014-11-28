package com.ebay.happymock.persist.dao;

/**
 * Created by fuxie on 2014/9/22  16:07.
 */
public class SDomainDaoImplTest {
    /**
    private DomainDao domainDao; 
    @BeforeTest
    public void setUp() {
        domainDao = new SDomainDaoImpl();
    }

    @Test
    public void testSaveAndDelete() throws Exception {
        Domain domain = new Domain("aaaaa");
        domainDao.save(domain);

        List<Domain> domains = domainDao.findByAttr("name", "aaaaa");
        assertEquals(domains.size(), 1);

        domainDao.delete(domains.get(0).getId());
        List<Domain> domains2 = domainDao.findByAttr("name", "aaaaa");
        assertEquals(domains2.size(), 0);
    }

    @Test
    public void testUpdate() throws Exception {
        String id = "5420ccacd07c6e84241e2faf";
        Domain oldDomain = domainDao.findById(id);
        assertEquals(oldDomain.getName(), "Account Management");
        assertEquals(oldDomain.getId(), id);
        Domain newDomain = new Domain();
        newDomain.setId(oldDomain.getId());
        newDomain.setName(oldDomain.getName());
        newDomain.setName("Test");
        domainDao.update(newDomain);
        assertEquals(domainDao.findById(id).getName(), "Test");
        domainDao.update(oldDomain);
        assertEquals(oldDomain.getName(), "Account Management");
    }

    @Test
    public void testFindAll() throws Exception {
        List<Domain> domains = domainDao.findAll();
        assertEquals(domains.size(), 19);
    }

    @Test
    public void testFindById() throws Exception {
        Domain domain = domainDao.findById("5420ccacd07c6e84241e2faf");
        assertEquals(domain.getName(), "Account Management");
    }
    @Test
    public void testFindByIdWithWrongId() throws Exception {
        Domain domain = domainDao.findById("63fed4fa302773802d85cec7");
        assertNull(domain);
    }

    @Test
    public void testFindByAttr() throws Exception {
        List<Domain> domains = domainDao.findByAttr("name", "User");
        assertEquals(domains.size(), 1);
    }

    @Test
    public void testFindByAttrWithWrongAttr() throws Exception {
        List<Domain> domains = domainDao.findByAttr("time", "User");
        assertEquals(domains.size(), 0);
    }

    @Test
    public void testFindByAttrWithWrongValue() throws Exception {
        List<Domain> domains = domainDao.findByAttr("name", "never");
        assertEquals(domains.size(), 0);
    }
    **/
}
