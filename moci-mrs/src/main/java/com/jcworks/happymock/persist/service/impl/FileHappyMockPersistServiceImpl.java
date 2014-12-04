package com.jcworks.happymock.persist.service.impl;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import com.jcworks.happymock.persist.model.MockItem;
import com.jcworks.happymock.persist.service.HappyMockPersistService;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * User: jicui
 * Date: 14-11-3
 */
public class FileHappyMockPersistServiceImpl implements HappyMockPersistService {
     private File dslFile;

    public FileHappyMockPersistServiceImpl(File dslFile) {
        checkState(dslFile.exists(),"Local DSL file does not exist");
        this.dslFile = dslFile;
    }

    @Override
    public List<MockItem> findAllActive() {
        throw new UnsupportedOperationException("not support now");
    }

    @Override
    public List<MockItem> findActive(String domainName) {
        throw new UnsupportedOperationException("not support now");
    }

    @Override
    public List<MockItem> findActiveByUser(String userName) {
        throw new UnsupportedOperationException("not support now");
    }

    @Override
    public List<MockItem> findActive(String username, String domainName, String resourceName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<MockItem> find(String username) {
        List<MockItem> mockItemList= Lists.newArrayList();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(this.dslFile));
            String line = null;
            StringBuffer sb=new StringBuffer();
            line = reader.readLine();
            for (; line != null; line = reader.readLine())
            {
                sb.append(line);
            }
            String[] dsls=sb.toString().split("---");
            for(String dsl:dsls){
                MockItem mockItem=new MockItem();
                mockItem.setContent(dsl);
                mockItemList.add(mockItem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        return mockItemList;
    }

}
