package com.ebay.happymock.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import com.ebay.happymock.command.validator.LOGLevelValidator;
import com.ebay.happymock.middleware.http.netty.MrsRunnerBuilder;

import java.io.File;

import static com.ebay.happymock.middleware.http.netty.MrsRunnerBuilder.mrsRunner;
import static com.google.common.base.Optional.fromNullable;

/**
 * User: jicui
 * Date: 14-10-27
 */
@Parameters(commandDescription = "start mock server")
public class CommandStart implements ICommand {
    protected static final String COMMAND_KEY = "start";
    @Parameter(names = {"-s", "-ssl", "--https"}, description = "(DO NOT SUPPORT NOW) mock server start as https server")
    protected boolean ssl = false;
    @Parameter(names = {"-p", "--port"}, description = "mock server http port", validateWith = PositiveInteger.class)
    protected int port = 6666;
    @Parameter(names = {"-f", "--file"}, description = "specify a path to the mock specs")
    protected File fileAdaptor;
    @Parameter(names = {"-m", "--mongo"}, description = "specific a path to mongoDB connect properties")
    protected File mongoAdaptor;
    @Parameter(names = {"-t", "--track"}, description = "set up mock server log level,support 'INFO,DEBUG,ERROR,TRACE'", validateWith = LOGLevelValidator.class)
    protected String track = "INFO";
    @Parameter(names = {"-k", "--key"}, description = "(DO NOT SUPPORT NOW) specify the auth token to stop the mock instance")
    protected String authToken;
    @Parameter(names = {"-h", "--help"}, help = true)
    protected boolean help;

    protected JCommander jCommander;

    public CommandStart(JCommander jCommander) {
        this.jCommander = jCommander;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public File getFileAdaptor() {
        return fileAdaptor;
    }

    public void setFileAdaptor(File fileAdaptor) {
        this.fileAdaptor = fileAdaptor;
    }

    public File getMongoAdaptor() {
        return mongoAdaptor;
    }

    public void setMongoAdaptor(File mongoAdaptor) {
        this.mongoAdaptor = mongoAdaptor;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    @Override
    public void run() {
        if (help) {
            jCommander.usage(COMMAND_KEY);
            return;
        }
        //start mrs
        try {
            if (fromNullable(this.fileAdaptor).isPresent()) {
                mrsRunner().withFileAdaptor(this.fileAdaptor).withTraceLevel(this.track).run(port);
            }else if(fromNullable(this.mongoAdaptor).isPresent()) {
                mrsRunner().withMongoAdaptor(this.mongoAdaptor).withTraceLevel(this.track).run(port);
            }else{//use default mongo connnect instance
                mrsRunner().withMongoAdaptor().run(port);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
