package com.nbcuni.test.cms.utils.jsonparsing.services.mvpd;

import com.google.gson.*;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map.Entry;

public class MvpdFromServiceSerializer implements
        JsonSerializer<MvpdFromService>, JsonDeserializer<MvpdFromService> {

    @Override
    public MvpdFromService deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context) throws JsonParseException {
        MvpdFromService mvpd = new MvpdFromService();
        JsonObject mvpdObject = json.getAsJsonObject();
        JsonElement idsElement = mvpdObject.get(MVPDServicesFields.FIELD_MVPD_IDS.getFieldName());
        if (idsElement != null) {
            JsonObject ids = idsElement.getAsJsonObject();
            int numberOfIds = ids.entrySet().size();
            for (int i = 0; i < numberOfIds; i++) {
                mvpd.addMvpdId(ids.get(String.valueOf(i)).getAsString());
            }
        }
        for (Entry<String, JsonElement> entry : mvpdObject.entrySet()) {
            if (entry.getKey().equals(MVPDServicesFields.FIELD_MVPD_IDS.getFieldName())) {
                continue;
            }
            MVPDServicesFields currentField = null;
            for (MVPDServicesFields serviceField : MVPDServicesFields.values()) {
                if (serviceField.getFieldName().equals(entry.getKey())) {
                    currentField = serviceField;
                    break;
                }
            }
            for (Field objectField : MvpdFromService.class.getDeclaredFields()) {
                if (objectField.getName().equals(currentField.getJavaName())) {
                    objectField.setAccessible(true);
                    try {
                        objectField.set(mvpd, entry.getValue().getAsString());
                    } catch (IllegalArgumentException e) {
                        Utilities.logSevereMessage("There is an error during parsing JSON " + Utilities.convertStackTraceToString(e));
                    } catch (IllegalAccessException e) {
                        Utilities.logSevereMessage("There is an error during parsing JSON " + Utilities.convertStackTraceToString(e));
                    }
                }
            }
        }
        return mvpd;
    }

    @Override
    public JsonElement serialize(MvpdFromService src, Type typeOfSrc,
                                 JsonSerializationContext context) {

        return null;
    }

}
