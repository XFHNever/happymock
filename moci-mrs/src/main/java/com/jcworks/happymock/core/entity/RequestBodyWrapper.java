package com.jcworks.happymock.core.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.HappyMockUtility;
import com.google.common.base.Strings;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.JsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

/**
 * User: jicui
 * Date: 14-10-20
 */
public final class RequestBodyWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(RequestBodyWrapper.class);
    private String content;
    private JsonProvider defaultJsonProvider = Configuration.defaultConfiguration().getProvider();
    private DocumentBuilder defaultXmlDocBuilder = HappyMockUtility.getXmlDocumentBuilder();
    private XPath xPath = XPathFactory.newInstance().newXPath();
    private JSONObject json;
    private Document xml;

    public RequestBodyWrapper(String content) {
        this.content = content;
        if(!Strings.isNullOrEmpty(content)){
            try {
                json = JSON.parseObject(this.content);
            } catch (Exception e) {
                LOG.debug("invalid json format error={}",e.getMessage());
            }
            //try to parse the xml in the case of content entity is not json format
            if (json == null) {
                try {
                    xml = defaultXmlDocBuilder.parse(new InputSource(new StringReader(content)));
                } catch (Exception e) {
                    LOG.debug("invalid xml format error={}",e.getMessage());
                }
            }
        }
    }

    /**
     * Extract a json value from jsonObject by jsonpath expression
     *
     * @param jsonPathExpression
     * @return the extracted json value,if the extraction process fails or the entity body does not match json format,return "#INVALID_JSON#"
     */
    public String getJsonValue(String jsonPathExpression) {
        String r = null;
        if (isJson()) {
            try {
                r = String.valueOf(JsonPath.read(json, jsonPathExpression));
            } catch (Exception e) {
                e.printStackTrace();
                LOG.debug("can not parse json path={} error={}",jsonPathExpression,e.getMessage());
            }
        }
        return r;
    }

    /**
     * Extract the xml value from xml document by xpath expression
     *
     * @param xpathExpression
     * @return the extracted xml value,if the extraction process fails or the entity body does not match xml format,return "#INVALID_XML#"
     */
    public String getXmlValue(String xpathExpression) {
        String r = null;
        if (isXml()) {
            try {
                XPathExpression expr = this.xPath.compile(xpathExpression);
                r = (String) expr.evaluate(this.xml, XPathConstants.STRING);
            } catch (Exception e) {
                LOG.debug("can not parse xml path={} error={}",xpathExpression,e.getMessage());
            }
        }
        return r;
    }

    /**
     * Return if the body entity is a json string
     *
     * @return
     */
    public boolean isJson() {
        return json != null;
    }

    /**
     * Return if the body entity is a xml string
     *
     * @return
     */
    public boolean isXml() {
        return this.xml != null;
    }

    /**
     * Get Json object
     *
     * @return
     */
    public JSONObject getJson() {
        return json;
    }


    /**
     * Get xml document
     *
     * @return
     */
    public Document getXml() {
        return xml;
    }

}
