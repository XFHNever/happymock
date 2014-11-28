package com.ebay.happymock.core.processor;

import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-9-2
 */
public class HttpMethodProcessor implements RequestProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(HttpMethodProcessor.class);
    private String method;
    @Override
    public boolean match(Request httpRequest) {
        checkNotNull(method);
        if(!Strings.isNullOrEmpty(httpRequest.getMethod())){
            String value=httpRequest.getMethod();
            return value.equalsIgnoreCase(method);
        }else{
            return false;
        }
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if(!Strings.isNullOrEmpty(mociRequest.getMethod())){
           this.method=mociRequest.getMethod().trim().toLowerCase();
           return true;
        }
        return false;
    }
}
