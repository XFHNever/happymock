package com.ebay.happymock.core.entity;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract inbound Request class,which represent all in bound request from http or socket
 * <p/>
 * User: jicui
 * Date: 14-7-31
 */
public final class Request {
    private static final String EMPTY_BODY = "#EMPTY_BODY#";
    private boolean keepAlive;
    private String url;//the full url including uri+query params
    private String uri;
    private Map<String, String> queryParam;
    private String method;
    private Map<String, String> cookies = new HashMap<String, String>();
    private Map<String, String> headers = new HashMap<String, String>();
    private String text;
    private String username;
    private RequestBodyWrapper requestBodyWrapper;

    public Request() {
        //set empty request body at initialize
        this.setRequestBodyWrapper(new RequestBodyWrapper(null));
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.setRequestBodyWrapper(new RequestBodyWrapper(text));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void setHeader(String name, String value) {
        throw new UnsupportedOperationException("not support");
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public void setCookie(String name, String value) {
        throw new UnsupportedOperationException("not support");
    }

    public String getJson(String jsonPath) {
        if (requestBodyWrapper != null) {
            return this.requestBodyWrapper.getJsonValue(jsonPath);
        }
        return EMPTY_BODY;
    }

    public void setJson(String name, String value) {
        throw new UnsupportedOperationException("not support");
    }

    public String getXml(String xpathExpression) {
        if (requestBodyWrapper != null) {
            return this.requestBodyWrapper.getXmlValue(xpathExpression);
        }
        return EMPTY_BODY;
    }

    public void setXml(String name, String value) {
        throw new UnsupportedOperationException("not support");
    }

    public String getQuery(String name) {
        return queryParam.get(name);
    }

    public void setQuery(String name, String value) {
        throw new UnsupportedOperationException("not support");
    }

    public String getUri(int i) {
        checkNotNull(this.uri);
        String[] uris = this.uri.split("/");
        if (i < uris.length) {
            return uris[i];
        } else {
            return "";
        }
    }

    public void setUri(int i, String value) {
        throw new UnsupportedOperationException("not support");
    }

    public RequestBodyWrapper getRequestBodyWrapper() {
        return requestBodyWrapper;
    }

    public void setRequestBodyWrapper(RequestBodyWrapper requestBodyWrapper) {
        this.requestBodyWrapper = requestBodyWrapper;
    }

    public boolean isJson() {
        return this.requestBodyWrapper.isJson();
    }

    public boolean isXml() {
        return this.requestBodyWrapper.isXml();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(Map<String, String> queryParam) {
        this.queryParam = queryParam;
    }
    //------------------------RequestBuilder ------------------
    public static final class RequestBuilder {

        public static RequestBuilder newRequest() {
            return new RequestBuilder();
        }

        private final Request request;

        public RequestBuilder() {
            this.request = new Request();
        }

        public RequestBuilder withURL(String url) {
            request.setUrl(checkNotNull(url));
            return this;
        }

        public RequestBuilder withURI(String uri) {
            request.setUri(checkNotNull(uri));
            return this;
        }

        public RequestBuilder withMethod(String method) {
            request.setMethod(checkNotNull(method));
            return this;
        }

        public RequestBuilder withKeepAlive() {
            request.setKeepAlive(Boolean.TRUE);
            return this;
        }

        public RequestBuilder withQueryParam(ImmutableMap queryParam) {
            request.setQueryParam(queryParam);
            return this;
        }

        public RequestBuilder withCookies(ImmutableMap cookieParam) {
            request.setCookies(cookieParam);
            return this;
        }

        public RequestBuilder withHeaders(ImmutableMap headersParam) {
            request.setHeaders(headersParam);
            return this;
        }

        public RequestBuilder withUserCtx(String ctx) {
            request.setUsername(ctx);
            return this;
        }

        public RequestBuilder withBody(String body) {
            request.setText(body);
            return this;
        }

        public Request build() {
            return request;
        }
    }
}
