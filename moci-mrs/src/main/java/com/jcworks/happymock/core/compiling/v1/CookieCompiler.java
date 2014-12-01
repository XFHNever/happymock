package com.jcworks.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;
import com.google.common.base.Strings;

import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-29
 */
public class CookieCompiler extends CompilerUtility implements Compiler {
    @Override
    public void compile(String dsl) throws CompileException {
        JSONObject jsonObject=null;
        try{
            //request dsl should be a json object
            jsonObject= JSON.parseObject(dsl);
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
        Map<String,String> cookieMap=super.getChildDSL(jsonObject);
        Iterator<String> iterator=cookieMap.keySet().iterator();
        while(iterator.hasNext()){
            String cookieName=iterator.next();
            String cookieValue=cookieMap.get(cookieName);
            if(Strings.isNullOrEmpty(cookieName)||Strings.isNullOrEmpty(cookieValue)){
                throw new CompileException(CompileErrorCode.INVALID_COOKIE_NAME_VALUE_PARE,cookieName,cookieValue);
            }
        }
    }

    @Override
    public String getKeyword() {
        return "cookies";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
