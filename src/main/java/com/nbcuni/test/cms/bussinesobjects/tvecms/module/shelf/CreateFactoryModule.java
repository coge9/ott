package com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf;

import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;

import java.util.Random;

/**
 * Created by Ivan_Karnilau on 3/15/2017.
 */
public class CreateFactoryModule {

    private CreateFactoryModule(){
        super();
    }

    public static void disableRandomAsset(Module module) {
        module.getContentEnabled().entrySet().forEach(entry -> entry.setValue(new Random().nextBoolean()));
    }
}
