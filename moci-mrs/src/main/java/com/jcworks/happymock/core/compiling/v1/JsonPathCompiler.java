package com.jcworks.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;

import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-26
 */
public class JsonPathCompiler extends CompilerUtility implements Compiler {
    @Override
    public void compile(String dsl) throws CompileException {
        JSONObject jsonObject=null;
        try{
            //request dsl should be a json object
            jsonObject= JSON.parseObject(dsl);
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
        Map<String,String> jsonPathMap=super.getChildDSL(jsonObject);
        Iterator<String> iterator=jsonPathMap.keySet().iterator();
        while(iterator.hasNext()){
            String jsonpath=iterator.next();
            String matchedNode=jsonPathMap.get(jsonpath);
            isValidJsonPath(jsonpath);
            try{
                Integer.valueOf(matchedNode);
            }catch(NumberFormatException e){
                throw new CompileException(CompileErrorCode.INVALID_EXPRESSION_VALUE,matchedNode);
            }
        }
    }

    @Override
    public String getKeyword() {
        return "jsonpath";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }

    private void isValidJsonPath(String jsonPath) throws CompileException{
      if(!jsonPath.startsWith("$.")){
          throw new CompileException(CompileErrorCode.INVALID_JSONPATH,jsonPath);
      }
      if(jsonPath.indexOf(".")==-1){
          throw new CompileException(CompileErrorCode.INVALID_JSONPATH,jsonPath);
      }
    }
}
