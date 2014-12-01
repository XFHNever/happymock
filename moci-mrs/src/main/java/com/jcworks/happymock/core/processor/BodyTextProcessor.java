package com.jcworks.happymock.core.processor;

import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-9-2
 */
public class BodyTextProcessor implements RequestProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(BodyTextProcessor.class);
    private String text;
    @Override
    public boolean match(Request httpRequest) {
        checkNotNull(text);
        if(!Strings.isNullOrEmpty(httpRequest.getText())){
           String value=httpRequest.getText();
           return value.equals(text);
        }
        return false;
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if(null!=mociRequest.getBody()&&!Strings.isNullOrEmpty(mociRequest.getBody().getText())){
            text=mociRequest.getBody().getText();
            return true;
        } else{
            return false;
        }
    }
}
