package com.nbcuni.test.cms.utils.jsonparsing.tvecms;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ResponseData;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.httpclient.CustomHttpClient;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Metadata;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuEndpointType;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.PublishingContentType;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class RequestHelper {

    public static final String SERIAL_ENDPOINT_IDENTIFIER = "serial.apps.nbcuni.com";
    private static final String REQUEST_COOKIE_BODY_FORMAT = "name=%s&pass=%s&form_build_id=&form_id=user_login&op=Log+in";
    private static final String DELETE_ACTION_IDENTIFIER = "delete";
    private CustomHttpClient customHttpClient;

    public RequestHelper() {
        customHttpClient = new CustomHttpClient();
    }

    /**
     * *********************************************************************************
     * Method is design to get all responses from Publishing log.
     *
     * @param url - Url to publishing log.
     * @return List of LocalApiJson - parsed response.
     * ***********************************************************************************
     */
    public List<LocalApiJson> getLocalApiJsons(final String url) {
        Utilities.logInfoMessage("Get AQA API objects by URL: " + url);
        List<LocalApiJson> apiJsons = new ArrayList<>();
        int page = 0;
        int elementsSize = 1;
        while (elementsSize > 0) {
            // Get Json element for the page $page
            JsonElement mainJson = getJsonElement(url + "&page=" + page);
            // Check that log exists and not empty.
            if (mainJson != null && ((JsonArray) mainJson).get(0).isJsonObject()) {
                // Parse josn for single page and add element to the Queue.
                mainJson.getAsJsonArray().forEach(apiJsonElement -> apiJsons.add(JsonParserHelper.getInstance().getJavaObjectFromJson(apiJsonElement, LocalApiJson.class)));
            } else {
                // if log is empty change variable to quit from while cycle.
                elementsSize = 0;
            }
            page++;
        }
        return apiJsons;
    }


    /**
     * *********************************************************************************
     * Method is design to get responses from Publishing log filtered by PublishingContentType.
     *
     * @param url  - Url to publishing log.
     * @param type - Publishing content type.
     * @return List of LocalApiJson - filtered response.
     * ***********************************************************************************
     */
    public List<LocalApiJson> getLocalApiJsons(final String url, PublishingContentType type) {
        List<LocalApiJson> notFilteredData;
        // Identify which API instance is requested
        if (type instanceof ConcertoApiPublishingTypes) {
            notFilteredData = getConcertoApiEntries(url);
        } else {
            notFilteredData = getSerialApiEntries(url);
        }
        return filterResponsesByObjectLabel(notFilteredData, type);
    }

    /**
     * *********************************************************************************
     * Method is design to get single response from Publishing log.
     * It is expect to get only one correspondent entry in publishing log. In case of
     * 0 or &gt;1 entries method will throw runtime exception.
     *
     * @param url - Url to publishing log.
     * @return LocalApiJson - localApiJson Object
     * ***********************************************************************************
     */
    public LocalApiJson getSingleLocalApiJson(String url) {
        List<LocalApiJson> listOfResponses = getLocalApiJsons(url);
        if (listOfResponses.isEmpty() || listOfResponses.size() > 1) {
            throw new RuntimeException("There are not single object of publishing log by URL: " + url);
        }
        return listOfResponses.get(0);
    }

    /**
     * *********************************************************************************
     * Method is design to filter responses from publishing log based on certain criteria.
     *
     * @param notFilteredList   - Not filtered publishing log entries.
     * @param filteringCriteria - Criteria for filtering.
     * @return List of LocalApiJson - Filtered publishing log entries.
     * ***********************************************************************************
     */
    public List<LocalApiJson> filterResponses(List<LocalApiJson> notFilteredList, Predicate<? super LocalApiJson> filteringCriteria) {
        // filtering using Java Stream API and return result as list.
        return notFilteredList.stream().filter(filteringCriteria).collect(Collectors.toList());
    }

    /**
     * *********************************************************************************
     * Method is design to get responses related to entries of Concerto API.
     *
     * @param url - Url to publishing log.
     * @return List of LocalApiJson - Log entries related to Concerto API.
     * ***********************************************************************************
     */
    public List<LocalApiJson> getConcertoApiEntries(final String url) {
        // geting all entries from the log.
        List<LocalApiJson> allEntries = getLocalApiJsons(url);
        // define criteria for filtering (belong to Concerto API)
        Predicate<? super LocalApiJson> filteringCriteria = elem ->
                elem.getEndpoint() != null &&
                        elem.getResponseStatus().equals(ResponseData.SUCCESS.getResponseStatus()) &&
                        (StringUtils.containsIgnoreCase(elem.getInstance(), PublishInstance.AMAZON.getName()) || StringUtils.containsIgnoreCase(elem.getInstance(), PublishInstance.CONCERTO.getName()));
        // filter results base on criteria.
        return filterResponses(allEntries, filteringCriteria);
    }

    /**
     * *********************************************************************************
     * Method is design to get responses related to entries of Serial API.
     *
     * @param url - Url to publishing log.
     * @return List of LocalApiJson - Log entries related to Serial API.
     * ***********************************************************************************
     */
    public List<LocalApiJson> getSerialApiEntries(final String url) {
        // geting all entries from the log.
        List<LocalApiJson> allEntries = getLocalApiJsons(url);
        // define criteria for filtering (belong to Serial API)
        Predicate<? super LocalApiJson> filteringCriteria = elem ->
                elem.getEndpoint() != null && (StringUtils.containsIgnoreCase(elem.getEndpoint(), SERIAL_ENDPOINT_IDENTIFIER));
        // filter results base on criteria.
        return filterResponses(allEntries, filteringCriteria);
    }

    /**
     * *********************************************************************************
     * Method is design to get parsed responses of certain content type.
     * This type also determine which instance (Concerto or Serial) is used.
     *
     * @param url - Url to publishing log.
     * @param type - content type.
     * @param <T> Type of class to parse JSON
     *
     * @return generic List -  List of parsed object of correspondent type.
     * ***********************************************************************************
     */
    public <T> List<T> getParsedResponse(String url, PublishingContentType type) {
        List<LocalApiJson> notParsedData;
        // Identify which API instance is requested (Concerto )
        if (type instanceof ConcertoApiPublishingTypes) {
            notParsedData = getConcertoApiEntries(url);
        } else {
            notParsedData = getSerialApiEntries(url);
            // trying to get RokuType
            RokuEndpointType endpointType = RokuEndpointType.getByPublishingType((SerialApiPublishingTypes) type);
            // if type define use regular logic
            if (endpointType != null) {
                serialApiErrorLoggingLogic(type.getBelongObjectClass(), notParsedData, endpointType, new SoftAssert());
            }
        }
        // Select only responsed of correspondent type.
        List<LocalApiJson> filteredData = filterResponsesByObjectLabel(notParsedData, type);
        // Parse data to certain class.
        return parse(filteredData, type.getBelongObjectClass());
    }


    /**
     * *********************************************************************************
     * Method is design to return responses related to the DELETE action for certain content type.
     * Currently it is valid only for Concerto API related classes.
     *
     * @param url  - Url to publishing log.
     * @param type - correspondent type.
     * @return generic List -  List of parsed delete object
     * ***********************************************************************************
     */
    public List<ContentTypeDeleteJson> getDeleteResponses(String url, PublishingContentType type) {
        // get local API jsons filtered by content type.
        List<LocalApiJson> filteredByContentType = getLocalApiJsons(url, type);
        // get requests related to delete action.
        List<LocalApiJson> deleteLocalApiJson = filterResponses(filteredByContentType, localApiJson -> localApiJson.getAttributes().getAction().getStringValue().equals(DELETE_ACTION_IDENTIFIER));
        // parse delete requests.
        return parse(deleteLocalApiJson, ContentTypeDeleteJson.class);
    }

    /**
     * *********************************************************************************
     * Method is design to return response related to the DELETE action for certain content type.
     * Currently it is valid only for Concerto API related classes.
     *
     * @param url  - Url to publishing log.
     * @param type - correspondent type.
     * @return Parsed delete object
     * ***********************************************************************************
     */
    public ContentTypeDeleteJson getSingleDeleteResponses(String url, PublishingContentType type) {
        List<ContentTypeDeleteJson> deleteJsons = getDeleteResponses(url, type);
        if (deleteJsons == null || deleteJsons.isEmpty() || deleteJsons.size() > 1) {
            throw new RuntimeException("There are not single object of publishing log by URL: " + url);
        }
        return deleteJsons.get(0);
    }

    /**
     * *********************************************************************************
     * Method is design to parse any LocalApiJsons to correspondent type.
     * <p>
     * NOTE: Used only in specific cases. Mostly you should use
     * getParsedResponse(String url, PublishingContentType type, final Class&lt;T&gt; classToParse)
     *
     * @param notParsedData - Not parsed data.
     * @param classToParse - Class to which you would like to parse.
     * @param <T> Type of class to parse JSON
     *
     * @return generic List -  List of parsed object of correspondent type.
     *
     * ***********************************************************************************
     */
    public <T> List<T> parse(List<LocalApiJson> notParsedData, Class<T> classToParse) {
        List<T> parsedObjects = new ArrayList<>();
        for (LocalApiJson localApiJson : notParsedData) {
            T parsedObject = JsonParserHelper.getInstance()
                    .getJavaObjectFromJson(localApiJson.getRequestData(),
                            classToParse);
            parsedObjects.add(parsedObject);
        }
        return parsedObjects;
    }

    /**
     * *********************************************************************************
     * Method is design to get single response related to entries of Serial API.
     * It is expect to get only one correspondent entry in publishing log. In case of
     * 0 or &gt; 1 entries method will throw runtime exception.
     *
     * @param url - Url to publishing log.
     * @param type - content type
     * @param <T> Type of class to parse JSON
     *
     * @return Parsed object of correspondent type.
     *
     *
     * ***********************************************************************************
     */
    public <T> T getSingleParsedResponse(String url, PublishingContentType type) {
        List<T> listOfResponses = getParsedResponse(url, type);
        if (listOfResponses.isEmpty() || listOfResponses.size() > 1) {
            throw new RuntimeException("There are not single object of publishing log by URL: " + url);
        }
        return listOfResponses.get(0);
    }

    /**
     * *********************************************************************************
     * Method is design to filter responses by Object label. It selects only success Responses.
     *
     * @param notFilteredList - Not filtered publishing log entries.
     * @return List of LocalApiJson - Filtered publishing log entries.
     * ***********************************************************************************
     */
    private List<LocalApiJson> filterResponsesByObjectLabel(List<LocalApiJson> notFilteredList, PublishingContentType type) {
        return filterResponses(notFilteredList, localApiJson -> StringUtils.containsIgnoreCase(localApiJson.getInfo().getObjectLabel(), type.getObjectLabel()) &&
                (localApiJson.getResponseStatus().equalsIgnoreCase(ResponseData.CREATED.getResponseStatus()) ||
                        localApiJson.getResponseStatus().equalsIgnoreCase(ResponseData.UPDATED.getResponseStatus()) ||
                        localApiJson.getResponseStatus().equalsIgnoreCase(ResponseData.SUCCESS.getResponseStatus())));
    }


    /**
     * *********************************************************************************
     * Method is design for serial API only to track there are no 50* errors present in publishing log.
     * <p>
     * ***********************************************************************************
     */
    private <T> List<T> serialApiErrorLoggingLogic(Class<T> classOfT, List<LocalApiJson> localApiJsons, RokuEndpointType endpointType, SoftAssert softAssert) {
        Assertion.assertTrue(classOfT.getClass().isInstance(Metadata.class), "The input class: " + classOfT + " is not Implement Metadata interface, please implement that to have an ability use method");
        List<T> actualItemObjects = new ArrayList<>();
        T itemJson = null;
        Boolean trigger = false;
        for (LocalApiJson localApiJson : localApiJsons) {
            if (localApiJson.getEndpoint().endsWith(endpointType.getEndpointType()) && localApiJson.getResponseStatus().startsWith("50")) {
                softAssert.assertTrue(false, "The POST method was not successful and get " + localApiJson.getResponseStatus() + " code");
            }
        }
        softAssert.assertAll("There are some errors in publishing log.");
        return actualItemObjects;
    }


    /**
     * *********************************************************************************
     * Method is design get JSON element from the publishing log URL. It contains logic
     * of login into CMS admin (because response is visible only for logged in user).
     *
     * @param url - Url to the publishing log.
     * @return JsonElement - Json element corresponding to publishing log.
     * ***********************************************************************************
     */
    private JsonElement getJsonThrowCookies(String url) {
        Utilities.logSevereMessage("Unable to get JSON via API class, trying alternative option");
        try {
            // get login page URL
            String loginUrl = url.substring(0, url.indexOf("com") + 3) + "/user";
            String requestUrl = url;
            // add headers for making login
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("Cookie", "third_party_cookie_enabled=true; has_js=1");
            // create REQUEST_BODY for login
            String body = String.format(REQUEST_COOKIE_BODY_FORMAT, Config.getInstance().getRokuUserLogin(), Config.getInstance().getRokuUserPassword());
            // send POST request.
            HttpResponse response = customHttpClient.httpPost(loginUrl, body, headers);
            // get login cookie from response.
            Header cookieHeader = response.getFirstHeader("Set-Cookie");
            // sending get request to publishing log URL.
            response = customHttpClient.httpGet(requestUrl, new BasicHeader("Cookie", cookieHeader.getValue()));
            JsonElement element = JsonParserHelper.getInstance().getJsonFromInputStream(response.getEntity().getContent());
            return element;
        } catch (Exception e1) {
            throw new RuntimeException("Unable to get JSON of publishing log");
        }
    }


    /**
     * *********************************************************************************
     * Method is design get JSON element from the publishing log URL.
     *
     * @param url - Url to the publishing log.
     * @return JsonElement - Json element corresponding to publishing log.
     * ***********************************************************************************
     */
    private JsonElement getJsonElement(String url) {
        Utilities.logInfoMessage("Requesting JSON from " + url);
        JsonElement mainJson = getJsonThrowCookies(url);
        if (!(mainJson.isJsonArray() || mainJson.isJsonObject())) {
            Utilities.logSevereMessage("Response at URL: " + url
                    + " is not JSON Array or Object");
            return null;
        }
        return mainJson;
    }

}