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
public class BodyXmlBinder implements ResponseBinder {
    private static final Logger LOG = LoggerFactory.getLogger(BodyXmlBinder.class);
    private String xmlStr;
    @Override
    public void write(Response response) {
      checkNotNull(xmlStr);
      response.setXml(response.buildDynamicContent(xmlStr));
    }

    @Override
    public boolean bind(MociResponse response) {
        if (response.getBody() != null && !Strings.isNullOrEmpty(response.getBody().getXml())) {
            try {
                //no need to parse str to xml as this has been done in compiling phrase
                xmlStr = response.getBody().getXml();
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
