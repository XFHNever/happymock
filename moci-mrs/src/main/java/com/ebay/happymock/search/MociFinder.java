package com.ebay.happymock.search;

import com.alibaba.fastjson.JSON;
import com.ebay.happymock.core.composition.ComposeFactory;
import com.ebay.happymock.core.entity.MociSetting;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.persist.HappyMockPersistServiceManager;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import static com.google.common.base.Optional.*;
/**
 * User: jicui
 * Date: 14-8-21
 */
@Deprecated
public class MociFinder {
    private static final Logger LOG= LoggerFactory.getLogger(MociFinder.class);
    private static class MociFinderHolder{
        private static final MociFinder INSTANCE=new MociFinder();
    }
    private MociFinder() {}
    public static MociFinder getInstance(){
        return MociFinderHolder.INSTANCE;
    }

    public Optional<MociSetting> firstMatch(Request request){
        List<MociSetting> mociSettingList=loadAll();
        for(MociSetting mociSetting:mociSettingList){
            if(mociSetting.match(request)){
                LOG.debug("find the matched DLS="+mociSetting.getRequest().toString());
                return of(mociSetting);
            }
        }
        LOG.debug("find no matched DSL");
        return absent();
    }

    public Optional<MociSetting> bestMatch(Request request){
        List<MociSetting> mociSettingList= loadWithDomainAndResource(request.getUsername());
        LOG.debug("url: " + request.getUrl());
        Optional<MociSetting> bestmatch=Optional.absent();
        for(MociSetting mociSetting:mociSettingList){
            if(mociSetting.match(request)){
                LOG.debug("find the matched DLS="+mociSetting.getRequest().toString());
                if(!bestmatch.isPresent()||bestmatch.get().matched()<mociSetting.matched()){
                    bestmatch=of(mociSetting);//reassign the best match
                }
            }
        }
        if(!bestmatch.isPresent()){
            LOG.debug("find no matched DLS");
        }
        return bestmatch;
    }

    private List<MociSetting> loadAll(){
        List<MociSetting> mociSettingList= Lists.newArrayList();
        InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("moci.dsl");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuffer sb=new StringBuffer();
        try {
            line = reader.readLine();
            for (; line != null; line = reader.readLine())
            {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] dsls=sb.toString().split("---");

        for(String dsl:dsls){
            //TODO:add compile DSL process
            MociSetting mociSetting= JSON.parseObject(dsl, MociSetting.class);
            //compose processor and binder to settings
            ComposeFactory.getInstance().composeSetting(mociSetting);
            mociSettingList.add(mociSetting);
        }
        return mociSettingList;
    }

    private List<MociSetting> loadWithDomainAndResource(String username, String domainName, String resourceName) {
        List<MociSetting> mociSettingList = Lists.newArrayList();
        List<String> dsls = HappyMockPersistServiceManager.getInstance().getDSLs(username, domainName, resourceName);
        for (String dsl : dsls) {
            MociSetting mociSetting = JSON.parseObject(dsl, MociSetting.class);
            //compose processor and binder to settings
            ComposeFactory.getInstance().composeSetting(mociSetting);
            mociSettingList.add(mociSetting);
        }

        return mociSettingList;
    }

    private List<MociSetting> loadWithDomainAndResource(String username) {
        List<MociSetting> mociSettingList = Lists.newArrayList();
        List<String> dsls = HappyMockPersistServiceManager.getInstance().getDSLsByUser(username);
        for (String dsl : dsls) {
            MociSetting mociSetting = JSON.parseObject(dsl, MociSetting.class);
            //compose processor and binder to settings
            ComposeFactory.getInstance().composeSetting(mociSetting);
            mociSettingList.add(mociSetting);
        }

        return mociSettingList;
    }
}
