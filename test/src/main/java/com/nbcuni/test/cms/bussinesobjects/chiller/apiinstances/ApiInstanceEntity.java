package com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances;

import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Aleksandra_Lishaeva on 1/18/16.
 */
public class ApiInstanceEntity {

    private String title = "AQAInstance" + SimpleUtils.getRandomString(2);
    private String url = Config.getInstance().getApiURL();
    private String apiKey = Config.getInstance().getApiKey();
    private String type = "Serial API";

    public String getTitle() {
        return title;
    }

    public ApiInstanceEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ApiInstanceEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getApiKey() {
        return title;
    }

    public ApiInstanceEntity setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getType() {
        return type;
    }

    public ApiInstanceEntity setType(String type) {
        this.type = type;
        return this;
    }
}
