package com.nbcuni.test.cms.utils.mpx.deserializer;

import com.google.gson.*;
import com.nbcuni.test.cms.utils.mpx.objects.MpxThumbnail;

import java.lang.reflect.Type;

public class MpxThumbnailDeserializer implements JsonDeserializer<MpxThumbnail> {

    @Override
    public MpxThumbnail deserialize(JsonElement paramJsonElement, Type paramType,
                                    JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {

        JsonObject obj = paramJsonElement.getAsJsonObject();
        MpxThumbnail thumbnail = new MpxThumbnail();
        thumbnail.setTitle(obj.get("title").getAsString());
        thumbnail.setGuid(obj.get("guid").getAsString());
        thumbnail.setId(obj.get("id").getAsString());
        thumbnail.setOwnerId(obj.get("ownerId").getAsString());
        thumbnail.setHeight(new Integer(obj.get("height").getAsInt()));
        thumbnail.setWidth(new Integer(obj.get("width").getAsInt()));
        thumbnail.setMediaId(obj.get("mediaId").getAsString());
        thumbnail.setUrl(obj.get("streamingUrl").getAsString());

        return thumbnail;
    }


}
