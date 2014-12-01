package com.jcworks.happymock.binder;

import com.jcworks.happymock.core.binder.HeadersBinder;
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
public class HeadersBinderTest {
    @Test
    public void test_can_not_bound() {
        MociResponse mociResponse=new MociResponse();
        mociResponse.setHeaders(null);
        HeadersBinder headersBinder=new HeadersBinder();
        Assert.assertFalse(headersBinder.bind(mociResponse));
    }

    @Test
    public void test_can_not_bound_with_empty_headers_in_request_payload() {
        MociResponse mociResponse=new MociResponse();
        mociResponse.setHeaders(new HashMap());
        HeadersBinder headersBinder=new HeadersBinder();
        Assert.assertFalse(headersBinder.bind(mociResponse));
    }

    @Test
    public void test_can_bind_case(){
        HeadersBinder headersBinder=new HeadersBinder();
        MociResponse mociResponse=new MociResponse();
        //set up headers
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociResponse.setHeaders(headMap);
        Assert.assertTrue(headersBinder.bind(mociResponse));
    }

    @Test
    void test_write(){
        HeadersBinder headersBinder=new HeadersBinder();
        MociResponse mociResponse=new MociResponse();
        //set up headers
        ImmutableMap headMap= ImmutableMap.of("username","jason cui","password","123");
        mociResponse.setHeaders(headMap);
        Assert.assertTrue(headersBinder.bind(mociResponse));
        Response response=new Response();
        headersBinder.write(response);
        Assert.assertEquals(response.getHeaders().size(),2);
    }
}
