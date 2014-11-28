package com.ebay.happymock.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * User: jicui
 * Date: 14-10-27
 */
@Parameters(commandDescription = "Happy Mock CLI")
public class HappyMockCommand implements ICommand {
    private static final String PROGRAM_NAME = "java -jar happy_mock_happy_work_<version>.jar";
    @Parameter(names = {"-h", "--help"}, help = true)
    protected boolean help;
    protected CommandStart commandStart;
    protected CommandStop commandStop;
    protected JCommander jCommander;

    public HappyMockCommand() {
        this.help = false;
        this.jCommander = new JCommander(this);
        this.jCommander.setProgramName(PROGRAM_NAME);
        commandStart = new CommandStart(jCommander);
        commandStop = new CommandStop(jCommander);
        this.jCommander.addCommand(CommandStart.COMMAND_KEY, commandStart);
        this.jCommander.addCommand(CommandStop.COMMAND_KEY, commandStop);
    }

    void parse(String... args) {
        this.jCommander.parse(args);
    }

    @Override
    public void run() {
        if (help) {
            jCommander.usage();
            return;
        }
        if (jCommander.getParsedCommand().equals(CommandStart.COMMAND_KEY)) {
            commandStart.run();
        }
        if (jCommander.getParsedCommand().equals(CommandStop.COMMAND_KEY)) {
            commandStop.run();
        }
    }


}
