package com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Ivan on 18.03.2016.
 */
public class CreateFactoryPage {

    public static final int LEN = 5;
    private static final String TITLE = "AQA Page %s";
    private static final String ALIAS = "AQA ALIAS%s";
    private static final String MACHINE_NAME = "aqa_machine_name_%s";
    private CreateFactoryPage(){
        super();
    }

    public static synchronized PageForm createDefaultPage() {
        String randomPostfix = SimpleUtils.getRandomString(LEN);
        PageForm pageForm = new PageForm();
        pageForm
                .setTitle(String.format(TITLE, randomPostfix))
                .setPlatform(CmsPlatforms.ROKU);
        return pageForm;
    }

    /**
     * The method generates default TVE Page object
     *
     * @return pageForm object with custom alias
     */
    public static synchronized PageForm createDefaultPageWithAlias() {
        String randomPostfix = SimpleUtils.getRandomString(LEN);
        return createDefaultPage().setAlias(new Slug()
                .setAutoSlug(false)
                .setSlugValue(String.format(ALIAS, randomPostfix)));
    }

    /**
     * He method generates TVE Page object with custom alias and machine name
     *
     * @return pageForm object with custom alias and machine name
     */
    public static synchronized PageForm createDefaultPageWithMachineNameAndAlias() {
        String randomPostfix = SimpleUtils.getRandomString(LEN);
        return createDefaultPage()
                .setAlias(new Slug()
                        .setAutoSlug(false)
                        .setSlugValue(String.format(ALIAS, randomPostfix)))
                .setMachineName(String.format(MACHINE_NAME, randomPostfix.toLowerCase()));
    }
}
