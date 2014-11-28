package com.ebay.happymock.middleware.http.netty;

import com.ebay.happymock.core.HappyMockEngine;
import com.ebay.happymock.persist.dao.impl.MongoConn;
import com.ebay.happymock.persist.service.impl.FileHappyMockPersistServiceImpl;
import com.ebay.happymock.persist.service.impl.MongoHappyMockPersistServiceImpl;
import io.netty.util.ResourceLeakDetector;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

import static com.ebay.happymock.core.HappyMockEngine.HappyMockEngineBuilder.happyMockEngine;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code MrsRunnerBuilder} is a single instance ,which hold {@code MrsRunner} instance and {@code HappyMockEngine} instance
 * Client can adopt this class as example below:
 *
 * MrsRunnerBuilder.mrsRunner().withFileAdaptor(..).run(6666);//to start up a mrs server
 * MrsRunnerBuilder.mrsRunner().getHappyMockEngine();//to get a HappyMockEngine
 *
 * Note:MrsRunnerBuilder.mrsRunner() will only return a single instance in JVM
 *
 * User: jicui
 * Date: 14-11-4
 */
public final class MrsRunnerBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(MrsRunnerBuilder.class);
    //static class to hold the singleton
    private static class MrsRunnerBuilderHolder{
        private static final MrsRunnerBuilder BUILDER=new MrsRunnerBuilder();
    }

    private MrsRunner mrsRunner;
    private HappyMockEngine happyMockEngine;

    private MrsRunnerBuilder(){
        mrsRunner=new MrsRunner();
    }

    public HappyMockEngine getHappyMockEngine() {
        return happyMockEngine;
    }

    /**
     * This method will only return a single instance of {@code MrsRunnerBuilder}
     * Client side can using static import to get the running instance via below:
     *
     * mrsRunner()
     *
     * @return
     */
    public static MrsRunnerBuilder mrsRunner() {
        return MrsRunnerBuilderHolder.BUILDER;
    }

    /**
     * Create a new {@code HappyMockEngine} with loading mock specs from given {@code File}
     *
     * @param file local mock specs file
     */
    public MrsRunnerBuilder withFileAdaptor(File file){
        LOG.info("load happy mock specs from file " + file.getAbsolutePath());
        happyMockEngine=happyMockEngine().withPersistAdaptor(new FileHappyMockPersistServiceImpl(file)).build();
        return this;
    }

    /**
     *
     * Init environment set up for Mongo DB
     * Create a new {@code HappyMockEngine} with loading mock specs from mongodb
     *
     * @param file the mongo config file
     */
    public MrsRunnerBuilder withMongoAdaptor(File file){
        MongoConn.getInstance().init(file);
        happyMockEngine=happyMockEngine().withPersistAdaptor(new MongoHappyMockPersistServiceImpl()).build();
        return this;
    }
    /**
     * Create a new {@code HappyMockEngine} with default mongo configuration.
     * The back end persist layer is based on mongo db connection
     * the connection string is based on default loaded from /meta/mongo.properties
     */
    public MrsRunnerBuilder withMongoAdaptor(){
        MongoConn.getInstance().init();
        happyMockEngine=happyMockEngine().build();
        return this;
    }

    public MrsRunnerBuilder withTraceLevel(String level){
        LogManager.getLogger("com.ebay.happymock").setLevel(org.apache.log4j.Level.toLevel(level));
        LOG.info("---"+level+"----opened---");
        return this;
    }

    public void run(int port) throws Exception {
        checkNotNull(happyMockEngine);
        mrsRunner.run(port);
    }

}


