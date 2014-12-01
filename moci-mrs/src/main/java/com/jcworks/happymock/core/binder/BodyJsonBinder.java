package com.jcworks.happymock.core.binder;

import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-9-3
 */
public class BodyJsonBinder implements ResponseBinder {
    private static final Logger LOG = LoggerFactory.getLogger(BodyJsonBinder.class);
    private String jsonStr;

    @Override
    public void write(Response response) {
        checkNotNull(jsonStr);
        response.setJson(response.buildDynamicContent(jsonStr));
    }

    @Override
    public boolean bind(MociResponse response) {
        if (response.getBody() != null && !Strings.isNullOrEmpty(response.getBody().getJson())) {
            try {
                //JSON.parseObject(response.getBody().getJson());
                //no need to parse str to json as this has been done in compiling phrase
                jsonStr = response.getBody().getJson();
                return true;
            } catch (Exception e) {
                LOG.debug("can not bind the response as the json format is invalid,json={}", response.getBody().getJson());
                return false;
            }
        } else {
            return false;
        }
    }
}
