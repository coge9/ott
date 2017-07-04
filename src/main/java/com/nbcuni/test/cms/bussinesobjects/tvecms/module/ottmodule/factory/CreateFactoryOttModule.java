package com.nbcuni.test.cms.bussinesobjects.tvecms.module.ottmodule.factory;

import com.nbcuni.test.cms.bussinesobjects.tvecms.module.ottmodule.OttModuleForm;

/**
 * Created by Ivan_Karnilau on 15-Sep-15.
 */
public class CreateFactoryOttModule {

    private CreateFactoryOttModule(){
        super();
    }

    public static OttModuleForm createDefaultOttModule() {
        OttModuleForm ottModuleForm = new OttModuleForm();
        ottModuleForm.setTitle("").setStatus(true).setLocked(false);
        return ottModuleForm;
    }

    public static OttModuleForm createOttModuleWithCustomTitle(String title) {
        OttModuleForm ottModuleForm = new OttModuleForm();
        ottModuleForm.setTitle(title).setStatus(true).setLocked(false);
        return ottModuleForm;
    }

    public static OttModuleForm createOttModule(String title, boolean status, boolean locked) {
        OttModuleForm ottModuleForm = new OttModuleForm();
        ottModuleForm.setTitle(title).setStatus(status).setLocked(locked);
        return ottModuleForm;
    }
}
