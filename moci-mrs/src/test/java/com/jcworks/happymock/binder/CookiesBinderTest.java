package com.jcworks.happymock.binder;

import com.jcworks.happymock.core.binder.CookiesBinder;
import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;
import com.google.common.collect.ImmutableMap;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * User: jicui
 * Date: 14-9-2
 */
@Test
public class CookiesBinderTest {
    @Test
    public void test_can_not_bound() {
        MociResponse mociResponse=new MociResponse();
        mociResponse.setCookies(null);
        CookiesBinder cookiesBinder=new CookiesBinder();
        Assert.assertFalse(cookiesBinder.bind(mociResponse));
    }

    @Test
    public void test_can_not_bound_with_empty_headers_in_request_payload() {
        MociResponse mociResponse=new MociResponse();
        mociResponse.setCookies(new HashMap());
        CookiesBinder cookiesBinder=new CookiesBinder();
        Assert.assertFalse(cookiesBinder.bind(mociResponse));
    }

    @Test
    public void test_can_bind_case(){
        CookiesBinder cookiesBinder=new CookiesBinder();
        MociResponse mociResponse=new MociResponse();
        //set up cookies
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociResponse.setCookies(headMap);
        Assert.assertTrue(cookiesBinder.bind(mociResponse));
    }

    @Test
    void test_write(){
        CookiesBinder cookiesBinder=new CookiesBinder();
        MociResponse mociResponse=new MociResponse();
        //set up headers
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociResponse.setCookies(headMap);
        Assert.assertTrue(cookiesBinder.bind(mociResponse));
        Response response=new Response();
        cookiesBinder.write(response);
        Assert.assertEquals(response.getCookies().size(),2);
    }
}
