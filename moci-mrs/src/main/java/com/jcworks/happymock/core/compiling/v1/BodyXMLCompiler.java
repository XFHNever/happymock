package com.jcworks.happymock.core.compiling.v1;

import com.jcworks.happymock.core.HappyMockUtility;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import java.io.StringReader;

/**
 * User: jicui
 * Date: 14-8-26
 */
public class BodyXMLCompiler implements Compiler {
    private static final DocumentBuilder DOCUMENT_BUILDER=HappyMockUtility.getXmlDocumentBuilder();

    @Override
    public void compile(String dsl) throws CompileException {
        if(dsl.indexOf("\n")!=-1){
            throw new CompileException(CompileErrorCode.INVALID_BODY_XML_VALUE);
        }
        try{
            StringReader sr = new StringReader(dsl);
            DOCUMENT_BUILDER.parse(new InputSource(sr));
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
    }

    @Override
    public String getKeyword() {
        return "xml";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
