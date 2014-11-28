package com.ebay.happymock.core.entity;

import java.util.Map;

/**
 * User: jicui
 * Date: 14-7-31
 */
public final class MociRequest {
    private String uri;
    private String method;
    private Map<String,String> cookies;
    private Map<String,String> headers;
    private MociBody body;
    private Map<String,String> xpath;
    private Map<String,String> jsonpath;
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }



    public Map<String, String> getXpath() {
        return xpath;
    }

    public void setXpath(Map<String, String> xpath) {
        this.xpath = xpath;
    }

    public Map<String, String> getJsonpath() {
        return jsonpath;
    }

    public void setJsonpath(Map<String, String> jsonpath) {
        this.jsonpath = jsonpath;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
