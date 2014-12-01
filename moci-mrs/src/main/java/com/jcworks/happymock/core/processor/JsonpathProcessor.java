package com.jcworks.happymock.core.processor;

import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Jsonpath process tries to process the json with given expression,This processor is by compile the express and tries to identify the number of matched nodes ,for example as below
 *  {
 *  "orderId" : 166941150,
 *  "ticketSeat" : [ {
 *  "row" : "e",
 *  "seat" : "11",
 *  "barcode" : "37917046628213",
 *  "type" : "R"
 *  },
 *  {
 *  "row" : "e",
 *  "seat" : "12",
 *  "barcode" : "379170466282111",
 *  "type" : "R"
 *  }]
 *  }
 *
 * try to match the ticketSeat node which his child property with name seat has value 123 and the expected quantity is 1
 *   {
 *       jsonpath:{
 *           "$.ticketSeat[?(@.seat=='123')]","1"
 *       }
 *   }
 *
 * try to match the all the ticketSeat nodes ,and the expected quantity is 2 (there is two ticketSeat element in json string ,with type of JsonObject)
 * {
 *       jsonpath:{
 *           "$..ticketSeat[*]","2"
 *       }
 *   }
 *
 * try to match the all the ticketSeat ,and the expected quantity is 1  (there is only one ticketSeat in json string ,with type of JsonArray)
 * {
 *       jsonpath:{
 *           "$.ticketSeat","2"
 *       }
 *   }
 *
 * try to match multiple expressions
 * {
 *       jsonpath:{
 *           "$.ticketSeat","2",
 *           "$.ticketSeat[?(@.type=='R')]","2"
 *       }
 *   }
 *
 *
 * User: jicui
 * Date: 14-8-20
 * @see "http://goessner.net/articles/JsonPath/"
 *
 */


public class JsonpathProcessor implements RequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(JsonpathProcessor.class);
    private ImmutableMap<String,String> jsonpathMap;
    @Override
    public boolean match(Request httpRequest) {
        checkNotNull(jsonpathMap);
        if(!httpRequest.isJson()){return false;}
        try{
            if(!Strings.isNullOrEmpty(httpRequest.getText())){
                //try to parse the string to json object
                JSONObject realJson= httpRequest.getRequestBodyWrapper().getJson();
                //looping the jsonpath and try to match all
                UnmodifiableIterator<String> itor= jsonpathMap.keySet().iterator();
                boolean matchAll=true;
                while(itor.hasNext()){
                    String jsonpath=itor.next();
                    String expectedValue=jsonpathMap.get(jsonpath);
                    List<?> nodes=JsonPath.compile(jsonpath).read(realJson);
                    String value=null;
                    value=String.valueOf(nodes.size());
                    if(value!=null&&!value.equals(expectedValue)){
                        matchAll=false;
                        break;
                    }
                }
                return matchAll;
            }else{
                return false;
            }
        }catch(Exception e){
            LOG.debug("can not match the request as the json format is invalid,json={}", httpRequest.getText());
            return false;
        }
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if (mociRequest.getJsonpath() != null && !mociRequest.getJsonpath().isEmpty()) {
            jsonpathMap = ImmutableMap.copyOf(mociRequest.getJsonpath());
            return true;
        } else {
            return false;
        }
    }
}
