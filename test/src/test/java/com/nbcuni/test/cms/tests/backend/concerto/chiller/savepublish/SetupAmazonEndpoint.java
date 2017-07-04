package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.tvecms.pages.apiinstances.ApiInstancesPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Aleksandra_Lishaeva on 6/22/16.
 */
public class SetupAmazonEndpoint extends BaseAuthFlowTest {

    public String setupAmazonEndpoint(String brand) {
        ApiInstanceEntity entity = new ApiInstanceEntity();
        entity.setApiKey(SimpleUtils.getRandomString(6));
        entity.setUrl(SimpleUtils.getRandomString(5));
        entity.setType(APIType.AMAZON.getType());
        ApiInstancesPage apiInstancesPage = mainRokuAdminPage.openPage(ApiInstancesPage.class, brand);
        return apiInstancesPage.createAmazonInstance(entity);
    }
}
