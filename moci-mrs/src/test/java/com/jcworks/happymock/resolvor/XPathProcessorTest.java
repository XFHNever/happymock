package com.jcworks.happymock.resolvor;

import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.core.processor.XPathProcessor;
import com.google.common.collect.Maps;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-20
 */
@Test
public class XPathProcessorTest {

    @Test
    void test_can_not_bind_with_empty_settings(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setXpath(null);
        Assert.assertFalse(xPathProcessor.bind(mociRequest));
        Map<String,String> map= Maps.newHashMap();
        mociRequest.setXpath(map);
        Assert.assertFalse(xPathProcessor.bind(mociRequest));
    }

    @Test
    void test_can_bind(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/id/text()","1");
        mociRequest.setXpath(xpath);
        Assert.assertTrue(xPathProcessor.bind(mociRequest));
    }

    @Test
    void test_can_not_match_with_empty_body(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/id[text()='1']","1");///HD/disk/files[text()='1580']
        mociRequest.setXpath(xpath);
        Assert.assertTrue(xPathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("null");
        Assert.assertFalse(xPathProcessor.match(request));
        request.setText(null);
        Assert.assertFalse(xPathProcessor.match(request));
        request.setText("");
        Assert.assertFalse(xPathProcessor.match(request));
    }

    @Test
    void test_can_not_match_with_invalid_xml_body(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/id[text()='1']","1");
        mociRequest.setXpath(xpath);
        Assert.assertTrue(xPathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("<request><parameters><id>1<id><name>jasoncui</name></parameters><parameters><id>1</id><name>jasonzhang</name></parameters></request>");
        Assert.assertFalse(xPathProcessor.match(request));
    }

    @Test
     void test_can_match_with_matched_element_size(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/id[text()='1']","1");
        mociRequest.setXpath(xpath);
        Assert.assertTrue(xPathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("<request><parameters><id>1</id></parameters></request>");
        Assert.assertTrue(xPathProcessor.match(request));
    }

    @Test
    void test_can_match_with_matched_attribute_element_size(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/name[contains(text(),'jason')]","2");///HD/disk/files[text()='1580']
        mociRequest.setXpath(xpath);
        Assert.assertTrue(xPathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("<request><parameters><id>1</id><name>jasoncui</name></parameters><parameters><id>1</id><name>jasonzhang</name></parameters></request>");
        Assert.assertTrue(xPathProcessor.match(request));
    }

    @Test
    void test_can_match_with_matched_attribute_value_size(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/name[@cid='test']","1");///HD/disk/files[text()='1580']
        mociRequest.setXpath(xpath);
        Assert.assertTrue(xPathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("<request><parameters><id>1</id><name cid='test'>jasoncui</name></parameters><parameters><id>1</id><name>jasonzhang</name></parameters></request>");
        Assert.assertTrue(xPathProcessor.match(request));
    }



    /**
     * match multiple expression
     */
    @Test
    public void test_can_match_with_multiple_xpath_expression(){
        XPathProcessor xPathProcessor= new XPathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/name[@cid='test']","1");
        xpath.put("/request/parameters/id[text()='2000']","1");
        mociRequest.setXpath(xpath);
        Assert.assertTrue(xPathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("<request><parameters><id>2000</id><name cid='test'>jasoncui</name></parameters><parameters><id>1</id><name>jasonzhang</name></parameters></request>");
        Assert.assertTrue(xPathProcessor.match(request));//find 2 nodes ,should be fails
    }

}
