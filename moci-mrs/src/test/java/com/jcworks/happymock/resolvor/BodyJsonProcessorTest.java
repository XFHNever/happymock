package com.jcworks.happymock.resolvor;

import com.jcworks.happymock.core.entity.MociBody;
import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.core.processor.BodyJsonProcessor;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-15
 */
@Test
public class BodyJsonProcessorTest {

    @Test
    public void test_can_not_bind_with_empty_string(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setJson(null);
        mociRequest.setBody(mociBody);
        BodyJsonProcessor bodyJsonProcessor =new BodyJsonProcessor();
        Assert.assertFalse(bodyJsonProcessor.bind(mociRequest));

        mociBody.setJson("");
        Assert.assertFalse(bodyJsonProcessor.bind(mociRequest));
    }
    @Test
    public void test_can_not_bind_with_invalid_json_format(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setJson("{\"foo\":\"bar\"\"foo\":\"bar\"}");
        mociRequest.setBody(mociBody);
        BodyJsonProcessor bodyJsonProcessor =new BodyJsonProcessor();
        Assert.assertFalse(bodyJsonProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_bind(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setJson("{\"foo\":\"bar\"}");
        mociRequest.setBody(mociBody);
        BodyJsonProcessor bodyJsonProcessor =new BodyJsonProcessor();
        Assert.assertTrue(bodyJsonProcessor.bind(mociRequest));
    }

    @Test
    public void test_can_not_match_with_empty_json_payload(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setJson("{\"foo\":\"bar\"}");
        mociRequest.setBody(mociBody);
        BodyJsonProcessor bodyJsonProcessor =new BodyJsonProcessor();
        bodyJsonProcessor.bind(mociRequest);
        Request request=new Request();
        request.setText(null);
        Assert.assertFalse(bodyJsonProcessor.match(request));
        request.setText("");
        Assert.assertFalse(bodyJsonProcessor.match(request));
    }

    @Test
    public void test_can_not_match_with_invalid_json_payload(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setJson("{\"foo\":\"bar\"}");
        mociRequest.setBody(mociBody);
        BodyJsonProcessor bodyJsonProcessor =new BodyJsonProcessor();
        bodyJsonProcessor.bind(mociRequest);
        Request request=new Request();
        request.setText(null);
        Assert.assertFalse(bodyJsonProcessor.match(request));
        request.setText("<test><id>123</id></test>");
        Assert.assertFalse(bodyJsonProcessor.match(request));
    }

    @Test
    public void test_can_not_match_with_unmatched_json_payload(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setJson("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"pd\":123,\n" +
                "               \t\t\t\"rd\":\"aaa\"\n" +
                "               \t\t},\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }");
        mociRequest.setBody(mociBody);
        BodyJsonProcessor bodyJsonProcessor =new BodyJsonProcessor();
        bodyJsonProcessor.bind(mociRequest);
        Request request=new Request();
        request.setText("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"pd\":124,\n" +
                "               \t\t\t\"rd\":\"aaa\"\n" +
                "               \t\t},\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }");
        Assert.assertFalse(bodyJsonProcessor.match(request));
    }

    @Test
    public void test_can_match(){
        MociRequest mociRequest=new MociRequest();
        MociBody mociBody =new MociBody();
        mociBody.setJson("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"pd\":123,\n" +
                "               \t\t\t\"rd\":\"aaa\"\n" +
                "               \t\t},\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }");
        mociRequest.setBody(mociBody);
        BodyJsonProcessor bodyJsonProcessor =new BodyJsonProcessor();
        bodyJsonProcessor.bind(mociRequest);
        Request request=new Request();
        request.setText("{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"name\":{\n" +
                "               \t\t\t\"rd\":\"aaa\",\n" +
                "               \t\t\t\"pd\":123\n" +
                "               \t\t},\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }");
        Assert.assertTrue(bodyJsonProcessor.match(request));

    }
}
