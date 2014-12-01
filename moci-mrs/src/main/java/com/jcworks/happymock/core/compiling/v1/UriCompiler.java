package com.jcworks.happymock.core.compiling.v1;

import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;

/**
 * User: jicui
 * Date: 14-8-22
 */
public class UriCompiler implements com.jcworks.happymock.core.compiling.v1.Compiler {

    @Override
    public void compile(String dsl) throws CompileException {
        String value = dsl;
        //if the uri is not match ant style and also not a path start with '/',then return error
        if(!isAntPath(value)&&!isNormalPath(value)){
            throw new CompileException(CompileErrorCode.INVALID_ANT_STYLE_URI,dsl);
        }
    }

    private boolean isNormalPath(String path){
        return path.startsWith("/");
    }

    public boolean isAntPath(String path) {
        return (path.indexOf('*') != -1 || path.indexOf('?') != -1);
    }

    @Override
    public String getKeyword() {
        return "uri";
    }

    @Override
    public com.jcworks.happymock.core.compiling.v1.Compiler locateChild(String keyword) {
        return null;
    }
}
