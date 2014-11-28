package com.ebay.happymock.resolvor;

import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.processor.HeadersProcessor;
import com.google.common.collect.ImmutableMap;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * User: jicui
 * Date: 14-9-1
 */
@Test
public class HeadersProcessTest {

    @Test
    public void test_can_not_bound() {
        MociRequest mociRequest=new MociRequest();
        mociRequest.setHeaders(null);
        HeadersProcessor headersProcessor=new HeadersProcessor();
        Assert.assertFalse(headersProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_not_bound_with_empty_headers_in_request_payload() {
        MociRequest mociRequest=new MociRequest();
        mociRequest.setHeaders(new HashMap());
        HeadersProcessor headersProcessor=new HeadersProcessor();
        Assert.assertFalse(headersProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_bind_case(){
        HeadersProcessor headersProcessor=new HeadersProcessor();
        MociRequest mociRequest=new MociRequest();
        //set up headers
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setHeaders(headMap);
        Assert.assertTrue(headersProcessor.bind(mociRequest));
    }


    @Test
    public void test_can_not_match_with_empty_headers_in_request_payload(){

        //bind first
        MociRequest mociRequest=new MociRequest();
        //set up headers
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setHeaders(headMap);
        HeadersProcessor headersProcessor=new HeadersProcessor();
        Assert.assertTrue(headersProcessor.bind(mociRequest));

        //request1  with null headers
        Request request1=new Request();
        request1.setHeaders(null);
        Assert.assertFalse(headersProcessor.match(request1));

        //request2 with null headers
        Request request2=new Request();
        request2.setHeaders(new HashMap());
        Assert.assertFalse(headersProcessor.match(request2));
    }

    @Test
    public void test_can_not_match_with_mismatched_headers_in_request_payload(){

        //bind first
        MociRequest mociRequest=new MociRequest();
        //set up headers
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setHeaders(headMap);
        HeadersProcessor headersProcessor=new HeadersProcessor();
        Assert.assertTrue(headersProcessor.bind(mociRequest));

        //request1 with mismatched username value
        Request request1=new Request();
        request1.setHeaders(ImmutableMap.of("username","jason cui1","password","123")); //jason cui1 is mismatched
        Assert.assertFalse(headersProcessor.match(request1));

        //request2 with mismatched username value
        Request request2=new Request();
        request2.setHeaders(ImmutableMap.of("password","123")); //only one headers ,but expected two,thus return false
        Assert.assertFalse(headersProcessor.match(request2));
    }

    @Test
    public void test_can_match_case(){
        //bind first
        MociRequest mociRequest=new MociRequest();
        //set up headers
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setHeaders(headMap);
        HeadersProcessor headersProcessor=new HeadersProcessor();
        Assert.assertTrue(headersProcessor.bind(mociRequest));

        //request1 with mismatched username value
        Request request1=new Request();
        request1.setHeaders(ImmutableMap.of("username","jason cui","password","123")); //jason cui1 is mismatched
        Assert.assertTrue(headersProcessor.match(request1));
    }
}
