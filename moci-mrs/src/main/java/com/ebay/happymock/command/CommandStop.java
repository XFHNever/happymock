package com.ebay.happymock.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * User: jicui
 * Date: 14-10-27
 */
@Parameters(commandDescription = "stop mock server")
public class CommandStop implements ICommand{
    protected static final String COMMAND_KEY="stop";
    @Parameter(names = {"-k","--key"}, description="specific the auth token to stop the mock instance",required = true)
    protected String authToken;
    protected JCommander jCommander;

    public CommandStop(JCommander jCommander) {
        this.jCommander = jCommander;
    }
    @Override
    public void run() {
        //stop happy mock server
    }
}
