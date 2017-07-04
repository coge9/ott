package com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing;

/**
 * Created by Ivan_Karnilau on 07-Jul-16.
 */
public enum Action {
    POST("post"),
    DELETE("delete");

    private String actionName;

    Action(String actionName) {
        this.actionName = actionName;
    }

    public String getAction() {
        return actionName;
    }
}
