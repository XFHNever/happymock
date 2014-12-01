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
public class CookiesBinder implements ResponseBinder{
    private static final Logger LOG = LoggerFactory.getLogger(CookiesBinder.class);
    private ImmutableMap<String,String> cookiesMap;
    @Override
    public void write(Response response) {
        checkNotNull(cookiesMap);
        response.setCookies(cookiesMap);
    }

    @Override
    public boolean bind(MociResponse response) {
        if(response.getCookies()!=null&&!response.getCookies().isEmpty()){
            cookiesMap= ImmutableMap.copyOf(response.getCookies());
            return true;
        }
        return false;
    }
}
