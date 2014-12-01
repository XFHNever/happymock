package com.jcworks.happymock;

import com.alibaba.fastjson.JSON;
import com.jcworks.happymock.core.binder.TextBinder;
import com.jcworks.happymock.core.entity.*;
import com.jcworks.happymock.core.processor.UriProcessor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-7-31
 */
@Test
public class MociSettingTest {

    @Test
    public void testSerialize(){
        MociSetting mociSetting=new MociSetting();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri("/happy_mock_happy_work/wheeel/");
        Map<String,String> cookies=new HashMap<String,String>();
        cookies.put("login","true");
        cookies.put("security","test123");
        mociRequest.setCookies(cookies);

        Map<String,String> xpath=new HashMap<String,String>();
        xpath.put("/request/parameters/id/text()","1");
        mociRequest.setXpath(xpath);

        Map<String,String> jsonpath=new HashMap<String,String>();
        jsonpath.put("$.request.parameters.id","1");
        mociRequest.setJsonpath(jsonpath);

        MociResponse mociResponse=new MociResponse();
        mociResponse.setText("hello Moci");
        mociSetting.setRequest(mociRequest);
        mociSetting.setResponse(mociResponse);

        MociBody mociBody =new MociBody();
        mociBody.setXml("<request><parameters><id>1</id></parameters></request>");
        mociRequest.setBody(mociBody);
        String str=JSON.toJSONString(mociSetting);
        System.out.println(str);
    }

    @Test
    public void testDeserialize(){
       String jsonStr="{\"request\":{\"cookies\":{\"login\":\"true\",\"security\":\"test123\"},\"text\":{\"xml\":\"<request><parameters><id>1</id></parameters></request>\"},\"uri\":\"/happy_mock_happy_work/wheeel/\",\"xpath\":{\"/request/parameters/id/text()\":\"1\"}},\"response\":{\"text\":\"hello Moci\"}}";
       MociSetting mociSetting=JSON.parseObject(jsonStr,MociSetting.class);
       Assert.assertEquals(mociSetting.getRequest().getUri(),"/happy_mock_happy_work/wheeel/");
       Assert.assertEquals(mociSetting.getResponse().getText(),"hello Moci");
    }

    @Test
    public void testDeserialize1(){
        String jsonStr="{\n" +
                "    \"request\": {\n" +
                "    \t\"uri\": \"/fulfillment/v1/window/**/listing\",\n" +
                "    \t\"headers\": {\n" +
                "            \"Authorization\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"cookies\": {\n" +
                "            \"guid\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"body\":{\n" +
                "               \"json\":{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "    \t\"status\":200,\n" +
                "     \t\"headers\": {\n" +
                "            \"Authorization\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"cookies\": {\n" +
                "            \"guid\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"body\":{\n" +
                "                \"xml\":\"<request><parameters><id>1</id></parameters></request>\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        MociSetting mociSetting=JSON.parseObject(jsonStr,MociSetting.class);
        Assert.assertEquals(mociSetting.getRequest().getUri(),"/fulfillment/v1/window/**/listing");
        //Assert.assertEquals(mociSetting.getResponse().getText(),"hello Moci");
    }

    @Test
    public void testAccept(){
        UriProcessor uriProcessor=new UriProcessor();
        TextBinder textBinder=new TextBinder();
        MociSetting mociSetting=new MociSetting();
        MociRequest mociRequest=new MociRequest();
        MociResponse mociResponse=new MociResponse();
        mociSetting.setRequest(mociRequest);
        mociSetting.setResponse(mociResponse);
        mociRequest.setUri("/fulfillment/window/**/123");
        mociResponse.setText("foo");
        mociSetting.accept(uriProcessor);
        mociSetting.accept(textBinder);

        Request request=new Request();
        request.setUrl("/fulfillment/window/v1/123");

        Request request1=new Request();
        request1.setUrl("/fulfillment/window/v1/foo");

        Assert.assertTrue(mociSetting.match(request));
        Assert.assertFalse(mociSetting.match(request1));

        Response response=new Response();
        if(mociSetting.match(request)){
            mociSetting.write(response);
        }

        Assert.assertEquals("foo",response.getText());
    }

}
