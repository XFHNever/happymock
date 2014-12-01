package com.jcworks.happymock.resolvor;

import com.jcworks.happymock.core.composition.ComposeFactory;
import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.MociResponse;
import com.jcworks.happymock.core.entity.MociSetting;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-12
 */
@Test
public class ComposeFactoryTest {

    public void testComposeSetting(){
        MociSetting mociSetting=new MociSetting();
        MociRequest mociRequest=new MociRequest();
        mociRequest.setUri("/happy_mock_happy_work/wheeel/");
        MociResponse mociResponse=new MociResponse();
        mociResponse.setText("hello Moci");
        mociSetting.setRequest(mociRequest);
        mociSetting.setResponse(mociResponse);

        ComposeFactory.getInstance().composeSetting(mociSetting);
        Assert.assertNotNull(mociSetting);
    }
}
