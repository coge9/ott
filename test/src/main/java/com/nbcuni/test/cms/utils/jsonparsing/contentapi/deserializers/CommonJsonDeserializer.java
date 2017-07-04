package com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers;

import com.google.gson.*;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.CommonJson;

import java.lang.reflect.Type;

/**
 * Created by Aliaksei_Dzmitrenka on 2/21/2017.
 */
public class CommonJsonDeserializer implements JsonDeserializer<CommonJson> {

    /**
     * @author Aliaksei Dzmitrenka
     *
     * This class is parser json with common nodes json into CommonJson class
     */


    @Override
    public CommonJson deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().get("output").getAsJsonObject();
        JsonObject condition = null;
        if (jsonObject.get("TveAqaApiGetNode") != null) {
            condition = jsonObject.get("TveAqaApiGetNode").getAsJsonObject();
        } else if (jsonObject.get("TveAqaApiGetModule") != null) {
            condition = jsonObject.get("TveAqaApiGetModule").getAsJsonObject();
        }
        return new CommonJson().setEntity(JsonParserHelper.getInstance().getJsonObjectAsMap(condition));

    }

}
