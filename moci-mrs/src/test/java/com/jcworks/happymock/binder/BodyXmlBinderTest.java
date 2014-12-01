package com.jcworks.happymock.binder;

import com.jcworks.happymock.core.binder.BodyXmlBinder;
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
public class BodyXmlBinderTest {
    @Test
    void test_can_not_bind_with_empty_body() {
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyXmlBinder bodyXmlBinder = new BodyXmlBinder();
        Assert.assertFalse(bodyXmlBinder.bind(mociResponse));
        MociBody mociBody=new MociBody();
        mociBody.setXml(null);
        mociResponse.setBody(mociBody);
        Assert.assertFalse(bodyXmlBinder.bind(mociResponse));
        mociBody.setXml("");
        Assert.assertFalse(bodyXmlBinder.bind(mociResponse));
    }

    /*@Test
    void test_can_not_bind_invalid_json_body() {
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyXmlBinder BodyXmlBinder = new BodyXmlBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setJson("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"pd\":123\n" +
                "               \t\t\t\"rd\":\"aaa\"\n" +
                "               \t\t}}");
        Assert.assertFalse(BodyXmlBinder.bind(mociResponse));
    }*/

    @Test
    void test_can_bind(){
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyXmlBinder bodyXmlBinder = new BodyXmlBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setXml("\"xml\":\"<request><parameters><id>1</id></parameters></request>\"");
        Assert.assertTrue(bodyXmlBinder.bind(mociResponse));
    }

    @Test
    void test_can_write(){
        MociResponse mociResponse = new MociResponse();
        mociResponse.setBody(null);
        BodyXmlBinder bodyXmlBinder = new BodyXmlBinder();
        MociBody mociBody=new MociBody();
        mociResponse.setBody(mociBody);
        mociBody.setXml("\"xml\":\"<request><parameters><id>1</id></parameters></request>\"");
        Assert.assertTrue(bodyXmlBinder.bind(mociResponse));

        Response response=new Response();
        bodyXmlBinder.write(response);
        Assert.assertNotNull(response.getXml());
    }

}
