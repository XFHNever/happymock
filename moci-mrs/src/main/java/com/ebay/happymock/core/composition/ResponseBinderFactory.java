package com.ebay.happymock.core.composition;

import com.ebay.happymock.core.binder.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: jicui
 * Date: 14-8-12
 */
public class ResponseBinderFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseBinderFactory.class);

    private ResponseBinderFactory() {
    }

    private static class ResponseBinderFactoryHolder {
        private static final ResponseBinderFactory FACTORY_INSTANCE = new ResponseBinderFactory();
    }

    public List<ResponseBinder> getBinderList() {
        List<ResponseBinder> responseBinderList = Lists.newArrayList();
        responseBinderList.add(new TextBinder());// register binder
        responseBinderList.add(new StatusBinder());
        responseBinderList.add(new HeadersBinder());
        responseBinderList.add(new CookiesBinder());
        responseBinderList.add(new BodyJsonBinder());
        responseBinderList.add(new BodyXmlBinder());
        responseBinderList.add(new BodyTextBinder());
        responseBinderList.add(new TimeDelayBinder());
        return responseBinderList;
    }

    public static ResponseBinderFactory getInstance() {
        return ResponseBinderFactoryHolder.FACTORY_INSTANCE;
    }
}
