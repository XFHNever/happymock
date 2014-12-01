package com.jcworks.happymock.core;

import com.alibaba.fastjson.JSON;
import com.jcworks.happymock.core.compiling.v1.RootCompiler;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;
import com.jcworks.happymock.core.composition.ComposeFactory;
import com.jcworks.happymock.core.entity.MociSetting;
import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.core.entity.Response;
import com.jcworks.happymock.persist.model.MockItem;
import com.jcworks.happymock.persist.service.HappyMockPersistService;
import com.jcworks.happymock.core.compiling.v1.Compiler;
import com.jcworks.happymock.persist.service.impl.MongoHappyMockPersistServiceImpl;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.List;

import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Happy mock engine.It includes the function of compiling and mock
 *
 * User: jicui
 * Date: 14-11-3
 */
public class HappyMockEngine {
    private static final Logger LOG = LoggerFactory.getLogger(HappyMockEngine.class);
    private Compiler rootCompiler;
    private HappyMockPersistService happyMockPersistService;

    private HappyMockEngine(Compiler rootCompiler, HappyMockPersistService happyMockPersistService) {
        this.rootCompiler = checkNotNull(rootCompiler);
        this.happyMockPersistService = checkNotNull(happyMockPersistService);
    }

    /**
     * Compile the happy mock dsl,if success ,return http code 200 ,otherwise return error 400 with specific msg in body.
     */
    public Response compile(String dsl) throws Exception {
        LOG.debug("start compile dsl={}", dsl);
        Response response = new Response();
        String content = URLDecoder.decode(dsl, "UTF-8");
        response.getHeaders().put("Content-Type", "application/json");
        String errorStr = "{\"message\":\"" + "success" + "\"}";
        try {
            rootCompiler.compile(content);
            response.setCode(200);
        } catch (CompileException e) {
            response.setCode(400);
            errorStr = "{\"message\":\""+e.getMessage().replaceAll("\"","'")+"\"}";
        }
        response.setJson(errorStr);
        LOG.debug("End compile request");
        return response;
    }

    /**
     * Mock the inbound request with pre-defined happy mock response.Internally this method will try to find the
     * corresponding mock specs and using the mock specs to render response.Otherwise(if mock specs not found),then return
     * http code=404
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Response mock(Request request) throws Exception {
        LOG.info("start mock request at url={} under utx={} ", request.getUrl(), request.getUsername());
        Response response = new Response();
        //load all mock specs under the user ctx
        List<MockItem> mockItemList = happyMockPersistService.find(request.getUsername());
        List<MociSetting> mociSettings = Lists.transform(mockItemList, new Function<MockItem, MociSetting>() {
            @Override
            public MociSetting apply(MockItem input) {
                MociSetting mociSetting = JSON.parseObject(input.getContent(), MociSetting.class);
                ComposeFactory.getInstance().composeSetting(mociSetting);
                return mociSetting;
            }
        });
        //find the best match mock specs
        Optional<MociSetting> bestmatch = Optional.absent();
        for (MociSetting mociSetting : mociSettings) {
            if (mociSetting.match(request)) {
                LOG.debug("find the matched DLS=" + mociSetting.getRequest().toString());
                if (!bestmatch.isPresent() || bestmatch.get().matched() < mociSetting.matched()) {
                    bestmatch = of(mociSetting);//reassign the best match
                }
            }
        }

        //write mock specs to response
        if (bestmatch.isPresent()) {
            response.setRequest(request);
            bestmatch.get().write(response);
            //time delay is in millseconds unit
            if (response.getTimeDelay() != null) {
                Thread.sleep(response.getTimeDelay());
            }
        } else {
            LOG.debug("find no matched DLS,return http code 404");
            response.setCode(404);//does not find matched settings
        }
        //set keep alive no matter we find mock specs or not
        response.setKeepAlive(request.isKeepAlive());
        return response;
    }



    public static class HappyMockEngineBuilder {
        private static final Compiler DEFAULT_COMPILER = new RootCompiler();
        private static final HappyMockPersistService DEFAULT_ADAPTOR = new MongoHappyMockPersistServiceImpl();
        private Optional<Compiler> rootCompiler = of(DEFAULT_COMPILER);
        private Optional<HappyMockPersistService> happyMockPersistService = of(DEFAULT_ADAPTOR);

        public static HappyMockEngineBuilder happyMockEngine() {
            return new HappyMockEngineBuilder();
        }

        /**
         * set up persist layer used by searching mock specs
         *
         * @param happyMockPersistService
         * @return
         */
        public HappyMockEngineBuilder withPersistAdaptor(HappyMockPersistService happyMockPersistService) {
            this.happyMockPersistService = of(happyMockPersistService);
            return this;
        }

        /**
         * set up compile used by happy mock engine
         *
         * @param rootCompiler
         * @return
         */
        public HappyMockEngineBuilder withCompilerTree(Compiler rootCompiler) {
            this.rootCompiler = of(rootCompiler);
            return this;
        }

        public HappyMockEngine build() {
            return new HappyMockEngine(rootCompiler.get(), happyMockPersistService.get());
        }

    }
}
