package com.ebay.happymock.core.entity;

import com.google.common.base.Strings;
import ognl.Ognl;
import ognl.OgnlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract out-bound response ,the destination may include Socket response or Http Response
 * <p/>
 * User: jicui
 * Date: 14-7-31
 */
public final class Response {
    private static final Logger LOG = LoggerFactory.getLogger(Response.class);
    private static final String NOT_FOUND="#NOT_FOUND#";
    private static final Pattern REGEX_PATTERN = Pattern.compile("(#(method|url)#)|#(json|xml|header|cookie|query|uri)(\\[('[^#]+'|[0-9]+)\\]){1}#");
    private boolean keepAlive;
    private int code;
    private String text;
    private String json;
    private String xml;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();
    private Request request;
    private Long timeDelay;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getContent() {
        if (this.getJson() != null) {
            return this.getJson();
        } else if (this.getXml() != null) {
            return this.getXml();
        } else {
            return this.text;
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String buildDynamicContent(String content) {
        checkNotNull(content);
        Matcher matcher = REGEX_PATTERN.matcher(content);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            String express = group.substring(1, group.length() - 1); //remove the prefix '#' and the surfix '#';
            String value = null;
            try {
                value = (String) Ognl.getValue(express, request);
            } catch (OgnlException e) {
                LOG.error("error when binding dynamic content, expression={} error={}", express, e.getMessage());
                value = NOT_FOUND;
            }
            //if OGNL find the matched value ,replace it ,otherwise replace with expression itself
            if(Strings.isNullOrEmpty(value)){
                value=NOT_FOUND;
            }
            matcher.appendReplacement(stringBuffer, value);

        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public Long getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(Long timeDelay) {
        this.timeDelay = timeDelay;
    }
}
