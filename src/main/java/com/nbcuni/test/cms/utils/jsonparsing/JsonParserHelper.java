package com.nbcuni.test.cms.utils.jsonparsing;

import com.google.gson.*;
import com.nbcuni.test.annotation.api.API;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.httpclient.CustomHttpClient;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers.CommonJsonDeserializer;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers.GlobalNodeJsonDeserializer;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers.ModuleNodeDeserializer;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.deserializers.PlatformNodeDeserializer;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.CommonJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.ModuleNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.PlatformNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFeed;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFromJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.parser.FeedParser;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.parser.SingleMvpdParser;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.ContentItemListObject;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.ListCustomParser;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RuleConditionParser;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RuleListConditions;
import com.nbcuni.test.webdriver.Utilities;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ******************************************************************
 * JSON Parser Helper
 * <p>
 * Description: contains useful methods for working with JSON
 *
 * @author Dzianis Kulesh
 * @version 1.0
 * May 17, 2014
 *
 * Revision Log-------------------------------------------------- Date- ,
 * Author- , Change Description-
 * *********************************************************************
 */

public class JsonParserHelper {

    private static JsonParserHelper instance = null;
    private static API api;
    private final Gson gson;

    private JsonParserHelper() {

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(MvpdFromJson.class, new SingleMvpdParser())
                .registerTypeAdapter(MvpdFeed.class, new FeedParser())
                .registerTypeAdapter(ContentItemListObject.class, new ListCustomParser())
                .registerTypeAdapter(RuleListConditions.class, new RuleConditionParser())
                .registerTypeAdapter(RuleListConditions.class, new RuleConditionParser())
                .registerTypeAdapter(CommonJson.class, new CommonJsonDeserializer())
                .registerTypeAdapter(GlobalNodeJson.class, new GlobalNodeJsonDeserializer())
                .registerTypeAdapter(PlatformNodeJson.class, new PlatformNodeDeserializer())
                .registerTypeAdapter(ModuleNodeJson.class, new ModuleNodeDeserializer()).create();
    }

    public static JsonParserHelper getInstance() {
        if (instance == null) {
            instance = new JsonParserHelper();
        }
        api = new API();
        return instance;
    }

    /**
     * *********************************************************************************
     * Method Name: getJson Description: return JsonElement(full JSON) from the
     * the Json file path
     *
     * @param pathToFile - file path to JSON
     * @return JsonElement
     * ***********************************************************************************
     */
    public JsonElement getJsonFromFile(final String pathToFile) {
        final File jsonfile = new File(pathToFile);
        JsonElement json = null;
        InputStream is = null;
        try {
            is = new FileInputStream(jsonfile);
            json = getJsonFromInputStream(is);
        } catch (final FileNotFoundException e) {
            Utilities.logSevereMessage("File " + pathToFile + " not found");
        }
        return json;
    }

    /**
     * *********************************************************************************
     * Method Name: getJson Description: return JsonElement(full JSON) from the
     * the Input Stream
     *
     * @param is - Input stream
     * @return JsonElement
     * ***********************************************************************************
     */
    public JsonElement getJsonFromInputStream(InputStream is) {
        final JsonParser parser = new JsonParser();
        JsonElement json = null;
        try {
            json = parser.parse(new InputStreamReader(is));
            is.close();
        } catch (final IOException e) {
            Utilities.logSevereMessage("Input/Output error " + e.getClass() + ": "
                    + e.getMessage());
            e.printStackTrace();
        }
        return json;
    }

    /**
     * *********************************************************************************
     * Method Name: getJson Description: return JsonElement(full JSON) from the
     * PAGE_URL
     *
     * @param url - PAGE_URL with JSON
     * @return JsonElement
     * ***********************************************************************************
     */
    public JsonElement getJson(final String url) {
        try {
            CustomHttpClient httpClient = new CustomHttpClient();
            return getJsonFromInputStream(httpClient.httpGet(url).getEntity().getContent());
        } catch (Exception e1) {
            Utilities.logSevereMessageThenFail("Unable to get JSON: " + e1.getMessage() + "\n. From url [" + url + "]");
        }
        return null;
    }

