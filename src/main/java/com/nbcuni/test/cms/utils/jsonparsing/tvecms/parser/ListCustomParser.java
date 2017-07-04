package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by alekca on 01.02.16.
 */
public class ListCustomParser implements JsonDeserializer<Object> {


    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject asset = jsonElement.getAsJsonObject();
        String operationType = asset.getAsJsonObject().get("itemType").getAsString();
        if (operationType.equalsIgnoreCase("rule")) {
            return context.deserialize(asset, LatestEpisodeJson.class);
        } else {
            return context.deserialize(asset, CuratedListItemJson.class);
        }
    }
}
