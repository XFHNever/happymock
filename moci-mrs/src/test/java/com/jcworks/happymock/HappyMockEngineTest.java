package com.jcworks.happymock;

import com.jcworks.happymock.core.compiling.v1.RootCompiler;
import com.jcworks.happymock.core.entity.Response;
import com.jcworks.happymock.persist.model.MongoProperties;
import com.jcworks.happymock.persist.service.impl.FileHappyMockPersistServiceImpl;
import com.jcworks.happymock.persist.service.impl.MongoHappyMockPersistServiceImpl;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static com.jcworks.happymock.core.HappyMockEngine.HappyMockEngineBuilder.happyMockEngine;
import static com.jcworks.happymock.core.entity.Request.RequestBuilder.newRequest;

/**
 * User: jicui
 * Date: 14-11-3
 */
@Test
public class HappyMockEngineTest {
    @BeforeTest
    public void setUp() {
        MongoProperties.init();
    }
    @Test
    void testBuild() {
        try {
           happyMockEngine().withCompilerTree(null).build();
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
        try {
            happyMockEngine().withPersistAdaptor(null).build();
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
        Assert.assertNotNull(happyMockEngine().withPersistAdaptor(new MongoHappyMockPersistServiceImpl()).withCompilerTree(new RootCompiler()).build());
        Assert.assertNotNull(happyMockEngine().build());
    }

    @Test
    void testCompile() throws Exception {
       String dsl="{\"request\":{\"uri\":\"/123\"},\"response\":{\"status\":\"200\"}}";
       Assert.assertEquals(happyMockEngine().build().compile(dsl).getCode(),200);

       String dsl_error="{\"request\":{\"uri\":\"/123\"},\"response1\":{\"status\":\"200\"}}";
       Assert.assertEquals(happyMockEngine().build().compile(dsl_error).getCode(),400);
    }

    @Test
    void testMock() throws Exception {
        String file=System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"java"+File.separator+"com"+File.separator+"jcworks"+File.separator+"happymock"+File.separator+"persist"+File.separator+"service"+File.separator+"moci.dsl";
        Response response=happyMockEngine().withPersistAdaptor(new FileHappyMockPersistServiceImpl(new File(file))).build().mock(newRequest().withURL("/jicui/3rdparty/ups/ship").withURI("/jicui/3rdparty/ups/ship").withMethod("POST").build());
        Assert.assertEquals(response.getHeaders().get("Content-Type"),"text/xml");
    }
}
