package com.ebay.happymock.core.composition;

import com.ebay.happymock.core.binder.ResponseBinder;
import com.ebay.happymock.core.entity.MociSetting;
import com.ebay.happymock.core.processor.RequestProcessor;

/**
 * User: jicui
 * Date: 14-8-12
 */
public class ComposeFactory {
    private ComposeFactory() {
    }
    private static class ComposeFactoryHolder{
        private static final ComposeFactory FACTORY_INSTANCE=new ComposeFactory();
    }
    public static ComposeFactory getInstance(){
        return ComposeFactoryHolder.FACTORY_INSTANCE;
    }

    public void composeSetting(MociSetting mockiSetting){
        //bind processor
        for(RequestProcessor requestProcessor: RequestProcessorFactory.getInstance().getProcessList()){
            mockiSetting.accept(requestProcessor);
        }
        //bind binder
        for(ResponseBinder responseBinder: ResponseBinderFactory.getInstance().getBinderList()){
            mockiSetting.accept(responseBinder);
        }
    }
}
