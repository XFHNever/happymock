package com.jcworks.happymock.binder;

import com.jcworks.happymock.core.binder.TextBinder;
import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-9-2
 */
@Test
public class TextBinderTest {

    @Test
    void test_can_not_bind(){
        TextBinder textBinder=new TextBinder();
        MociResponse response=new MociResponse();
        response.setText(null);
        Assert.assertFalse(textBinder.bind(response));
    }

    @Test
    void test_can_bind(){
        TextBinder textBinder=new TextBinder();
        MociResponse response=new MociResponse();
        response.setText("abcd");
        Assert.assertTrue(textBinder.bind(response));
    }

    @Test
    void test_write(){
        TextBinder textBinder=new TextBinder();
        MociResponse mociResponse=new MociResponse();
        mociResponse.setText("abcd");
        textBinder.bind(mociResponse);
        Response response =new Response();
        textBinder.write(response);
        Assert.assertEquals(response.getText(),"abcd");
    }
}
