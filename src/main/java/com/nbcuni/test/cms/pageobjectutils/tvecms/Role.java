package com.nbcuni.test.cms.pageobjectutils.tvecms;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/22/2015.
 */
public enum Role {
    ADMIN("administrator", "active", Arrays.asList("administrator")),
    EDITOR("editor", "active", Arrays.asList("editor")),
    SENIOR_EDITOR("senior editor", "active", Arrays.asList("senior editor"));

    private String name;
    private List<String> roles;
    private String status;
    private Role(String name, String status, List<String> roles) {
        this.name = name;
        this.status = status;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getRoles() {
        return roles;
    }
}
