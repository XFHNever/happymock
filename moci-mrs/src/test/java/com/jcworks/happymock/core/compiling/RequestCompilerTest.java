package com.jcworks.happymock.core.compiling;

import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;
import com.jcworks.happymock.core.compiling.v1.RequestCompiler;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-22
 */
@Test
public class RequestCompilerTest {
    @Test
    public void testCompile(){
        RequestCompiler requestCompiler=new RequestCompiler();
        String dsl="{\"uri\": \"/fulfillment/v1/window/**/listing\"}" ;
        try {
            requestCompiler.compile(dsl);
        } catch (CompileException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(true);
    }
    @Test
    public void testCompile1(){
        RequestCompiler requestCompiler=new RequestCompiler();
        String dsl="{\"url\": \"/fulfillment/v1/window/**/listing\"}" ;
        try {
            requestCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.UNRECOGNIZED_KEYWORD.getCode());
        }
    }
    @Test
    public void testCompile2(){
        RequestCompiler requestCompiler=new RequestCompiler();
        String dsl="{}" ;
        try {
            requestCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.EMPTY_DSL_BODY.getCode());
        }
    }
}
