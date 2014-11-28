package com.ebay.happymock.core.composition;

import com.ebay.happymock.core.processor.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: jicui
 * Date: 14-8-4
 */
public class RequestProcessorFactory {
    private static final Logger LOG = LoggerFactory.getLogger(RequestProcessorFactory.class);

    private RequestProcessorFactory() {
    }

    private static class RequestProcessorFactoryHolder {
        private static final RequestProcessorFactory FACTORY_INSTANCE = new RequestProcessorFactory();
    }

    public List<RequestProcessor> getProcessList() {
        List<RequestProcessor> requestProcessorList = Lists.newArrayList();
        requestProcessorList.add(new UriProcessor());
        requestProcessorList.add(new HttpMethodProcessor());
        requestProcessorList.add(new HeadersProcessor());
        requestProcessorList.add(new CookiesProcessor());
        //requestProcessorList.add(new BodyXmlProcessor());
        requestProcessorList.add(new BodyXmlProcessorV2());
        requestProcessorList.add(new BodyJsonProcessor());
        requestProcessorList.add(new BodyTextProcessor());
        requestProcessorList.add(new XPathProcessor());
        requestProcessorList.add(new JsonpathProcessor());
        return requestProcessorList;
    }

    public static RequestProcessorFactory getInstance() {
        return RequestProcessorFactoryHolder.FACTORY_INSTANCE;
    }

}
