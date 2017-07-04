package com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers;

import com.google.gson.*;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.PlatformNodeJson;

import java.lang.reflect.Type;

/**
 * Created by Dzianis Kulesh on 3/23/2017.
 */
public class PlatformNodeDeserializer implements JsonDeserializer<PlatformNodeJson> {

    /**
     * @author Dzianis Kulesh
     * <p/>
     * This class is parser for Platform json.
     */
    private static final String SERVICE_INSTANCE_FIELD = "service_instance";

    @Override
    public PlatformNodeJson deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        // Create default parser for parsing string fields.
        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();
        PlatformNodeJson platform = gson.fromJson(jsonElement, PlatformNodeJson.class);

        // CUSTOM FIELDS PARSING
        // determine service type.
        String serviceInstance = jsonElement.getAsJsonObject().get(SERVICE_INSTANCE_FIELD).getAsString();
        platform.setServiceInstance(APIType.getTypeByName(serviceInstance.replace("_", " ")));
        return platform;
    }

}
