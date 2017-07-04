package com.nbcuni.test.cms.utils.jsonparsing.contentapi;

import com.nbcuni.test.cms.utils.Config;

/**
 * Created by Aliaksei_Dzmitrenka on 2/20/2017.
 */
public abstract class ContentApi {

    /**
     * @author Aliaksei Dzmitrenka
     * <p/>
     * This class is represent Service for working Content API
     */

    private static final String URL_PART = "/aqa/get?secretKey=15a6b43927adbf34fc1ee253238956e5";

    protected String baseUrl;
    protected String brand;

    public ContentApi(String brand) {
        this.brand = brand;
        baseUrl = Config.getInstance().getRokuHomePage(brand) + URL_PART;
    }

}
