package com.jcworks.happymock.core.processor;

import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * XPath processor is try to match the size of founded nodes based on xpath expression,for example as below
 * find the id elements size ,which id 's text content equals to 1  ,and the expected founded id element size is 1
 * {
 *     xpath:{
 *         "/request/parameters/id[text()='1']":"1"
 *     }
 * }
 * find name element match the string functions,which contains 'jason' in it's text content and the expected founded element size is 2
 * {
 *     xpath:{
 *         "/request/parameters/name[contains(text(),'jason')]":"2"
 *     }
 * }
 *
 * find the matched name element which has attribute named "cid" and value equals to 'test',the expected founded node size is 1
 * {
 *     xpath:{
 *         "/request/parameters/name[@cid='test']":"1"
 *     }
 * }
 *
 * Further more the xpath DSL can support multiple expression with 'AND' logical operation  for example
 * the given xml document should match two rules:
 * rule 1: has only 1 "name" element and the name element has attribute named 'cid' and value equals to 'test'
 *  AND
 * rule 2: has only 1 "id" element and the text content equals 2000
 * {
 *     xpath:{
 *         "/request/parameters/name[@cid='test']":"1",
 *         "/request/parameters/id[text()='2000']":"1"
 *     }
 *
 * }
 *
 *
 * User: jicui
 * Date: 14-8-19
 */
public class XPathProcessor implements RequestProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(XPathProcessor.class);
    private ImmutableMap<String,String> xpathMap;
    public static final XPathFactory X_PATH_FACTORY = XPathFactory.newInstance();

    @Override
    public boolean match(Request httpRequest) {
        checkNotNull(xpathMap);
        if(!httpRequest.isXml()){return false;}
        try {
            if (!Strings.isNullOrEmpty(httpRequest.getText())) {
                Document reqDoc=httpRequest.getRequestBodyWrapper().getXml();
                UnmodifiableIterator<String> itor= xpathMap.keySet().iterator();
                boolean matchAll=true;
                while(itor.hasNext()){
                    String express=itor.next();
                    String expectedValue=xpathMap.get(express);
                    String value=null;
                    XPath xPath=X_PATH_FACTORY.newXPath();
                    NodeList nodes = (NodeList)xPath.compile(express).evaluate(reqDoc, XPathConstants.NODESET);
                    value=String.valueOf(nodes.getLength());
                    if(value!=null&&!value.equals(expectedValue)){
                        matchAll=false;
                        break;
                    }
                }
                return matchAll;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOG.debug("can not match the request as the xml format is invalid,json={}", httpRequest.getText());
            return false;
        }
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if (mociRequest.getXpath() != null && !mociRequest.getXpath().isEmpty()) {
            xpathMap = ImmutableMap.copyOf(mociRequest.getXpath());
            return true;
        } else {
            return false;
        }
    }
}
