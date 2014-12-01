package com.jcworks.happymock.resolvor;

import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.core.processor.JsonpathProcessor;
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
public class JsonpathProcessorTest {

    @Test
    void test_can_not_bind_with_empty_setting(){
        JsonpathProcessor jsonpathProcessor=new JsonpathProcessor();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setJsonpath(null);
        Assert.assertFalse(jsonpathProcessor.bind(mociRequest));
        Map<String,String> map=Maps.newHashMap();
        mociRequest.setJsonpath(map);
        Assert.assertFalse(jsonpathProcessor.bind(mociRequest));
    }

    @Test
    void test_can_bind(){
        JsonpathProcessor jsonpathProcessor=new JsonpathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> jsonpath=new HashMap<String,String>();
        jsonpath.put("$.request.parameters.id","1");
        mociRequest.setJsonpath(jsonpath);
        Assert.assertTrue(jsonpathProcessor.bind(mociRequest));
    }

    @Test
    void test_can_not_match_with_empty_json_payload(){
        JsonpathProcessor jsonpathProcessor=new JsonpathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> jsonpath=new HashMap<String,String>();
        jsonpath.put("$.request.parameters.id","1");
        mociRequest.setJsonpath(jsonpath);
        Assert.assertTrue(jsonpathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("");
        Assert.assertFalse(jsonpathProcessor.match(request));
    }

    @Test
    void test_can_not_match_with_invalid_json_payload(){
        JsonpathProcessor jsonpathProcessor=new JsonpathProcessor();
        MociRequest mociRequest=new MociRequest();
        Map<String,String> jsonpath=new HashMap<String,String>();
        jsonpath.put("$.request.parameters.id","1");
        mociRequest.setJsonpath(jsonpath);
        Assert.assertTrue(jsonpathProcessor.bind(mociRequest));

        Request request=new Request();
        request.setText("{\"request\":{\"parameters\"={\"id\":\"1\"}}}");
        Assert.assertFalse(jsonpathProcessor.match(request));
    }

    @Test
    void test_can_match_with_expected_element_size(){
        JsonpathProcessor jsonpathProcessor=new JsonpathProcessor();
        Request request=new Request();
        request.setText("{\n" +
                "  \"orderId\" : 166941150,\n" +
                "  \"ticketSeat\" : [ {\n" +
                "    \"row\" : \"e\",\n" +
                "    \"seat\" : \"11\",\n" +
                "    \"barcode\" : \"37917046628213\",\n" +
                "    \"type\" : \"R\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"row\" : \"e\",\n" +
                "    \"seat\" : \"12\",\n" +
                "    \"barcode\" : \"379170466282111\",\n" +
                "    \"type\" : \"R\"\n" +
                "  }]             \n" +
                "}\n" +
                "\n");
        MociRequest mociRequest=new MociRequest();
        Map<String,String> jsonpath=new HashMap<String,String>();
        jsonpath.put("$.ticketSeat[?(@.row=='e')]","2");
        mociRequest.setJsonpath(jsonpath);
        jsonpathProcessor.bind(mociRequest);
        Assert.assertTrue(jsonpathProcessor.match(request));
    }

    @Test
    void test_can_match_with_multiple_jsonpath_expression(){
        JsonpathProcessor jsonpathProcessor=new JsonpathProcessor();
        Request request=new Request();
        request.setText("{\n" +
                "  \"orderId\" : 170231159,\n" +
                "  \"ticketSeat\" : [ {\n" +
                "    \"row\" : \"3\",\n" +
                "    \"seat\" : \"1\",\n" +
                "    \"barcode\" : \"37917046638213\",\n" +
                "    \"type\" : \"R\"\n" +
                "  },\n" +
                " {\n" +
                "    \"row\" : \"3\",\n" +
                "    \"seat\" : \"1\",\n" +
                "    \"barcode\" : \"37917046638213\",\n" +
                "    \"type\" : \"R\"\n" +
                "  }]\n" +
                "}");
        MociRequest mociRequest=new MociRequest();
        Map<String,String> jsonpath=new HashMap<String,String>();
        jsonpath.put("$.ticketSeat[*]","2");
        jsonpath.put("$.ticketSeat","2"); //"$.ticketSeat":"2"
        mociRequest.setJsonpath(jsonpath);
        jsonpathProcessor.bind(mociRequest);

        Assert.assertTrue(jsonpathProcessor.match(request));
    }

}