    public int getHttpResponseCode(String url) {
        try {
            return api.getHTTPResponseCode(url);
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
        return 404;
    }

    /*****************************************************************************************
     * Method Name: getJavaObjectFromJson Description: create java object from
     * JSON element
     *
     * @param element
     *            - JSON element
     * @param classOfT
     *            - class of desired object
     *
     * @param <T> To of class to parse JSON
     * @return instance of correspondent class - result of JSON parsing
     *
     ****************************************************************************************/
    public <T> T getJavaObjectFromJson(final JsonElement element,
                                       final Class<T> classOfT) {
        return gson.fromJson(element, classOfT);
    }

    public <T> T getJavaObjectFromJson(final Gson gson, final JsonElement element,
                                       final Class<T> classOfT) {
        return gson.fromJson(element, classOfT);
    }

    /**
     * **************************************************************************************
     * Method Name: getListOfJavaObjectsFromJson Description: create list of
     * java object from array of JSON element
     *
     * @param array    - array of JSON elements
     * @param classOfT - class of desired object
     * @param <T> To of class to parse JSON
     *
     * @return List of instance of correspondent class - result of JSON parsing
     * **************************************************************************************
     */
    public <T> List<T> getListOfJavaObjectsFromJson(final JsonArray array,
                                                    final Class<T> classOfT) {
        final List<T> list = new ArrayList<T>();
        for (final JsonElement element : array) {
            list.add(this.getJavaObjectFromJson(element, classOfT));
        }
        return list;
    }

    /**
     * **************************************************************************************
     * Method Name: verifyFieldPresentInJsonObject Description: verify presence
     * of certain field in JSON object
     *
     * @param object - Json object for verification
     * @param fieldName - field which should present
     *
     * @return boolean value if field exists of not.
     * **************************************************************************************
     */
    public Boolean verifyFieldPresentInJsonObject(final JsonObject object,
                                                  final String fieldName) {
        return object.get(fieldName) == null ? false : true;
    }

    /**
     * **************************************************************************************
     * Method Name: getNumberOfFieldsInJsonObject Description: return number of
     * elements in json object JSON object
     *
     * @param object - JSON object
     * @return number of fields in JSON
     *
     *************************************************************************************
     */
    public int getNumberOfFieldsInJsonObject(final JsonObject object) {
        return object.entrySet().size();
    }

    /**
     * **************************************************************************************
     * Method Name: verifyObjectStructureCorrect Description: verify that JSON
     * object has all fields from List of fields
     *
     * @param object    - JSON object
     * @param fieldList - List of Strings -  list of fields name
     * @return boolean
     * **************************************************************************************
     */
    public Boolean verifyObjectStructureCorrect(final JsonObject object,
                                                final List<String> fieldList) {
        for (final String field : fieldList) {
            if (!verifyFieldPresentInJsonObject(object, field))
                return false;
        }
        return true;
    }

    /**
     * **************************************************************************************
     * Method Name: getJsonObjectAsMap Description: return simple Json object as
     * Map
     *
     * @param object - JSON object
     * @return Map
     * **************************************************************************************
     */
    public Map<String, String> getJsonObjectAsMap(JsonObject object) {
        Map<String, String> data = new HashMap<String, String>();
        for (Entry<String, JsonElement> entry : object.entrySet()) {
            data.put(entry.getKey(), entry.getValue().getAsString());
        }
        return data;
    }

    /**
     * Method Name: getAsJsonFromString Description: return JsonObject from string
     *
     * @param line -string parameter
     * @return JSONObject
     */
    public JSONObject getAsJsonFromString(String line) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(line);

        } catch (JSONException e) {
            Assertion.fail(e.getMessage());
        }
        return jsonObj;
    }

}