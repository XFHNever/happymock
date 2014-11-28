package com.ebay.happymock.core.compiling;

import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import com.ebay.happymock.core.compiling.v1.RootCompiler;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-22
 */
@Test
public class RootCompilerTest {
    @Test
    void testCompile() throws CompileException {
        String dsl="{\"request\":{\"uri\":\"/123\"},\"response\":{\"status\":\"200\"}}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
        Assert.assertTrue(true);
    }

    @Test
    void testCompile1(){
        String dsl="{\"request\":{\"uri\":\"/123\"},\"response1\":{\"status\":\"200\"}}";
        RootCompiler rootCompiler=new RootCompiler();
        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
              Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.UNRECOGNIZED_KEYWORD.getCode());
        }
    }

    @Test
    void testCompile2(){
        String dsl="{\"request\":{\"uri\"=\"/123\"},\"response1\":{\"status\":\"200\"}}";
        RootCompiler rootCompiler=new RootCompiler();
        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.UNPARSEABLE_FORMAT.getCode());
        }
    }

    @Test
    void testCompile3() throws CompileException {
        String dsl="{\"request\":{\"uri\":\"/123\"}}";
        RootCompiler rootCompiler=new RootCompiler();
        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.MISSING_COMPILED_KEYWORD.getCode());
        }
    }

    @Test
    void testCompile4() {
        String dsl="{\"request\":{\"uri\":\"/123\"},\"response\":{\"status\":\"200\"}}";
        RootCompiler rootCompiler=new RootCompiler();
        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.UNRECOGNIZED_KEYWORD.getCode());
        }
    }

    @Test
    void testCompile5() throws CompileException {
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"cookies\": {\n" +
                "            \"Authorization\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"uri\": \"/fulfillment/v1/window/**/listing\"\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"status\": \"200\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
    }

    @Test
    void testCompile6() throws CompileException {
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"body\":{\n" +
                "               \"json\":\"{\\\"foo\\\":\\\"bar\\\"}\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"status\": \"200\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
    }

    @Test
    void testCompile7() throws CompileException {
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"uri\":\"/fulfillment/**\",\n" +
                "        \"xpath\":{\n" +
                "              \"/request/parameters/name[@cid='test']\":\"1\",\n" +
                "              \"/request/parameters/id[text()='2000']\":\"2\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"status\": \"200\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
    }

    @Test
    void testCompile8() throws CompileException {
        String dsl="{\"request\": {\n" +
                "        \"uri\":\"/fulfillment/**\",\n" +
                "        \"jsonpath\":{\n" +
                "              \"$.ticketSeat[?(@.type=='R')]\":\"2\",\n" +
                "              \"$.ticketSeat\":\"2\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"status\": \"200\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
    }

    @Test
      void testCompile9(){
        String dsl="{\"request\": {\n" +
                "        \"uri\":\"/fulfillment/**\",\n" +
                "        \"jsonpath\":{\n" +
                "              \"$.ticketSeat[?(@.type=='R')]\":\"2\",\n" +
                "              \"$.ticketSeat\":\"2\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "    \t\"status\":\"302a\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();

        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(CompileErrorCode.INVALID_STATUS_CODE.getCode(),e.getErrorCode().getCode());
        }
    }

    @Test
    void testCompile10(){
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"headers\": {\n" +
                "            \"Authorization\": \"\"\n" +
                "        },\n" +
                "        \"uri\": \"/fulfillment/v1/window/**/listing\"\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"status\": \"200\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();

        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(CompileErrorCode.INVALID_HEADER_NAME_VALUE_PARE.getCode(),e.getErrorCode().getCode());
        }
    }

    @Test
    void testCompile11() throws CompileException {
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"headers\": {\n" +
                "            \"Authorization\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"uri\": \"/fulfillment/v1/window/**/listing\"\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "    \t\"status\":\"200\",\n" +
                "     \t\"headers\": {\n" +
                "            \"Authorization\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
            RootCompiler rootCompiler=new RootCompiler();
            rootCompiler.compile(dsl);
    }

    @Test
    void testCompile12() throws CompileException {
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"body\":{\n" +
                "               \"json\":{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"status\": \"200\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
    }

    @Test
    void testCompile13() throws CompileException {
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"body\":{\n" +
                "               \"json\":{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "    \t\"status\":\"200\",\n" +
                "     \t\"headers\": {\n" +
                "            \"Authorization\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"body\":{\n" +
                "               \"json\":{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
    }

    @Test
    void testCompile14() throws CompileException {
        String dsl="{\n" +
                "    \"request\": {\n" +
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
                "        \"body\":{\n" +
                "                \"xml\":\"<request><parameters><id>1</id></parameters></request>\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        rootCompiler.compile(dsl);
    }

    @Test
    void testCompile15(){
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"body\":{\n" +
                "               \"json\":{\n" +
                "               \t\t\"foo\":\"bar\",\n" +
                "               \t\t\"code\":\"123\"\n" +
                "               },\n" +
                "               \"text\":\"123\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "    \t\"status\":\"200\",\n" +
                "     \t\"headers\": {\n" +
                "            \"Authorization\": \"gWwh4zP4l90Cj4wQCslKHpB67_8a\"\n" +
                "        },\n" +
                "        \"body\":{\n" +
                "                \"xml\":\"<request><parameters><id>1</id></parameters></request>\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();
        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(CompileErrorCode.INVALID_BODY_ELEMENT.getCode(),e.getErrorCode().getCode());
        }
    }

    @Test
    void testCompile16(){
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"method\":\"adg\" ," +
                "        \"uri\": \"/fulfillment/v1/window/**/listing\"\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"status\": \"200\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();

        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(CompileErrorCode.INVALID_HTTP_METHOD_VALUE.getCode(),e.getErrorCode().getCode());
        }
    }

    @Test
    void testCompile17(){
        String dsl="{\n" +
                "    \"request\": {\n" +
                "        \"method\":\"get\" ," +
                "        \"uri\": \"/fulfillment/v1/window/**/listing\"\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"timeDelay\": \"3000000000\"\n" +
                "    }\n" +
                "}";
        RootCompiler rootCompiler=new RootCompiler();

        try {
            rootCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(CompileErrorCode.INVALID_TIME_DELAY_CODE.getCode(),e.getErrorCode().getCode());
        }
    }
}
