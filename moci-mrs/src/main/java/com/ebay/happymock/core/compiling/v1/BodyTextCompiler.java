package com.ebay.happymock.core.compiling.v1;

/**
 * User: jicui
 * Date: 14-8-29
 */
import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import com.google.common.base.Strings;

public class BodyTextCompiler implements Compiler {
    @Override
    public void compile(String dsl) throws CompileException {
        if(Strings.isNullOrEmpty(dsl)){
            throw new CompileException(CompileErrorCode.INVALID_TEXT_VALUE,dsl);
        }
    }

    @Override
    public String getKeyword() {
        return "text";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
