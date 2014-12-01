package com.jcworks.happymock.core.binder;


import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;

/**
 * User: jicui
 * Date: 14-7-31
 */
public interface ResponseBinder {

    /**
     * Write the mock response
     *
     * @param response
     */
    public void write(Response response);

    /**
     * Bind the response binder to MockResponse
     *
     * @param response
     * @return
     */
    public boolean bind(MociResponse response);
}
