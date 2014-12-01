package com.jcworks.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-26
 */
public class XPathCompiler extends CompilerUtility implements Compiler {
   public static final XPathFactory X_PATH_FACTORY = XPathFactory.newInstance();
    @Override
    public void compile(String dsl) throws CompileException {
        JSONObject jsonObject=null;
        try{
            //request dsl should be a json object
            jsonObject= JSON.parseObject(dsl);
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
        Map<String,String> xpathMap=super.getChildDSL(jsonObject);
        Iterator<String> iterator=xpathMap.keySet().iterator();
        while(iterator.hasNext()){
            String xpath=iterator.next();
            String matchedNode=xpathMap.get(xpath);
            try {
                X_PATH_FACTORY.newXPath().compile(xpath);
            } catch (XPathExpressionException e) {
                throw new CompileException(CompileErrorCode.INVALID_XPATH,xpath);
            }
            try{
                Integer.valueOf(matchedNode);
            }catch(NumberFormatException e){
                throw new CompileException(CompileErrorCode.INVALID_EXPRESSION_VALUE,matchedNode);
            }
        }
    }

    @Override
    public String getKeyword() {
        return "xpath";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
