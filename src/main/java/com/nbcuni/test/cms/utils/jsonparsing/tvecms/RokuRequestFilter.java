package com.nbcuni.test.cms.utils.jsonparsing.tvecms;

public class RokuRequestFilter {

    private String instance;
    private String endpoint;
    private String endpointStart;
    private String objectType;
    private String responseStatus;
    private String responseStatusStart;
    private String responseMessage;
    private String objectLabel;

    public RokuRequestFilter() {
        super();
    }

    public String getInstance() {
        return instance;
    }

    public RokuRequestFilter setInstance(String instance) {
        this.instance = instance;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public RokuRequestFilter setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getObjectType() {
        return objectType;
    }

    public RokuRequestFilter setObjectType(String objectType) {
        this.objectType = objectType;
        return this;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public RokuRequestFilter setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public RokuRequestFilter setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    public String getResponseStatusStart() {
        return responseStatusStart;
    }

    public RokuRequestFilter setResponseStatusStart(String responseStatusStart) {
        this.responseStatusStart = responseStatusStart;
        return this;
    }

    public String getEndpointStart() {
        return endpointStart;
    }

    public RokuRequestFilter setEndpointStart(String endpointStart) {
        this.endpointStart = endpointStart;
        return this;
    }

    public String getObjectLabel() {
        return objectLabel;
    }

    public RokuRequestFilter setObjectLabel(String objectLabel) {
        this.objectLabel = objectLabel;
        return this;
    }

}
