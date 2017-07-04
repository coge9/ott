package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.parser;

import com.google.gson.*;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.MvpdErrorMessage;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFromJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdFeedJsonGlobalFields;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdJsonAllAvailableFields;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleMvpdParser extends AbstractMvpdServiceParser implements JsonSerializer<MvpdFromJson>,
        JsonDeserializer<MvpdFromJson> {

    @Override
    public MvpdFromJson deserialize(JsonElement src, Type type,
                                    JsonDeserializationContext context) throws JsonParseException {
        List<MvpdJsonAllAvailableFields> availableFields = Arrays
                .asList(MvpdJsonAllAvailableFields.values());
        availableFields
                .remove(MvpdJsonAllAvailableFields.ADOBE_PASS_ERROR_MAPPING);
        JsonObject parsedObject = src.getAsJsonObject();
        MvpdFromJson mvpd = new MvpdFromJson();
        for (MvpdJsonAllAvailableFields field : availableFields) {
            setFieldValueToJavaObject(parsedObject, field, mvpd);
        }
        // parsing of custom error messages
        JsonElement errorsElement = parsedObject
                .get(MvpdJsonAllAvailableFields.ADOBE_PASS_ERROR_MAPPING
                        .getFieldNameInJson());
        if (errorsElement == null) {
            mvpd.setCustomErrorMessages(null);
        } else {
            List<MvpdErrorMessage> errors = new ArrayList<MvpdErrorMessage>();
            parseErrorMessages(errorsElement, errors);
            mvpd.setCustomErrorMessages(errors);
        }

        return mvpd;
    }

    @Override
    public JsonElement serialize(MvpdFromJson src, Type type,
                                 JsonSerializationContext context) {
        return null;
    }

    private void setFieldValueToJavaObject(JsonObject jsonObject,
                                           MvpdJsonAllAvailableFields field, MvpdFromJson javaObject) {
        JsonElement fieldValue = jsonObject.get(field.getFieldNameInJson());
        try {
            Field javaField = javaObject.getClass().getDeclaredField(
                    field.getFieldMappingToJavaField());
            javaField.setAccessible(true);
            if (fieldValue != null && fieldValue.isJsonPrimitive()) {
                javaField.set(javaObject, fieldValue.getAsString());
            }
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("Error during JSON parsing");
        }

    }
}
