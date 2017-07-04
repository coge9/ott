package com.nbcuni.test.cms.utils.jsonparsing.mvpd;

import com.google.gson.JsonElement;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Platform;
import com.nbcuni.test.cms.pageobjectutils.mvpd.BrandsMvpdAdmin;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFeed;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdEntitlementsServiceVersion;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.IValueValidator;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.ValueValidatorFactory;
import com.nbcuni.test.webdriver.Utilities;

public class MvpdEntitlementsServiceHelper {

    private MvpdEntitlementsServiceVersion version;
    private Platform platform;
    private BrandsMvpdAdmin brand;
    private String brandId;
    private MvpdFeed mvpdFeed;
    private Instance instance;
    private IValueValidator valueValidator;
    private Boolean isLogoTypeParameterPresent = false;

    public MvpdEntitlementsServiceHelper(
            MvpdEntitlementsServiceVersion version, Platform platform,
            String requestorId, Boolean... isLogoTypeParameterPresent) {
        if (isLogoTypeParameterPresent.length > 0 && isLogoTypeParameterPresent[0]) {
            this.isLogoTypeParameterPresent = true;
        }
        this.version = version;
        this.brandId = requestorId;
        this.brand = BrandsMvpdAdmin.getBrandById(requestorId);
        this.platform = platform;
        buildValueValidator();
    }

    public MvpdFeed parseJson(String url) {
        JsonElement elem = JsonParserHelper.getInstance().getJson(url);
        MvpdFeed feed = JsonParserHelper.getInstance().getJavaObjectFromJson(
                elem, MvpdFeed.class);
        if (feed == null) {
            Utilities.logSevereMessageThenFail("There is an error during parsing Mvpd feed");
        }
        mvpdFeed = feed;
        mvpdFeed.setLogotypeFilter(isLogoTypeParameterPresent);
        return feed;
    }

    private void buildValueValidator() {
        Utilities.logInfoMessage("Building JSON value validator");
        valueValidator = ValueValidatorFactory.getValueValidator(version,
                platform, instance);
    }

}
