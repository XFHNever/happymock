package com.jcworks.happymock.core.binder;

import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-10-22
 */
public class TimeDelayBinder implements ResponseBinder{
    private static final Logger LOG = LoggerFactory.getLogger(TimeDelayBinder.class);
    private Long timeDelay;
    @Override
    public void write(Response response) {
        checkNotNull(timeDelay);
        response.setTimeDelay(timeDelay);
    }

    @Override
    public boolean bind(MociResponse response) {
        if(response.getTimeDelay()!=null){
            this.timeDelay=Long.valueOf(response.getTimeDelay());
            return true;
        }
        return false;
    }
}
