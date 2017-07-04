package com.nbcuni.test.cms.bussinesobjects.tvecms.module.ottmodule;

/**
 * Created by Ivan_Karnilau on 15-Sep-15.
 */
public class OttModuleForm {

    private String title = null;
    private Boolean status = null;
    private Boolean locked = null;

    public Boolean getLocked() {
        return locked;
    }

    public OttModuleForm setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OttModuleForm setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public OttModuleForm setStatus(Boolean status) {
        this.status = status;
        return this;
    }
}
