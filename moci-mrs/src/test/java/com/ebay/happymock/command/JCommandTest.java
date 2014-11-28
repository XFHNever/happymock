package com.ebay.happymock.command;

import com.beust.jcommander.ParameterException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by jicui on 10/26/14.
 */
@Test
public class JCommandTest {
    @Test(enabled =false)
    void test_start_parse(){
        HappyMockCommand happyMockCommand=new HappyMockCommand();
        happyMockCommand.parse("start", "-ssl", "-p", "8888", "-f", "/opt/env/mock.specs", "--key", "mockany", "-t", "DEBUG");
        happyMockCommand.run();
        Assert.assertNotNull(happyMockCommand.commandStart);
        Assert.assertEquals(happyMockCommand.commandStart.port,8888);
        Assert.assertEquals(happyMockCommand.commandStart.fileAdaptor,new File("/opt/env/mock.specs"));
        Assert.assertEquals(happyMockCommand.commandStart.track,"DEBUG");
    }

    @Test(enabled =false)
    void test_start_parse_(){
        HappyMockCommand happyMockCommand=new HappyMockCommand();
        happyMockCommand.parse("start", "-p", "8888","--key", "mockany", "-t", "DEBUG");
        happyMockCommand.run();
        Assert.assertNotNull(happyMockCommand.commandStart);
        Assert.assertEquals(happyMockCommand.commandStart.port,8888);
        Assert.assertEquals(happyMockCommand.commandStart.fileAdaptor,new File("/opt/env/mock.specs"));
        Assert.assertEquals(happyMockCommand.commandStart.track,"DEBUG");
    }

    @Test
    void test_start_parse_fail_in_negative_port(){
        try{
            HappyMockCommand happyMockCommand=new HappyMockCommand();
            happyMockCommand.parse("start", "-ssl", "-p", "-6666", "-f", "/opt/env/mock.specs", "--key", "mockany","-t","DEBUG");
            happyMockCommand.run();
        }catch(ParameterException p){
            //p.printStackTrace();
            Assert.assertEquals(p.getMessage(),"Parameter -p should be positive (found -6666)");
        }
    }

    @Test
    void test_start_parse_fail_in_invalid_track(){
        try{
            HappyMockCommand happyMockCommand=new HappyMockCommand();
            happyMockCommand.parse("start", "-ssl", "-p", "6666", "-f", "/opt/env/mock.specs", "--key", "mockany","-t","HALO");
            happyMockCommand.run();
        }catch(ParameterException p){
           // p.printStackTrace();
            Assert.assertEquals(p.getMessage(),"Parameter -t should be valid log level (found HALO),support 'debug,info,error,trace'");
        }
    }

    @Test
    void test_stop_parse(){
        HappyMockCommand happyMockCommand=new HappyMockCommand();
        happyMockCommand.parse("stop", "-k", "mockany");
        happyMockCommand.run();
        Assert.assertNotNull(happyMockCommand.commandStop);
        Assert.assertEquals(happyMockCommand.commandStop.authToken,"mockany");
    }

    @Test
    void test_help_parse(){
        HappyMockCommand happyMockCommand=new HappyMockCommand();
        happyMockCommand.parse("--help");
        happyMockCommand.run();
        Assert.assertTrue(happyMockCommand.help);
    }

    @Test
    void test_generate_usage_for_start_parse(){
        HappyMockCommand happyMockCommand=new HappyMockCommand();
        happyMockCommand.parse("start", "-h");
        happyMockCommand.run();
        Assert.assertEquals( happyMockCommand.commandStart.help, true);
    }

}
