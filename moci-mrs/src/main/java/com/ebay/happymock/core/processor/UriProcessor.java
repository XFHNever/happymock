package com.ebay.happymock.core.processor;

import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;
import com.google.common.base.Strings;
import org.springframework.util.AntPathMatcher;

/**
 * The mapping matches URLs using the following rules:
 * <p/>
 * ? matches one character
 * * matches zero or more characters
 * ** matches zero or more 'directories' in a path
 *
 * @See {@code AntPathMatcher}
 *
 * User: jicui
 * Date: 14-7-31
 */
public class UriProcessor implements RequestProcessor {
    private String uri;
    private AntPathMatcher antPathMatcher;

    public UriProcessor() {
        this.antPathMatcher = new AntPathMatcher();
    }

    @Override
    public boolean match(Request httpRequest) {
        if(Strings.isNullOrEmpty(httpRequest.getUrl())){
            return false;
        }
        if(antPathMatcher.isPattern(this.getUri())){
            return   antPathMatcher.match(this.getUri().toLowerCase(), httpRequest.getUrl().toLowerCase());
        }else{//match based on plain text
            return this.getUri().equals(httpRequest.getUrl());
        }
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if (mociRequest.getUri() != null) {
            this.setUri(mociRequest.getUri());
            return true;
        }
        return false;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
