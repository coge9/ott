package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.parser;

import com.google.gson.*;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.MvpdErrorMessage;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFeed;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFromJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdFeedJsonGlobalFields;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FeedParser extends AbstractMvpdServiceParser implements
        JsonSerializer<MvpdFeed>, JsonDeserializer<MvpdFeed> {

    @Override
    public MvpdFeed deserialize(JsonElement src, Type type,
                                JsonDeserializationContext context) throws JsonParseException {
        MvpdFeed mvpdFeed = null;
        if (src.isJsonArray()) {
            mvpdFeed = new MvpdFeed();
            parseMvpdsSection(src, context, mvpdFeed);
        } else if (src.isJsonObject()) {
            mvpdFeed = new MvpdFeed();
            JsonObject feedJsonObject = src.getAsJsonObject();
            parseTopLevelOfFeed(feedJsonObject, mvpdFeed);
            JsonElement mvpds = feedJsonObject
                    .get(MvpdFeedJsonGlobalFields.MVPD_WHITE_LIST
                            .getFieldNameInJson());
            if (mvpds != null) {
                parseMvpdsSection(mvpds, context, mvpdFeed);
            }
        } else {
            Utilities.logSevereMessage("WRONG JSON IS RECIEVED");
        }
        return mvpdFeed;
    }

    @Override
    public JsonElement serialize(MvpdFeed src, Type type,
                                 JsonSerializationContext context) {
        return null;
    }

    private void parseTopLevelOfFeed(JsonObject feedJsonObject,
                                     MvpdFeed mvpdFeed) {
        JsonElement brand = feedJsonObject.get(MvpdFeedJsonGlobalFields.BRAND
                .getFieldNameInJson());
        if (brand != null) {
            mvpdFeed.setBrand(brand.getAsString());
        }
        JsonElement globalSettingsElement = feedJsonObject
                .get(MvpdFeedJsonGlobalFields.GLOBAL_SETTINGS
                        .getFieldNameInJson());
        parseGlobalSettingsSection(globalSettingsElement, mvpdFeed);
    }

    private void parseGlobalSettingsSection(JsonElement globalSettingsElement,
                                            MvpdFeed mvpdFeed) {
        if (globalSettingsElement != null
                && globalSettingsElement.isJsonObject()) {
            JsonObject globalSettingsObject = globalSettingsElement
                    .getAsJsonObject();
            JsonElement brand = globalSettingsObject
                    .get(MvpdFeedJsonGlobalFields.BRAND.getFieldNameInJson());
            if (brand != null) {
                mvpdFeed.setBrand(brand.getAsString());
            }
            JsonElement filePath = globalSettingsObject
                    .get(MvpdFeedJsonGlobalFields.FILE_PATH
                            .getFieldNameInJson());
            if (filePath != null) {
                mvpdFeed.setFilePath(filePath.getAsString());
            }
            JsonElement adobePassEndPoint = globalSettingsObject
                    .get(MvpdFeedJsonGlobalFields.ADOBE_PASS_END_POINT
                            .getFieldNameInJson());
            if (adobePassEndPoint != null) {
                mvpdFeed.setAdobePassEndPoint(adobePassEndPoint.getAsString());
            }
            JsonElement adobePassErrorMappings = globalSettingsObject
                    .get(MvpdFeedJsonGlobalFields.ADOBE_PASS_ERROR_MAPPING
                            .getFieldNameInJson());
            if (adobePassErrorMappings != null) {
                List<MvpdErrorMessage> listOfErrors = new ArrayList<>();
                parseErrorMessages(adobePassErrorMappings, listOfErrors);
                mvpdFeed.setGlobalErrorMessages(listOfErrors);
            }
        }
    }

    private void parseMvpdsSection(JsonElement src,
                                   JsonDeserializationContext context, MvpdFeed mvpdFeed) {
        JsonArray mvpdArray = src.getAsJsonArray();
        for (JsonElement singleMvpd : mvpdArray) {
            if (singleMvpd.isJsonArray() && singleMvpd.getAsJsonArray().size()== 0) {
                break;
            }
            MvpdFromJson mvpdFromJson = context.deserialize(singleMvpd, MvpdFromJson.class);
            if (mvpdFromJson != null) {
                mvpdFeed.addMvpd(mvpdFromJson);
            }
        }
    }
}
