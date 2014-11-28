package com.ebay.happymock.core.compiling.v1.exception;

/**
 * User: jicui
 * Date: 14-8-21
 */
public enum CompileErrorCode {
    UNRECOGNIZED_KEYWORD("happymock.unrecognized.keyword","the keyword is unrecognized,keyword={0}"),
    UNPARSEABLE_FORMAT("happymock.unparseable.format","the pass in domain language is not parsable, msg={0}"),
    MISSING_COMPILED_KEYWORD("happymock.missing.compiled.keyword","specific keyword is missing compiled,missingKeyword={0} ,current compiler={1},expected processed={2}"),
    EMPTY_DSL_BODY("happymock.empty.dsl.entity","the given dsl body is empty,dsl={0}"),
    INVALID_ANT_STYLE_URI("happymock.invalid.antstyle.path","the path is an invalid ant path .Valid path should follow the ant style path or start with '/',path={0}"),
    INVALID_HEADER_NAME_VALUE_PARE("happymock.invalid.head.name.value.pair","the given head name value pair is in valid name={0} value={1}"),
    INVALID_COOKIE_NAME_VALUE_PARE("happymock.invalid.cookie.name.value.pair","the given cookie name value pair is in valid name={0} value={1}"),
    INVALID_KEYWORD_OCCURANCE("happymock.invalid.occurance","the keyword has incorrect occurance,keyword={0}, occurance={1}"),
    INVALID_BODY_ELEMENT("happymock.invalid.body.element","the body element occurance is invalid text,json,and xml should only select one"),
    INVALID_XPATH("happymock.invalid.xpath","the xpath express is invalid,xpath={0}"),
    INVALID_JSONPATH("happymock.invalid.jsonpath","the json express is invalid,jsonpath={0}"),
    INVALID_STATUS_CODE("happymock.invalid.status","the status code is invalid,status={0}"),
    INVALID_TIME_DELAY_CODE("happymock.invalid.time.delay","the time delay value is invalid it should between 0 and 5 minutes,timeDelay={0}"),
    INVALID_TEXT_VALUE("happymock.invalid.text.value","the response text value is invalid,value={0}"),
    INVALID_HTTP_METHOD_VALUE("happymock.invalid.httpmethod.value","the request http method is invalid,method={0}"),
    INVALID_BODY_XML_VALUE("happymock.invalid.body.xml.value","the response body xml is invalid,please remove any delimeter characters"),
    INVALID_EXPRESSION_VALUE("happymock.invaid.expression.value","the xpath or jsonpath expression value should be a number,value={0}");
    private String code;
    private String message;

    private CompileErrorCode(String code,String message) {
        this.code=code;
        this.message=message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
