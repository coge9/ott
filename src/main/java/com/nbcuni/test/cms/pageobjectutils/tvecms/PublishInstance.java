package com.nbcuni.test.cms.pageobjectutils.tvecms;

import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Alena_Aukhukova on 10/8/2015.
 */
public enum PublishInstance {

    PROD("Production"),
    DEV("Development"),
    QA(""),
    AMAZON("amazon"),
    CONCERTO("concerto"),
    STAGE("Stage");

    private String name;

    PublishInstance(String name) {
        this.name = name;
    }

    public static PublishInstance getInstanceByName(String name) {
        for (PublishInstance instance : PublishInstance.values()) {
            if (instance.toString().equalsIgnoreCase(name) || instance.getName().equalsIgnoreCase(name)) {
                Utilities.logInfoMessage("API instance parameter is [" + instance + "]");
                return instance;
            } else {
                Utilities.logWarningMessage("API instance with name [" + name + "] isn't found in Publish Instance list");
            }
        }
        return null;

    }

    public String getName() {
        return name;
    }

}
