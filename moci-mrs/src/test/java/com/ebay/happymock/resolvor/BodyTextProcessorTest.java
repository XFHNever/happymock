package com.ebay.happymock.resolvor;

import com.ebay.happymock.core.entity.MociBody;
import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.processor.BodyTextProcessor;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-9-2
 */
@Test
public class BodyTextProcessorTest {

    @Test void test_can_not_bind_with_empty_payload(){
        BodyTextProcessor bodyTextProcessor=new BodyTextProcessor();
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody=new MociBody();
        mociBody.setText("");
        mociRequest.setBody(mociBody);
        Assert.assertFalse(bodyTextProcessor.bind(mociRequest));
        mociBody.setText(null);
        Assert.assertFalse(bodyTextProcessor.bind(mociRequest));
    }

    @Test
    void test_can_bind(){
        BodyTextProcessor bodyTextProcessor=new BodyTextProcessor();
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody=new MociBody();
        mociRequest.setBody(mociBody);
        mociBody.setText("12345");
        Assert.assertTrue(bodyTextProcessor.bind(mociRequest));
    }

    @Test
    void test_can_not_match_with_empty_text(){
        BodyTextProcessor bodyTextProcessor=new BodyTextProcessor();
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody=new MociBody();
        mociRequest.setBody(mociBody);
        mociBody.setText("12345");
        Assert.assertTrue(bodyTextProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText(null);
        Assert.assertFalse(bodyTextProcessor.match(request));
    }

    @Test
    void test_can_match(){
        BodyTextProcessor bodyTextProcessor=new BodyTextProcessor();
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody=new MociBody();
        mociRequest.setBody(mociBody);
        mociBody.setText("12345");
        Assert.assertTrue(bodyTextProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("12345");
        Assert.assertTrue(bodyTextProcessor.match(request));
    }

}
