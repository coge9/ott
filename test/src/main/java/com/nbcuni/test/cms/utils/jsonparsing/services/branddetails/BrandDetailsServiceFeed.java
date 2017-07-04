package com.nbcuni.test.cms.utils.jsonparsing.services.branddetails;

import com.nbcuni.test.webdriver.Utilities;

import java.util.List;
import java.util.Random;

public class BrandDetailsServiceFeed {

    List<Requestor> requestors;
    private Random random = new Random();

    public BrandDetailsServiceFeed(List<Requestor> requestors) {
        this.requestors = requestors;
    }

    public int getNumberOfRequestors() {
        return requestors.size();
    }

    public List<Resource> getResourcesForRequestor(String requestorId) {
        if (requestors != null) {
            for (Requestor requestor : requestors) {
                if (requestor.getRequestorId().equals(requestorId)) {
                    return requestor.getResources();
                }
            }
        } else {
            throw new RuntimeException("Requestors was not initialize");
        }
        Utilities.logInfoMessage("There is no " + requestorId
                + " requestor ID is not found in list of requestors.");
        return null;
    }

    public Requestor getRequestor(String requestorId) {
        if (requestors != null) {
            for (Requestor requestor : requestors) {
                if (requestor.getRequestorId().equals(requestorId)) {
                    return requestor;
                }
            }
        }
        Utilities.logInfoMessage("There is no " + requestorId
                + " requestor ID is not found in list of requestors.");
        return null;
    }

    public Requestor getRandomRequestor() {
        if (requestors != null) {
            Requestor requestor = requestors.get(random.nextInt(requestors.size()));
            return requestor;
        }
        return null;
    }

    public Resource getResource(String requestorId, String resourceId) {
        List<Resource> resources = getResourcesForRequestor(requestorId);
        if (resources != null) {
            for (Resource resource : resources) {
                if (resource.getResourceId().equals(resourceId)) {
                    return resource;
                }
            }
        }
        Utilities.logInfoMessage("Resource " + resourceId + " is not found");
        return null;
    }

    public List<Requestor> getAllRequestors() {
        return requestors;
    }

    public boolean isRequestorPresent(String requestorId) {
        return getRequestor(requestorId) != null;
    }

    public boolean isResourcePresent(String requestorId, String resourceId) {
        return getResource(requestorId, resourceId) != null;
    }

}
