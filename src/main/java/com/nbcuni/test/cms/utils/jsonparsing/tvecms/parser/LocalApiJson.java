package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.common.base.MoreObjects;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandra_Lishaeva on 10/23/15.
 */
public class LocalApiJson {

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("request_data")
    private JsonObject requestData;

    @SerializedName("id")
    private String id;

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("instance")
    private String instance;

    @SerializedName("endpoint")
    private String endpoint;

    @SerializedName("object_type")
    private String objectType;

    @SerializedName("object_id")
    private String objectId;

    @SerializedName("request_options")
    private RequestOptions requestOptions;

    @SerializedName("response_status")
    private String responseStatus;

    @SerializedName("response_message")
    private String responseMessage;

    @SerializedName("response_data")
    private String responseData;

    @SerializedName("date")
    private String date;

    @SerializedName("info")
    private Info info;

    @SerializedName("uid")
    private String uid;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public JsonObject getRequestData() {
        return requestData;
    }

    public void setRequestData(JsonObject requestData) {
        this.requestData = requestData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    public void setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getObjectType() {
        return objectType;
    }

    // method desing to get attributes from LocalApiJson.
    public AttributesJson getAttributes() {
        return getRequestOptions().getAttributes();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userEmail", userEmail)
                .add("requestData", requestData)
                .add("id", id)
                .add("sessionId", sessionId)
                .add("instance", instance)
                .add("endpoint", endpoint)
                .add("objectType", objectType)
                .add("objectId", objectId)
                .add("requestOptions", requestOptions)
                .add("responseStatus", responseStatus)
                .add("responseMessage", responseMessage)
                .add("responseData", responseData)
                .add("date", date)
                .add("info", info)
                .add("uid", uid)
                .toString();
    }


    public class Info {

        @SerializedName("time_spent")
        private String timeSpent;

        @SerializedName("object_label")
        private String objectLabel;

        public String getObjectLabel() {
            return objectLabel;
        }

        public void setObjectLabel(String objectLabel) {
            this.objectLabel = objectLabel;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("timeSpent", timeSpent)
                    .add("objectLabel", objectLabel)
                    .toString();
        }

    }
}
