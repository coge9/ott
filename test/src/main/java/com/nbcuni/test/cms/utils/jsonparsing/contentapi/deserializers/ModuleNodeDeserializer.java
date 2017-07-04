package com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers;

import com.google.gson.*;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.ModuleNodeJson;

import java.lang.reflect.Type;

/**
 * Created by Dzianis Kulesh on 3/23/2017.
 */
public class ModuleNodeDeserializer implements JsonDeserializer<ModuleNodeJson> {

    /**
     * @author Dzianis Kulesh
     *
     * This class is parser for Module json.
     */

    @Override
    public ModuleNodeJson deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        // Create default parser for parsing string fields.
        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();
        ModuleNodeJson module = gson.fromJson(jsonElement, ModuleNodeJson.class);
        return module;
    }

}
