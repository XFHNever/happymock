package com.jcworks.happymock.core.compiling.v1;

import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;

/**
 * User: jicui
 * Date: 14-8-29
 */
public class StatusCompiler implements Compiler {
    @Override
    public void compile(String dsl) throws CompileException {
        String statusCode = dsl;
        try {
            Integer.valueOf(statusCode);
        } catch (Exception e) {
            throw new CompileException(CompileErrorCode.INVALID_STATUS_CODE, dsl);
        }
    }

    @Override
    public String getKeyword() {
        return "status";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
