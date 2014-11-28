package com.ebay.happymock;

import com.ebay.happymock.core.entity.RequestBodyWrapper;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-10-20
 */
@Test
public class RequestBodyWrapperTest {

    @Test
    public void test_isJson() {
        RequestBodyWrapper requestBodyWrapper = new RequestBodyWrapper("{foo:123,bar:345}");
        Assert.assertTrue(requestBodyWrapper.isJson());
    }

    @Test
    public void test_isXml() {
        RequestBodyWrapper requestBodyWrapper = new RequestBodyWrapper("<?xml version=\"1.0\"?><ele><foo>123</foo><bar>345</bar></ele>");
        Assert.assertFalse(requestBodyWrapper.isJson());
        Assert.assertTrue(requestBodyWrapper.isXml());
    }

    @Test
    public void test_xpath(){
        RequestBodyWrapper requestBodyWrapper = new RequestBodyWrapper("<?xml version=\"1.0\"?><ele><foo>123</foo><bar>345</bar></ele>");
        String rtVal=requestBodyWrapper.getXmlValue("/ele/foo/text()");
        Assert.assertEquals("123",rtVal);
    }

    @Test
    public void test_jsonpath(){
        RequestBodyWrapper requestBodyWrapper = new RequestBodyWrapper("{foo:123,bar:345}");
        String rtVal=requestBodyWrapper.getJsonValue("$.bar");
        Assert.assertEquals("345",rtVal);
    }

    @Test
    public void test_empty_content(){
        RequestBodyWrapper requestBodyWrapper = new RequestBodyWrapper(null);
        String rtVal=requestBodyWrapper.getJsonValue("$.bar");
        String r = "#INVALID_JSON#";
        Assert.assertEquals(null,rtVal);
    }
}
