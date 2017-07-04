package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Aleksandra_Lishaeva on 3/16/16.
 */
public class RuleConditionParser implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject condition = jsonElement.getAsJsonObject();
        String field = condition.getAsJsonObject().get("field").getAsString();
        if (field.equalsIgnoreCase("fullEpisode")) {
            return context.deserialize(condition, RuleListConditionsJsonDefault.class);
        } else {
            return context.deserialize(condition, RuleListConditionsJson.class);
        }
    }
}
