package com.jcworks.happymock.core.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-8-15
 */
public class BodyJsonProcessor implements RequestProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(BodyJsonProcessor.class);
    private JSONObject json;

    @Override
    public boolean match(Request httpRequest) {
        checkNotNull(json);
        if(!httpRequest.isJson()){return false;}
        if (!Strings.isNullOrEmpty(httpRequest.getText())) {
            try {
                JSONObject realJson = httpRequest.getRequestBodyWrapper().getJson();
                if (realJson != null && realJson.equals(json)) {
                    return true;
                }
            } catch (Exception e) {
                LOG.debug("can not match the request as the json format is invalid,json={}", httpRequest.getText());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if (null != mociRequest.getBody() && !Strings.isNullOrEmpty(mociRequest.getBody().getJson())) {
            try {
                this.json = JSON.parseObject(mociRequest.getBody().getJson());
                return true;
            } catch (Exception e) {
                LOG.debug("can not bind the request as the json format is invalid,json={}",mociRequest.getBody().getJson());
                return false;
            }
        }
        return false;
    }
}
