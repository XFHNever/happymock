package com.ebay.happymock;

import com.ebay.happymock.core.entity.Request;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import ognl.Ognl;
import ognl.OgnlException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jicui on 10/18/14.
 */
@Test
public class OGNLTEST {

    @Test
    void test_normal_property_access() throws OgnlException {
        Request request=new Request();
        Map<String,String> headers=new HashMap<String,String>();
        headers.put("h1","h1value");
        headers.put("h2","h2value");
        request.setHeaders(headers);
        request.setMethod("GET");
        String value=(String)Ognl.getValue("method",request);
        Assert.assertEquals(value, "GET");
    }

    @Test
    void test_uri_property_access() throws OgnlException {
        Request request=new Request();
        request.setUri("/aaa/bbb/ccc");
        String value=(String)Ognl.getValue("uri[1]",request);
        Assert.assertEquals(value, "aaa");
    }

    @Test
    void test_queryparam_property_access() throws OgnlException {
        Request request=new Request();
        Map<String,String> queryParams=new HashMap<String,String>();
        queryParams.put("foo","foovalue");
        queryParams.put("bar","barvalue");
        request.setQueryParam(queryParams);
        request.setMethod("GET");
        String value=(String)Ognl.getValue("query['foo']",request);
        Assert.assertEquals(value, "foovalue");
        String value1=(String)Ognl.getValue("query['bar']",request);
        Assert.assertEquals(value1, "barvalue");
    }

    @Test
    void test_map_property_access() throws OgnlException {
        Request request=new Request();
        ImmutableMap<String,String> headers= ImmutableMap.of("h1", "h1value", "h2", "h2value");
        request.setHeaders(headers);
        request.setMethod("GET");
        String value=(String)Ognl.getValue("getHeader('h1')",request);
        Assert.assertEquals(value, "h1value");
        String value1=(String)Ognl.getValue("header['h1']",request);
        Assert.assertEquals(value1, "h1value");
        String value2=(String)Ognl.getValue("header['h2']",request);
        Assert.assertEquals(value2, "h2value");
        //Assert.assertEquals("header.h2","h2value");
    }

    @Test
    void test_json_property_access() throws OgnlException {
        Request request=new Request();
        ImmutableMap<String,String> headers= ImmutableMap.of("h1", "h1value", "h2", "h2value");
        request.setHeaders(headers);
        request.setMethod("GET");
        request.setText(TestUtility.getTestJson());
        String value=(String)Ognl.getValue("json['$.store.book[0].author']",request);
        Assert.assertEquals(value, "Nigel Rees");
        String value1=(String)Ognl.getValue("json['$.store.book[0,1].author']",request);
        Assert.assertEquals(value1, "[\"Nigel Rees\",\"Evelyn Waugh\"]");
    }

    @Test
    void test_xml_property_access() throws OgnlException {
        Request request=new Request();
        ImmutableMap<String,String> headers= ImmutableMap.of("h1", "h1value", "h2", "h2value");
        request.setHeaders(headers);
        request.setMethod("GET");
        request.setText(TestUtility.getTestXml());
        String value=(String)Ognl.getValue("xml['/bookstore/book[price>40]/title/text()']",request);
        Assert.assertEquals(value, "XQuery Kick Start");
        String value1=(String)Ognl.getValue("xml['//bookstore/book[price<40]/title']",request);
        Assert.assertEquals(value1, "Everyday Italian");
        String value2=(String)Ognl.getValue("xml['//bookstore/book[author=\"Erik T. Ray\"]/title/text()']",request);
        Assert.assertEquals(value2, "Learning XML");
    }

    @Test
    void test_operation() throws OgnlException {
        Request request=new Request();
        ImmutableMap<String,String> headers= ImmutableMap.of("h1", "h1value", "h2", "h2value");
        request.setHeaders(headers);
        request.setMethod("GET");
        request.setText(TestUtility.getTestXml());
        String value=(String)Ognl.getValue("xml['/bookstore/book[price>40]/title/text()']+'--'+xml['//bookstore/book[author=\"Erik T. Ray\"]/title/text()']",request);
        Assert.assertEquals("XQuery Kick Start--Learning XML",value);
        String value1=(String)Ognl.getValue("xml['/bookstore/book[price>40]/price']+100",request);
        Assert.assertEquals("49.99100",value1);
    }



}
