package com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceConstant;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceEntity;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Aleksandra_Lishaeva on 1/18/16.
 */
public class CreateFactoryModule {

    private CreateFactoryModule(){
        super();
    }

    public static ApiInstanceEntity createDefaultInstance() {
        return new ApiInstanceEntity();
    }

    public static ApiInstanceEntity createApiInstanceWithCustomTitle(String title) {
        ApiInstanceEntity form = new ApiInstanceEntity();
        form.setTitle(title);
        return form;
    }

    public static ApiInstanceEntity createApiInstanceWithCustomUrl(String url) {
        ApiInstanceEntity form = new ApiInstanceEntity();
        form.setUrl(url);
        return form;
    }

    public static ApiInstanceEntity createApiInstanceWithCustomApiKey(String apiKey) {
        ApiInstanceEntity form = new ApiInstanceEntity();
        form.setApiKey(apiKey);
        return form;
    }

    public static ApiInstanceEntity createApiInstanceWithCustomFields(String title, String url, String apiKey) {
        ApiInstanceEntity form = new ApiInstanceEntity();
        form.setTitle(title).setUrl(url).setApiKey(apiKey);
        return form;
    }

    public static ApiInstanceEntity createAmazonApiInstance() {
        ApiInstanceEntity form = new ApiInstanceEntity();
        form.setTitle(ApiInstanceConstant.AMAZON_TITLE + SimpleUtils.getRandomString(2));
        form.setUrl(ApiInstanceConstant.AMAZON_URL).setType(APIType.AMAZON.getType());
        return form;
    }
}
