package com.ebay.happymock.binder;

import com.ebay.happymock.core.binder.BodyTextBinder;
import com.ebay.happymock.core.binder.BodyXmlBinder;
import com.ebay.happymock.core.entity.MociBody;
import com.ebay.happymock.core.entity.MociResponse;
import com.ebay.happymock.core.entity.Response;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-9-3
 */
@Test
public class BodyTextBinderTest {
    @Test
    void test_can_not_bind_with_empty_body() {
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyTextBinder bodyTextBinder = new BodyTextBinder();
        Assert.assertFalse(bodyTextBinder.bind(mociResponse));
        MociBody mociBody=new MociBody();
        mociBody.setText(null);
        mociResponse.setBody(mociBody);
        Assert.assertFalse(bodyTextBinder.bind(mociResponse));
        mociBody.setText("");
        Assert.assertFalse(bodyTextBinder.bind(mociResponse));
    }

    @Test
    void test_can_bind(){
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyTextBinder bodyTextBinder = new BodyTextBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setText("12345");
        Assert.assertTrue(bodyTextBinder.bind(mociResponse));
    }

    @Test
    void test_can_write(){
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyTextBinder bodyTextBinder = new BodyTextBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setText("text");
        Assert.assertTrue(bodyTextBinder.bind(mociResponse));

        Response response=new Response();
        bodyTextBinder.write(response);
        Assert.assertNotNull(response.getText());
    }

}
