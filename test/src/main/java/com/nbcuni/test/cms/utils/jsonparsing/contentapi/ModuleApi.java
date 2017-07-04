package com.nbcuni.test.cms.utils.jsonparsing.contentapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.CommonJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.ModuleNodeJson;

public class ModuleApi extends ContentApi {

    /**
     * This class is represent Service for working Module Content API
     */


    private static final String MODULE_URL_PART = "&entityType=module";
    private static final String TITLE_PART = "&title=%s";
    private static final String ID_PART = "&id=%s";

    public ModuleApi(String brand) {
        super(brand);
        baseUrl = baseUrl + MODULE_URL_PART;
    }

    /**
     * Allow to obtain module from Content API by title
     *
     * @param title - module title (String)
     * @return - ModuleNodeJson represent module data
     */

    public ModuleNodeJson getModuleByTitle(String title) {
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        CommonJson commonJson = jsonParserHelper.getJavaObjectFromJson(
                jsonParserHelper.getJson(baseUrl + String.format(TITLE_PART, SimpleUtils.encodeStringToHTML(title))), CommonJson.class);
        String id = commonJson.getEntity().keySet().iterator().next();
        JsonElement globalElement = JsonParserHelper.getInstance().getJson(baseUrl + String.format(ID_PART, id));
        JsonObject modules = globalElement.getAsJsonObject().get("output").getAsJsonObject().get("TveAqaApiGetModule").getAsJsonObject();
        ModuleNodeJson module = JsonParserHelper.getInstance().getJavaObjectFromJson(modules.entrySet().iterator().next().getValue(), ModuleNodeJson.class);
        return module;
    }
}
