package com.jcworks.happymock.core.compiling;

import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;
import com.jcworks.happymock.core.compiling.v1.UriCompiler;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-22
 */
@Test
public class UriCompilerTest {
    @Test
    public void testCompile(){
        String dsl="/221/12";
        UriCompiler uriCompiler= new UriCompiler();
        try {
            uriCompiler.compile(dsl);
        } catch (CompileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCompile1(){
        String dsl="221";
        UriCompiler uriCompiler= new UriCompiler();
        try {
            uriCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.INVALID_ANT_STYLE_URI.getCode());
        }
    }
}
