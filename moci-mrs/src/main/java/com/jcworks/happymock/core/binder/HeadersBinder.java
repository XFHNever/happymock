package com.jcworks.happymock.core.binder;

import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-9-2
 */
public class HeadersBinder implements ResponseBinder {
    private static final Logger LOG = LoggerFactory.getLogger(HeadersBinder.class);
    private ImmutableMap<String,String> headersMap;
    @Override
    public void write(Response response) {
       checkNotNull(headersMap);
       response.setHeaders(headersMap);
    }

    @Override
    public boolean bind(MociResponse response) {
        if(response.getHeaders()!=null&&!response.getHeaders().isEmpty()){
            headersMap= ImmutableMap.copyOf(response.getHeaders());
            return true;
        }
        return false;
    }
}
