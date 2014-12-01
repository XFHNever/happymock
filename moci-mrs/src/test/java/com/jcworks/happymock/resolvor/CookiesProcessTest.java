package com.jcworks.happymock.resolvor;

import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.core.processor.CookiesProcessor;
import com.google.common.collect.ImmutableMap;
import junit.framework.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;

/**
 * User: jicui
 * Date: 14-8-12
 */
@Test
public class CookiesProcessTest {
    @Test
    public void test_can_not_bound() {
        MociRequest mociRequest=new MociRequest();
        mociRequest.setCookies(null);
        CookiesProcessor cookiesProcessor=new CookiesProcessor();
        Assert.assertFalse(cookiesProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_not_bound_with_empty_cookies_in_request_payload() {
        MociRequest mociRequest=new MociRequest();
        mociRequest.setHeaders(new HashMap());
        CookiesProcessor cookiesProcessor=new CookiesProcessor();
        Assert.assertFalse(cookiesProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_bind_case(){
        CookiesProcessor cookiesProcessor=new CookiesProcessor();
        MociRequest mociRequest=new MociRequest();
        //set up headers
        ImmutableMap cookiesMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setCookies(cookiesMap);
        Assert.assertTrue(cookiesProcessor.bind(mociRequest));
    }


    @Test
    public void test_can_not_match_with_empty_cookies_in_request_payload(){

        //bind first
        MociRequest mociRequest=new MociRequest();
        //set up headers
        ImmutableMap cookieMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setCookies(cookieMap);
        CookiesProcessor cookiesProcessor=new CookiesProcessor();
        Assert.assertTrue(cookiesProcessor.bind(mociRequest));

        //request1  with null cookies
        Request request1=new Request();
        request1.setCookies(null);
        Assert.assertFalse(cookiesProcessor.match(request1));

        //request2 with empty cookies
        Request request2=new Request();
        request2.setCookies(new HashMap());
        Assert.assertFalse(cookiesProcessor.match(request2));
    }

    @Test
    public void test_can_not_match_with_mismatched_headers_in_request_payload(){

        //bind first
        MociRequest mociRequest=new MociRequest();
        //set up cookies
        ImmutableMap cookieMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setCookies(cookieMap);
        CookiesProcessor cookiesProcessor=new CookiesProcessor();
        Assert.assertTrue(cookiesProcessor.bind(mociRequest));

        //request1 with mismatched username value
        Request request1=new Request();
        request1.setCookies(ImmutableMap.of("username","jason cui1","password","123")); //jason cui1 is mismatched
        Assert.assertFalse(cookiesProcessor.match(request1));

        //request2 with mismatched username value
        Request request2=new Request();
        request2.setCookies(ImmutableMap.of("password","123")); //only one cookie ,but expected two,thus return false
        Assert.assertFalse(cookiesProcessor.match(request2));
    }

    @Test
    public void test_can_match_case(){
        //bind first
        MociRequest mociRequest=new MociRequest();
        //set up headers
        ImmutableMap cookieMap= ImmutableMap.of("username","jason cui","password","123");
        mociRequest.setCookies(cookieMap);
        CookiesProcessor cookiesProcessor=new CookiesProcessor();
        Assert.assertTrue(cookiesProcessor.bind(mociRequest));

        //request1 with mismatched username value
        Request request=new Request();
        request.setCookies(ImmutableMap.of("username","jason cui","password","123")); //jason cui1 is mismatched
        Assert.assertTrue(cookiesProcessor.match(request));
    }
}
