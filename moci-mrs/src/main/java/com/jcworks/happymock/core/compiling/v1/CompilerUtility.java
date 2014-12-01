package com.jcworks.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-8-22
 */
public class CompilerUtility {
    /**
     * Get the child dsl
     *
      * @param dsl
     * @return
     */
    public Map<String,String> getChildDSL(String dsl) throws CompileException {
        JSONObject jsonObject;
        try{
            //dsl should be a jsonobject
            jsonObject= JSON.parseObject(dsl);
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
        return getChildDSL(jsonObject);
    }

    /**
     * Get the child dsl
     *
     * @param jsonObject
     * @return
     */
    public Map<String,String> getChildDSL(JSONObject jsonObject){
        Map<String,String> dslMap=new HashMap<String,String>();
        checkNotNull(jsonObject);
        //validate keyword matchable
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        Iterator<Map.Entry<String, Object>> itor = entrySet.iterator();
        while (itor.hasNext()) {
            Map.Entry<String, Object> stringObjectEntry = itor.next();
            String key = stringObjectEntry.getKey();
            String subDSL=jsonObject.getString(key);
            dslMap.put(key,subDSL);
        }
        return dslMap;
    }

}
