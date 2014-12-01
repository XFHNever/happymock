package com.jcworks.happymock.core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * User: jicui
 * Date: 14-10-17
 */
public class HappyMockUtility {
    private static class DocumentBuilderHolder {
        private static final DocumentBuilder DOCUMENT_BUILDER;
        static {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setCoalescing(true);
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setIgnoringComments(true);
            try {
                DOCUMENT_BUILDER = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static DocumentBuilder getXmlDocumentBuilder() {
        return DocumentBuilderHolder.DOCUMENT_BUILDER;
    }
}
