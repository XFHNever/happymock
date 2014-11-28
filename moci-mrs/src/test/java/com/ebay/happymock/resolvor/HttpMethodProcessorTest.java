package com.ebay.happymock.resolvor;

import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.processor.HttpMethodProcessor;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-9-2
 */
@Test
public class HttpMethodProcessorTest {

    @Test
    void test_can_not_bind_with_empty_method() {
        MociRequest mociRequest=new MociRequest();
        mociRequest.setMethod(null);
        HttpMethodProcessor httpMethodProcessor=new HttpMethodProcessor();
        Assert.assertFalse(httpMethodProcessor.bind(mociRequest));
        mociRequest.setMethod("");
        Assert.assertFalse(httpMethodProcessor.bind(mociRequest));
    }

    @Test
    void test_can_bind() {
        MociRequest mociRequest=new MociRequest();
        mociRequest.setMethod(null);
        HttpMethodProcessor httpMethodProcessor=new HttpMethodProcessor();
        Assert.assertFalse(httpMethodProcessor.bind(mociRequest));
        mociRequest.setMethod("gEt");
        Assert.assertTrue(httpMethodProcessor.bind(mociRequest));
    }

    @Test
    void test_can_match(){
        MociRequest mociRequest=new MociRequest();
        mociRequest.setMethod(null);
        HttpMethodProcessor httpMethodProcessor=new HttpMethodProcessor();
        Assert.assertFalse(httpMethodProcessor.bind(mociRequest));
        mociRequest.setMethod("get");
        Assert.assertTrue(httpMethodProcessor.bind(mociRequest));

        Request request=new Request();
        request.setMethod("GET");
        Assert.assertTrue(httpMethodProcessor.match(request));
    }


}
