package com.ebay.happymock.command.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * User: jicui
 * Date: 14-10-27
 */
public class LOGLevelValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if(!value.toUpperCase().equals("INFO")&&!value.toUpperCase().equals("DEBUG")&&!value.toUpperCase().equals("ERROR")&&!value.toUpperCase().equals("TRACE")){
            throw new ParameterException("Parameter " + name + " should be valid log level (found " + value +"),support 'debug,info,error,trace'");
        }
    }
}
