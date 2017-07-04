package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.v2;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.*;
import com.nbcuni.test.cms.pageobjectutils.mvpd.BrandsMvpdAdmin;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFeed;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFromJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdEntitlementsServiceVersion;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdJsonAllAvailableFields;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.AbstractValueValidator;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.IValueValidator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;
import java.util.Map;

public class DesktopV2 extends AbstractValueValidator implements
        IValueValidator {

    private static final MvpdEntitlementsServiceVersion VERSION = MvpdEntitlementsServiceVersion.V2;
    private static final Platform PLATFORM = Platform.DESKTOP;
    private final Instance instance;

    public DesktopV2(Instance instance) {
        this.instance = instance;
    }

    @Override
    public boolean validateSingleMvpdInJson(MvpdFeed mvpdFeed, Mvpd mvpd, Brand brand) {
        return validateDefault(mvpdFeed, mvpd, brand);
    }

    @Override
    public boolean validateGeneralInformationInTheFeed(MvpdFeed expectedFeed) {

        return false;
    }

    @Override
    public boolean validateAllMvpdsInJson(List<Mvpd> mvpdList) {

        return false;
    }

    @Override
    public boolean verifyAllMvpdPresent(List<Mvpd> mvpdList) {

        return false;
    }

    @Override
    public boolean validateLogoFormat(MvpdFeed mvpdFeed, String mvpdId,
                                      String brandRequestorId, MvpdLogoSettings logoSettings) {
        Boolean status = true;
        List<MvpdJsonAllAvailableFields> listOfFields = MvpdJsonAllAvailableFields.getListOfFields(VERSION, PLATFORM,
                BrandsMvpdAdmin.getBrandById(brandRequestorId));
        MvpdFromJson mvpdFromJson = mvpdFeed.getMvpdFromFeedById(mvpdId);
        Utilities.logInfoMessage("Validation logo format for " + mvpdId);
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = getMappingForLogoV1(logoSettings, mvpdId, brandRequestorId, PLATFORM);
        status = status & (validateLogosByPattern(listOfFields, logoMapping, mvpdFromJson, mvpdFeed.getFilePath()));
        return status;
    }

    public boolean validateDefault(MvpdFeed mvpdFeed, Mvpd mvpd,
                                   Brand brand) {
        Boolean status = true;
        List<MvpdJsonAllAvailableFields> listOfFields = MvpdJsonAllAvailableFields.getListOfFields(VERSION, PLATFORM,
                BrandsMvpdAdmin.getBrandById(brand.getBrandRequestorId()));
        MvpdFromJson mvpdFromJson = mvpdFeed.getMvpdFromFeedById(mvpd.getId());
        Utilities.logInfoMessage("Validation of mvpd " + mvpd.getId());
        // validate fields with direct mapping
        status = status & validateFieldsWithDirectMapping(listOfFields, mvpdFromJson, mvpd);
        // validate other fields
        String expectedEnableNewWindowWorkFlow;
        if (mvpd.isEnableNewWindowWorkflow() && !brand.isDisableNewWindowWorkflow()) {
            expectedEnableNewWindowWorkFlow = "1";
        } else {
            expectedEnableNewWindowWorkFlow = "0";
        }
        status = status & verifyField(MvpdJsonAllAvailableFields.ENABLE_NEW_WINDOW_WORFLOW.getFieldNameInJson(),
                mvpdFromJson.getEnableNewWindowWorkflow(), expectedEnableNewWindowWorkFlow);
        String expectedTier;
        if (mvpd.isFeatured()) {
            expectedTier = "1";
        } else {
            expectedTier = "0";
        }
        status = status & verifyField(MvpdJsonAllAvailableFields.TIER.getFieldNameInJson(),
                mvpdFromJson.getFeatured(), expectedTier);
        // validate error messages
        status = status & verifyOverridenMessages(mvpd, mvpdFromJson);
        if (mvpdFeed.isLogotypeFilter()) {
            MvpdLogo logo = mvpd.getLogoByType(LogoTypes.getTypeByUploadName(mvpdFeed.getLogoTypefilterValue()));
            String expectedLogoInJson = "";
            if (logo != null) {
                expectedLogoInJson = logo.getPath();
                expectedLogoInJson = expectedLogoInJson.replace(mvpdFeed.getFilePath(), "");
            }
            status = status
                    & verifyField(
                    MvpdJsonAllAvailableFields.MVPD_LOGO.getFieldNameInJson(),
                    mvpdFromJson.getMvpdLogo(), expectedLogoInJson);
        } else {
            // validate logos
            status = status & validateLogos(listOfFields, getMappingForLogoV2(brand.getMvpdLogoSettings(), mvpd.getId(), brand.getBrandRequestorId(), PLATFORM), mvpdFromJson, mvpd, mvpdFeed.getFilePath());
        }
        return status;
    }

}
