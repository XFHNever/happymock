package com.jcworks.happymock.persist.service;

import com.jcworks.happymock.core.entity.MociSetting;
import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.search.MociFinder;
import com.google.common.base.Optional;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-12
 */
@Test
public class MociFinderTest {
    @Test(enabled =false)
    public void testlookup(){
        Request request=new Request();
        request.setMethod("get");
        //request.setDomain("User");
        //request.setResource("ResourceA");
        request.setUrl("/User/ResourceA/window/123/listing/?eventid=123&pricemin=100");
        Optional<MociSetting> mociSetting=MociFinder.getInstance().bestMatch(request);
     //   Assert.assertNotNull(mociSetting.get());
    }

}
