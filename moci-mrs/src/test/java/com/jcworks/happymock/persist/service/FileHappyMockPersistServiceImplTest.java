package com.jcworks.happymock.persist.service;

import com.jcworks.happymock.persist.model.MockItem;
import com.jcworks.happymock.persist.service.impl.FileHappyMockPersistServiceImpl;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

/**
 * User: jicui
 * Date: 14-11-3
 */
@Test
public class FileHappyMockPersistServiceImplTest {
    @Test
    void testfind(){
        String file=System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"java"+File.separator+"com"+File.separator+"jcworks"+File.separator+"happymock"+File.separator+"persist"+File.separator+"service"+File.separator+"moci.dsl";
        HappyMockPersistService  happyMockPersistService=new FileHappyMockPersistServiceImpl(new File(file));
        List<MockItem> mockItemList= happyMockPersistService.find("test");
        Assert.assertNotNull(mockItemList);
    }
}
