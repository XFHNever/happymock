package com.jcworks.happymock.core.binder;

import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.Response;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-9-3
 */
public class BodyTextBinder implements ResponseBinder {
    private static final Logger LOG = LoggerFactory.getLogger(BodyTextBinder.class);
    private String text;
    @Override
    public void write(Response response) {
        checkNotNull(text);
        response.setText(response.buildDynamicContent(text));
    }

    @Override
    public boolean bind(MociResponse response) {
        if(null!=response.getBody()&&!Strings.isNullOrEmpty(response.getBody().getText())){
            this.text=response.getBody().getText();
            return true;
        }
        return false;
    }
}
