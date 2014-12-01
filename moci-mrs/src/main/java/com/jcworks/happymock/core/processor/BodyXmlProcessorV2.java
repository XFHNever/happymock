package com.jcworks.happymock.core.processor;

import com.jcworks.happymock.core.HappyMockUtility;
import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: jicui
 * Date: 14-10-17
 */
public class BodyXmlProcessorV2 implements RequestProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(BodyXmlProcessorV2.class);
    private Document document;

    @Override
    public boolean match(Request httpRequest) {
        checkNotNull(document);
        if(!httpRequest.isXml()){return false;}
        if (!Strings.isNullOrEmpty(httpRequest.getText())) {
            try {
                Document reqDocument=httpRequest.getRequestBodyWrapper().getXml();
                //com.sun.org.apache.xerces.internal.dom.DeferredDocumentImpl d1=(com.sun.org.apache.xerces.internal.dom.DeferredDocumentImpl)reqDocument;
                //return d1.isEqualNode(document);
                return reqDocument.isEqualNode(document);//TODO:can not match
            } catch (Exception e) {
                LOG.debug("can not match the request as the xml format is invalid,xml={}", httpRequest.getText(),e);
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if (null!=mociRequest.getBody()&&mociRequest.getBody().getXml() != null) {
            try {
                document= HappyMockUtility.getXmlDocumentBuilder().parse(new InputSource(new StringReader(mociRequest.getBody().getXml())));
            } catch (Exception e) {
                LOG.debug("can not bind the request as the xml format is invalid,xml={}", mociRequest.getBody().getXml());
                return false;
            }
            return true;
        }
        return false;
    }
}
