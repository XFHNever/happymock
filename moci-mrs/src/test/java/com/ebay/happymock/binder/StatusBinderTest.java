package com.ebay.happymock.binder;

import com.ebay.happymock.core.binder.StatusBinder;
import com.ebay.happymock.core.entity.MociResponse;
import com.ebay.happymock.core.entity.Response;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-9-2
 */
@Test
public class StatusBinderTest {
    @Test
    void test_can_not_bind(){
        StatusBinder statusBinder=new StatusBinder();
        MociResponse response=new MociResponse();
        response.setStatus(null);
        Assert.assertFalse(statusBinder.bind(response));
    }

    @Test
    void test_can_bind(){
        StatusBinder statusBinder=new StatusBinder();
        MociResponse response=new MociResponse();
        response.setStatus(200);
        Assert.assertTrue(statusBinder.bind(response));
    }

    @Test
    void test_write(){
        StatusBinder statusBinder=new StatusBinder();
        MociResponse mociResponse=new MociResponse();
        mociResponse.setStatus(200);
        statusBinder.bind(mociResponse);
        Response response =new Response();
        statusBinder.write(response);
        Assert.assertEquals(response.getCode(),200);
    }
}
