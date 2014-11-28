package com.ebay.happymock.core.compiling.v1;

import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;

/**
 * User: jicui
 * Date: 14-9-2
 */
public class HttpMethodCompiler implements Compiler{
    @Override
    public void compile(String dsl) throws CompileException {
        String method = dsl;
        if (!method.equalsIgnoreCase("get")
             &&!method.equalsIgnoreCase("post")
             && !method.equalsIgnoreCase("put")
             && !method.equalsIgnoreCase("delete")
             && !method.equalsIgnoreCase("head")
                ) {
            throw new CompileException(CompileErrorCode.INVALID_HTTP_METHOD_VALUE, dsl);
        }
    }

    @Override
    public String getKeyword() {
        return "method";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
