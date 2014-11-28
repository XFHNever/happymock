package com.ebay.happymock;

import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.entity.Response;
import com.google.common.collect.ImmutableMap;
import ognl.OgnlException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jicui on 10/19/14.
 */
@Test
public class RegexReplacementTest
{

    @Test
    public void test_extract(){
        String str="th#url#is is a #method[aa2]# and  #json['$.store.book[0].author']# with #xml['//bookstore/book[author=\"Erik T. Ray\"]/title/text()']# thus it is a #uri[0]#";
        Pattern pattern=Pattern.compile("(#(method|url)#)|#(json|xml|header|cookie|query|uri)(\\[('[^#]+'|[0-9]+)\\]){1}#");
        Matcher matcher=pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
           String s=matcher.group();
            System.out.println(s);
           //matcher.appendReplacement(sb, "dog");
        }
        //matcher.appendTail(sb);
        //Assert.assertEquals("this is a #test# and  dog with dog", sb.toString());
    }

    @Test
    public void test_uri(){
        Pattern URI_PATTERN=Pattern.compile("/[0-9a-zA-Z/]+");
        Matcher matcher=URI_PATTERN.matcher("/aaa/bbb/ccc/?foo=123&fag=345");
        matcher.find();
        String s=matcher.group();
        System.out.println(s);
            //matcher.appendReplacement(sb, "dog");

    }

    @Test
    void test_response_build_dynamic_content_json(){
        Request request=new Request();
        request.setUrl("/aaa/bbb/ccc?foo=123&bar=345");
        request.setUri("/aaa/bbb/ccc");
        request.setMethod("PUT");
        request.setCookies(ImmutableMap.of("c1","cv1","c2","cv2"));
        request.setQueryParam(ImmutableMap.of("q1","qv1","q2","qv2"));
        request.setHeaders(ImmutableMap.of("h1","hv1","h2","hv2"));
        request.setText(TestUtility.getTestJson());
        Response response=new Response();
        response.setRequest(request);
        String json="{\n" +
                "  \"message\":\"all mock request details\",\n" +
                "  \"method\":\"#method#\",\n" +
                "  \"url\":\"#url#\",\n" +
                "  \"uri1\":\"#uri[1]#\",\n" +
                "  \"headers\":\"#header['h2']#\",\n" +
                "  \"cookies\":\"#cookie['c1']#\",\n" +
                "  \"jsonPath\":\"#json['$.store.book[0].author']#\",\n" +
                "  \"queryparams\":\"#query['q2']#\"\n" +
                "}";

           String dcontent= response.buildDynamicContent(json);
           response.setJson(dcontent);
           System.out.println("From json="+json+" to json="+dcontent);

    }

    @Test
    void test_response_build_dynamic_content_xml(){
        Request request=new Request();
        request.setUrl("/aaa/bbb/ccc?foo=123&bar=345");
        request.setUri("/aaa/bbb/ccc");
        request.setMethod("PUT");
        request.setCookies(ImmutableMap.of("c1","cv1","c2","cv2"));
        request.setQueryParam(ImmutableMap.of("q1","qv1","q2","qv2"));
        request.setHeaders(ImmutableMap.of("h1","hv1","h2","hv2"));
        request.setText(TestUtility.getTestJson());
        Response response=new Response();
        response.setRequest(request);
        String xml="<happymock>\n" +
                "<request>\n" +
                "<message>this is a mock test</message>\n" +
                "<method>#method#</method>\n" +
                "<url>#url#</url>\n" +
                "<uri1>#uri[1]#</uri1>\n" +
                "<header>#header['h2']#</header>\n" +
                "<cookie>#cookie['c1']#</cookie>\n" +
                "<jsonpath>#json['$.store.book[0].author']#</jsonpath>\n" +
                "<queryparams>#query['q2']#</queryparams>\n" +
                "</request>\n" +
                "</happymock>";

            String dcontent= response.buildDynamicContent(xml);
            response.setXml(dcontent);
            System.out.println("From xml="+xml+" to xml="+dcontent);

    }
}
