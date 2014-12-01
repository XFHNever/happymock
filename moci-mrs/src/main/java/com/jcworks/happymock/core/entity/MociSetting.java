package com.jcworks.happymock.core.entity;

import com.jcworks.happymock.core.binder.ResponseBinder;
import com.jcworks.happymock.core.processor.RequestProcessor;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * User: jicui
 * Date: 14-7-31
 */
public final class MociSetting {
    private MociRequest request;
    private MociResponse response;
    private transient List<RequestProcessor> processorList;
    private transient List<ResponseBinder> binderList;
    public MociRequest getRequest() {
        return request;
    }

    public void setRequest(MociRequest request) {
        this.request = request;
    }

    public MociResponse getResponse() {
        return response;
    }

    public void setResponse(MociResponse response) {
        this.response = response;
    }

    public void accept(RequestProcessor requestProcessor){
         if(requestProcessor.bind(this.getRequest())){
            if(processorList==null){
                processorList= Lists.newArrayList(requestProcessor);
            }else{
                processorList.add(requestProcessor);
            }
         }
    }

    public void accept(ResponseBinder responseBinder){
        if(responseBinder.bind(this.getResponse())){
            if(binderList==null){
                binderList= Lists.newArrayList(responseBinder);
            }else{
                binderList.add(responseBinder);
            }
        }
    }

    public boolean match(Request request){
        Iterator<RequestProcessor> itor=this.processorList.iterator();
        while(itor.hasNext()){
            RequestProcessor requestProcessor=itor.next();
            if(!requestProcessor.match(request)){
               return false;
            }
        }
        return true;
    }

    public void write(Response response){
        Iterator<ResponseBinder> itor=this.binderList.iterator();
        while(itor.hasNext()){
            ResponseBinder responseBinder=itor.next();
            responseBinder.write(response);
        }
    }

     public  int  matched(){
        return this.processorList==null?0:this.processorList.size();
    }

    @Override
    public String toString() {
        return "MociSetting{" +
                "response=" + response +
                ", request=" + request +
                '}';
    }
}
