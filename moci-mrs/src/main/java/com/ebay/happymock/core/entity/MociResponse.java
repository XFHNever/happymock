package com.ebay.happymock.core.entity;

import java.util.Map;

/**
 * User: jicui
 * Date: 14-7-31
 */
public final class MociResponse {
    private String text;
    private Map<String,String> cookies;
    private Map<String,String> headers;
    private MociBody body;
    private Integer status;
    private Long timeDelay;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public MociBody getBody() {
        return body;
    }

    public void setBody(MociBody body) {
        this.body = body;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(Long timeDelay) {
        this.timeDelay = timeDelay;
    }
}
