package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DegradationPermission {

    String brandName;
    private Map<String, Boolean> authN = new HashMap<String, Boolean>();
    private Map<String, Boolean> authZ = new HashMap<String, Boolean>();

    public DegradationPermission(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Map<String, Boolean> getAuthN() {
        return authN;
    }

    public void setAuthN(Map<String, Boolean> authN) {
        this.authN = authN;
    }

    public Map<String, Boolean> getAuthZ() {
        return authZ;
    }

    public void setAuthZ(Map<String, Boolean> authZ) {
        this.authZ = authZ;
    }

    public void addAuthnItem(String id, Boolean status) {
        authN.put(id, status);
    }

    public void addAuthzItem(String id, Boolean status) {
        authZ.put(id, status);
    }

    public boolean isAuthNExist() {
        for (Entry<String, Boolean> item : authN.entrySet()) {
            if (item.getValue().equals(true)) {
                return true;
            }
        }
        return false;
    }


    public boolean isAuthZExist() {
        for (Entry<String, Boolean> item : authZ.entrySet()) {
            if (item.getValue().equals(true)) {
                return true;
            }
        }
        return false;
    }
}
