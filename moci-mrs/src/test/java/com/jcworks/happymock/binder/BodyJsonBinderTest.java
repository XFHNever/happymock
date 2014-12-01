package com.jcworks.happymock.binder;

import com.jcworks.happymock.core.binder.BodyJsonBinder;
import com.jcworks.happymock.core.entity.MociBody;
import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-9-3
 */
@Test
public class BodyJsonBinderTest {
    @Test
    void test_can_not_bind_with_empty_body() {
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyJsonBinder bodyJsonBinder = new BodyJsonBinder();
        Assert.assertFalse(bodyJsonBinder.bind(mociResponse));
        MociBody mociBody=new MociBody();
        mociBody.setJson(null);
        mociResponse.setBody(mociBody);
        Assert.assertFalse(bodyJsonBinder.bind(mociResponse));
        mociBody.setJson("");
        Assert.assertFalse(bodyJsonBinder.bind(mociResponse));
    }

    /*@Test
    void test_can_not_bind_invalid_json_body() {
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyJsonBinder bodyJsonBinder = new BodyJsonBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setJson("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"pd\":123\n" +
                "               \t\t\t\"rd\":\"aaa\"\n" +
                "               \t\t}}");
        Assert.assertFalse(bodyJsonBinder.bind(mociResponse));
    }*/

    @Test
    void test_can_bind(){
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyJsonBinder bodyJsonBinder = new BodyJsonBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setJson("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"pd\":123,\n" +
                "               \t\t\t\"rd\":\"aaa\"\n" +
                "               \t\t}}");
        Assert.assertTrue(bodyJsonBinder.bind(mociResponse));
    }

    @Test
    void test_can_write(){
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyJsonBinder bodyJsonBinder = new BodyJsonBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setJson("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"pd\":123,\n" +
                "               \t\t\t\"rd\":\"aaa\"\n" +
                "               \t\t}}");
        Assert.assertTrue(bodyJsonBinder.bind(mociResponse));

        Response response=new Response();
        bodyJsonBinder.write(response);
        Assert.assertNotNull(response.getJson());
    }

}
