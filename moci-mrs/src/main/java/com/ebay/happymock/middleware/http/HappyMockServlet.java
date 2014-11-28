package com.ebay.happymock.middleware.http;

import com.ebay.happymock.core.compiling.v1.RootCompiler;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import com.ebay.happymock.core.entity.MociSetting;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.entity.Response;
import com.ebay.happymock.search.MociFinder;
import com.google.common.base.Optional;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import static com.ebay.happymock.adaptor.HttpAdaptor.fromHttpRequest;
import static com.ebay.happymock.adaptor.HttpAdaptor.toHttpResponse;

/**
 * User: jicui
 * Date: 14-9-3
 */
@Deprecated
public class HappyMockServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(HappyMockServlet.class);
    private static final String COMPILING_END_POINT = "/compile";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processMockRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        precessRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processMockRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processMockRequest(req, resp);
    }

    private void precessRequest(HttpServletRequest req, HttpServletResponse resp) {
        if (isCompilingRequest(req)) {
            processCompileRequest(req, resp);
        } else {
            processMockRequest(req, resp);
        }
    }

    /**
     * Process Mock request
     *
     * @param req
     * @param resp
     */
    private void processMockRequest(HttpServletRequest req, HttpServletResponse resp) {
        Request request = fromHttpRequest(req);
        Response response = new Response();
        Optional<MociSetting> mociSetting = MociFinder.getInstance().bestMatch(request);
        if (mociSetting.isPresent()) {
            mociSetting.get().write(response);
            toHttpResponse(resp, response);
        } else {
            resp.setStatus(404);//does not find matched settings
        }
    }

    /**
     * Process Compile request with URI=/compile
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void processCompileRequest(HttpServletRequest req, HttpServletResponse resp){
        String content=null;
        try {
            content= CharStreams.toString(req.getReader());
            content = URLDecoder.decode(content, "UTF-8");
            RootCompiler rootCompiler= new RootCompiler();
            rootCompiler.compile(content);
            resp.setStatus(200);

            OutputStream ps = resp.getOutputStream();
            resp.setHeader("Content-Type","application/json");
            String errorStr="{\"message\":\""+"success"+"\"}";
            ps.write(errorStr.getBytes("UTF-8"));
        } catch (CompileException e) {
            resp.setStatus(400);
            LOG.info("can not parse the given dsl={}", content);
            try {
                OutputStream ps = resp.getOutputStream();
                resp.setHeader("Content-Type","application/json");
                String errorStr="{\"message\":\""+e.getMessage().replaceAll("\"","'")+"\"}";
                ps.write(errorStr.getBytes("UTF-8"));
            } catch (IOException e1) {
                LOG.error("can not write content to response");
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCompilingRequest(HttpServletRequest req) {
        return "post".equalsIgnoreCase(req.getMethod()) && req.getRequestURI().equalsIgnoreCase(COMPILING_END_POINT);
    }
}
