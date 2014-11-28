package com.ebay.happymock.core.processor;


import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;

/**
 * User: jicui
 * Date: 14-7-31
 */
public interface RequestProcessor {

    /**
     * Parsing the real-time http request and return true|false if request matches the pattern
     *
     * @param httpRequest
     * @return
     */
    public boolean match(Request httpRequest);

    /**
     * Bind the processor to the instance if the DSL keyword has been found
     *
     * @param mociRequest
     * @return
     */
    public boolean bind(MociRequest mociRequest);
}
