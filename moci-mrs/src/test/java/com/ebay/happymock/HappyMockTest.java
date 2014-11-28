package com.ebay.happymock;

import com.alibaba.fastjson.JSON;
import com.ebay.happymock.core.compiling.v1.RootCompiler;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import com.ebay.happymock.core.composition.ComposeFactory;
import com.ebay.happymock.core.entity.MociSetting;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.entity.Response;
import com.ebay.happymock.search.MociFinder;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-9-2
 */
@Test
public class HappyMockTest {
    @Test
    void test_request_compiler_and_compose() throws CompileException {
        String dsl = "{\"request\":{\"method\":\"get\",\"uri\":\"/fulfillment/v1/window/**/listing\",\"headers\":{\"Authorization\":\"gWwh4zP4l90Cj4wQCslKHpB67_8a\"},\"cookies\":{\"guid\":\"gWwh4zP4l90Cj4wQCslKHpB67_8a\"},\"body\":{\"json\":{\"foo\":\"bar\",\"name\":{\"pd\":123,\"rd\":\"aaa\"},\"code\":\"123\"}},\"jsonpath\":{\"$.ticketSeat[?(@.type=='R')]\":\"2\",\"$.ticketSeat\":\"2\"}},\"response\":{\"status\":\"200\"}}";
        RootCompiler rootCompiler = new RootCompiler();
        rootCompiler.compile(dsl);
        MociSetting mociSetting=JSON.parseObject(dsl,MociSetting.class);
        ComposeFactory.getInstance().composeSetting(mociSetting);
        Assert.assertNotNull(mociSetting);
    }

    @Test
    void test_response_compiler_and_compose() throws CompileException {
        String dsl = "{\"request\":{\"method\":\"get\"},\"response\":{\"status\":\"400\",\"headers\":{\"Authorization\":\"gWwh4zP4l90Cj4wQCslKHpB67_8a\"},\"body\":{\"text\":\"text111\"}}}";
        RootCompiler rootCompiler = new RootCompiler();
        rootCompiler.compile(dsl);
        MociSetting mociSetting=JSON.parseObject(dsl,MociSetting.class);
        ComposeFactory.getInstance().composeSetting(mociSetting);
        Assert.assertNotNull(mociSetting);
    }


    /**
     * test match
     * set the url does not match the mock specs
     * set the cookie c1=cv11 does not match the mock specs
     * expect return 404
     */
    @Test
     void test_match_fail(){
        Request request=new Request();
        request.setUrl("/aaa/bbb/ccc/ddd/?foo=123&bar=345");
        request.setUri("/aaa/bbb/ccc");
        request.setMethod("POST");
        request.setCookies(ImmutableMap.of("c1", "cv11", "c2", "cv2"));
        request.setQueryParam(ImmutableMap.of("q1","qv1","q2","qv2"));
        request.setHeaders(ImmutableMap.of("h1","hv1","h2","hv2"));
        request.setText(TestUtility.getTestJson());

        Response response = new Response();
        response.setRequest(request);
        Optional<MociSetting> mociSetting = MociFinder.getInstance().firstMatch(request);
        Assert.assertFalse(mociSetting.isPresent());
    }


    /**
     *
     *  Test success case,all request processor match
     *
     */
    @Test
    void test_match_success(){
        Request request=new Request();
        request.setUrl("/aaa/bbb/ccc/ddd/?foo=123&bar=345");
        request.setUri("/aaa/bbb/ccc");
        request.setMethod("POST");
        request.setCookies(ImmutableMap.of("c1", "cv1", "c2", "cv2"));
        request.setQueryParam(ImmutableMap.of("q1","qv1","q2","qv2"));
        request.setHeaders(ImmutableMap.of("h1","hv1","h2","hv2"));
        request.setText(TestUtility.getTestJson());

        Response response = new Response();
        response.setRequest(request);
        Optional<MociSetting> mociSetting = MociFinder.getInstance().firstMatch(request);
        Assert.assertTrue(mociSetting.isPresent());
    }

    /**
     *
     *  Test success case,all request processor match
     *
     */
    @Test
    void test_match_xml_success(){
        Request request=new Request();
        request.setUrl("/aaa/bbb/ccc/ddd/?foo=123&bar=345");
        request.setUri("/aaa/bbb/ccc");
        request.setMethod("POST");
        request.setCookies(ImmutableMap.of("c1", "cv1", "c2", "cv2"));
        request.setQueryParam(ImmutableMap.of("q1","qv1","q2","qv2"));
        request.setHeaders(ImmutableMap.of("h1","hv1","h2","hv2"));
        request.setText(TestUtility.getTestXml());

        Response response = new Response();
        response.setRequest(request);
        Optional<MociSetting> mociSetting = MociFinder.getInstance().firstMatch(request);
        Assert.assertTrue(mociSetting.isPresent());
    }

    @Test
    void test_bind(){
        Request request=new Request();
        request.setUrl("/aaa/bbb/ccc/ddd/?foo=123&bar=345");
        request.setUri("/aaa/bbb/ccc");
        request.setMethod("POST");
        request.setCookies(ImmutableMap.of("c1", "cv1", "c2", "cv2"));
        request.setQueryParam(ImmutableMap.of("q1","qv1","q2","qv2"));
        request.setHeaders(ImmutableMap.of("h1","hv1","h2","hv2"));
        request.setText(TestUtility.getTestJson());

        Response response = new Response();
        response.setRequest(request);
        Optional<MociSetting> mociSetting = MociFinder.getInstance().firstMatch(request);
        Assert.assertTrue(mociSetting.isPresent());
        mociSetting.get().write(response);
        Assert.assertNotNull(response.getJson());
        System.out.println(response.getJson());
    }

    @Test
    void test_bind_xml(){
        Request request=new Request();
        request.setUrl("/aaa/bbb/ccc/ddd/?foo=123&bar=345");
        request.setUri("/aaa/bbb/ccc");
        request.setMethod("POST");
        request.setCookies(ImmutableMap.of("c1", "cv1", "c2", "cv2"));
        request.setQueryParam(ImmutableMap.of("q1","qv1","q2","qv2"));
        request.setHeaders(ImmutableMap.of("h1","hv1","h2","hv2"));
        request.setText(TestUtility.getTestXml());

        Response response = new Response();
        response.setRequest(request);
        Optional<MociSetting> mociSetting = MociFinder.getInstance().firstMatch(request);
        Assert.assertTrue(mociSetting.isPresent());
        mociSetting.get().write(response);
        Assert.assertNotNull(response.getXml());
        System.out.println(response.getXml());
    }

    @Test
    void test_time_delay_mock(){
        Request request=new Request();
        request.setUrl("/fulfillment/v1/window/12345/listing");
        request.setUri("/fulfillment/v1/window/12345/listing");
        request.setMethod("POST");
        request.setHeaders(ImmutableMap.of("authorization","gWwh4zP4l90Cj4wQCslKHpB67_8a"));
        request.setText("<request><parameters><id>1</id></parameters></request>");
        Response response = new Response();
        response.setRequest(request);
        Optional<MociSetting> mociSetting = MociFinder.getInstance().firstMatch(request);
        Assert.assertTrue(mociSetting.isPresent());
        mociSetting.get().write(response);
        Assert.assertEquals(response.getTimeDelay(),Long.valueOf(1000L));
    }



}
