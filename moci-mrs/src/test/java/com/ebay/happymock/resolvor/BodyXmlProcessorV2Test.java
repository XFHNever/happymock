package com.ebay.happymock.resolvor;

import com.ebay.happymock.core.entity.MociBody;
import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.processor.BodyXmlProcessorV2;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-10-17
 */
@Test
public class BodyXmlProcessorV2Test {
    @Test
    public void test_can_not_bind_with_empty_xml(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setXml(null);
        mociRequest.setBody(mociBody);
        BodyXmlProcessorV2 bodyXmlProcessor =new BodyXmlProcessorV2();
        Assert.assertFalse(bodyXmlProcessor.bind(mociRequest));
        mociBody.setXml("");
        Assert.assertFalse(bodyXmlProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_not_bind_with_invalid_xml(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setXml("\"<request><parameters><id>1<id></parameters></request>");
        mociRequest.setBody(mociBody);
        BodyXmlProcessorV2 bodyXmlProcessor =new BodyXmlProcessorV2();
        Assert.assertFalse(bodyXmlProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_bind(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setXml("<request><parameters><id>1</id></parameters></request>");
        mociRequest.setBody(mociBody);
        BodyXmlProcessorV2 bodyXmlProcessor =new BodyXmlProcessorV2();
        Assert.assertTrue(bodyXmlProcessor.bind(mociRequest));
    }

    @Test
    void test_can_not_match_with_empty_xml_payload(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setXml("<request><parameters><id>1</id></parameters></request>");
        mociRequest.setBody(mociBody);
        BodyXmlProcessorV2 bodyXmlProcessor =new BodyXmlProcessorV2();
        Assert.assertTrue(bodyXmlProcessor.bind(mociRequest));
        Request request=new Request();
        request.setText(null);
        Assert.assertFalse(bodyXmlProcessor.match(request));

        request.setText("");
        Assert.assertFalse(bodyXmlProcessor.match(request));

    }

    @Test
    void test_can_not_match_with_invalid_xml_payload(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setXml("<request><parameters><id>1</id></parameters></request>");
        mociRequest.setBody(mociBody);
        BodyXmlProcessorV2 bodyXmlProcessor =new BodyXmlProcessorV2();
        Assert.assertTrue(bodyXmlProcessor.bind(mociRequest));
        Request request=new Request();
        request.setText("<request><parameters><id>1<id></parameters></request>");
        Assert.assertFalse(bodyXmlProcessor.match(request));
    }


    @Test
    void test_can_not_match_with_unmatched_xml_payload(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setXml("<request><parameters><id>1</id><pd>1</pd></parameters></request>");
        mociRequest.setBody(mociBody);
        BodyXmlProcessorV2 bodyXmlProcessor =new BodyXmlProcessorV2();
        Assert.assertTrue(bodyXmlProcessor.bind(mociRequest));
        Request request=new Request();
        request.setText("<request><parameters><id>1<id></parameters></request>");
        Assert.assertFalse(bodyXmlProcessor.match(request));
    }

    @Test
    void test_can_match(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setXml("<request><parameters id=\"123\"><id>1</id><pd>1</pd></parameters></request>");
        mociRequest.setBody(mociBody);
        BodyXmlProcessorV2 bodyXmlProcessor =new BodyXmlProcessorV2();
        Assert.assertTrue(bodyXmlProcessor.bind(mociRequest));
        Request request=new Request();
        request.setText("<request><parameters id=\"123\"><id>1</id><pd>1</pd></parameters></request>");
        Assert.assertTrue(bodyXmlProcessor.match(request));
    }
}
