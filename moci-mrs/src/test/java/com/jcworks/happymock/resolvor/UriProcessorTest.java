package com.jcworks.happymock.resolvor;

import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.core.processor.UriProcessor;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-7-31
 */
@Test
public class UriProcessorTest {

    @Test
    public void test_can_not_bind(){
        UriProcessor uriProcessor=new UriProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri(null);
        //uri is null,thus can not bind
        Assert.assertFalse(uriProcessor.bind(mociRequest));
        //uri is empty string
        MociRequest mociRequest1=new MociRequest();
        mociRequest.setUri("");
        Assert.assertFalse(uriProcessor.bind(mociRequest1));
    }

    @Test
    public void test_can_bind(){
        UriProcessor uriProcessor=new UriProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri("/fulfillment/barcode/v1/**/12?");
        Assert.assertTrue(uriProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_not_match(){
        UriProcessor uriProcessor=new UriProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri("/fulfillment/barcode/v1/**/12?");
        Assert.assertTrue(uriProcessor.bind(mociRequest));

        Request request1=new Request();
        request1.setUrl(null);
        Assert.assertFalse(uriProcessor.match(request1));

        Request request2=new Request();
        request2.setUrl("/fulfillment/barcode/v1/controlcode/1234");//does not match the specs
        Assert.assertFalse(uriProcessor.match(request2));
    }

    @Test
    public void test_can_not_match_with_plan_url_specs(){
        UriProcessor uriProcessor=new UriProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri("/fulfillment/barcode/v1/control/123");
        Assert.assertTrue(uriProcessor.bind(mociRequest));

        Request request1=new Request();
        request1.setUrl("/fulfillment/barcode/v1/control/1234");
        Assert.assertFalse(uriProcessor.match(request1));
    }

    @Test
    public void test_can_match(){
        UriProcessor uriProcessor=new UriProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri("/fulfillment/barcode/v1/**/12?");
        Assert.assertTrue(uriProcessor.bind(mociRequest));
        Request request1=new Request();
        request1.setUrl("/fulfillment/barcode/v1/control/123");
        Assert.assertTrue(uriProcessor.match(request1));
    }

    @Test
    public void test_can_match_with_question_mark(){
        UriProcessor uriProcessor=new UriProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri("/fulfillment/v1/window/**/listing/?eventid=123&pricemin=100");
        Assert.assertTrue(uriProcessor.bind(mociRequest));
        Request request1=new Request();
        request1.setUrl("/fulfillment/v1/window/1234/listing/?eventid=123&pricemin=100");
        Assert.assertTrue(uriProcessor.match(request1));
    }

}
