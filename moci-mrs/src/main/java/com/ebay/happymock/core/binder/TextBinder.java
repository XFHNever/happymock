package com.ebay.happymock.core.binder;

import com.ebay.happymock.core.entity.MociResponse;
import com.ebay.happymock.core.entity.Response;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-7-31
 */
public class TextBinder implements ResponseBinder {
    private String text;

    @Override
    public void write(Response response) {
        checkNotNull(text);
        response.setText(text);
    }

    @Override
    public boolean bind(MociResponse response) {
        if(!Strings.isNullOrEmpty(response.getText())){
           this.setText(response.getText());
           return true;
        }
        return false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
