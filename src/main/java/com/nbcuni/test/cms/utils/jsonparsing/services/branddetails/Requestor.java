package com.nbcuni.test.cms.utils.jsonparsing.services.branddetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Requestor {

    @SerializedName("requestor_id")
    private String requestorId;

    @SerializedName("resources")
    private List<Resource> resources;

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "Requestor [requestorId=" + requestorId + ", resources="
                + resources + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((requestorId == null) ? 0 : requestorId.hashCode());
        result = prime * result
                + ((resources == null) ? 0 : resources.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Requestor other = (Requestor) obj;
        if (requestorId == null) {
            if (other.requestorId != null)
                return false;
        } else if (!requestorId.equals(other.requestorId))
            return false;
        if (resources == null) {
            if (other.resources != null)
                return false;
        } else if (!resources.equals(other.resources))
            return false;
        return true;
    }

}
