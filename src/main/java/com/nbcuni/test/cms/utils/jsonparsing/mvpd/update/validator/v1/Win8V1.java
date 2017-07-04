package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator.v1;

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

public class Win8V1 extends AbstractValueValidator implements IValueValidator {

    private static final MvpdEntitlementsServiceVersion VERSION = MvpdEntitlementsServiceVersion.V1;
    private static final Platform PLATFORM = Platform.WIN8;
    private final Instance instance;

    public Win8V1(Instance instance) {
        this.instance = instance;
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
    public boolean validateSingleMvpdInJson(MvpdFeed mvpdFeed, Mvpd mvpd, Brand brand) {
        return validateDefault(mvpdFeed, mvpd, brand);
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
        status = (validateLogosByPattern(listOfFields, logoMapping, mvpdFromJson, mvpdFeed.getFilePath())) && status;
        return status;
    }

    public boolean validateDefault(MvpdFeed mvpdFeed, Mvpd mvpd, Brand brand) {
        Boolean status = true;
        List<MvpdJsonAllAvailableFields> listOfFields = MvpdJsonAllAvailableFields.getListOfFields(VERSION, PLATFORM,
                BrandsMvpdAdmin.getBrandById(brand.getBrandRequestorId()));
        MvpdFromJson mvpdFromJson = mvpdFeed.getMvpdFromFeedById(mvpd.getId());
        Utilities.logInfoMessage("Validation of mvpd " + mvpd.getId());
        // validate fields with direct mapping
        status = validateFieldsWithDirectMapping(listOfFields, mvpdFromJson, mvpd) && status;
        // validate other fields
        String expectedEnableNewWindowWorkFlow;
        if (mvpd.isEnableNewWindowWorkflow() && !brand.isDisableNewWindowWorkflow()) {
            expectedEnableNewWindowWorkFlow = "1";
        } else {
            expectedEnableNewWindowWorkFlow = "0";
        }
        status = verifyField(MvpdJsonAllAvailableFields.IS_NEW_WINDOW.getFieldNameInJson(),
                mvpdFromJson.getEnableNewWindowWorkflow(), expectedEnableNewWindowWorkFlow) && status;
        String tier;
        if (mvpd.isFeatured()) {
            tier = "1";
        } else {
            tier = "2";
        }
        status = verifyField(MvpdJsonAllAvailableFields.FEATURED.getFieldNameInJson(),
                mvpdFromJson.getFeatured(), tier) && status;
        String expectedAdobePassEndPoint;
        if (instance.equals(Instance.PROD)) {
            expectedAdobePassEndPoint = ADOBE_PASS_END_POINT_PROD;
        } else {
            expectedAdobePassEndPoint = ADOBE_PASS_END_POINT_STAGE;
        }
        status = verifyField(MvpdJsonAllAvailableFields.ADOBE_PASS_END_POINT.getFieldNameInJson(),
                mvpdFromJson.getAdobePassEndPoint(), expectedAdobePassEndPoint) && status;

        // validate error messages
        status = verifyAllMessages(mvpd, mvpdFromJson) && status;
        // validate logos
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = getMappingForLogoV1(brand.getMvpdLogoSettings(), mvpd.getId(), brand.getBrandRequestorId(), PLATFORM);
        status = (validateLogos(listOfFields, logoMapping, mvpdFromJson, mvpd, mvpdFeed.getFilePath())) && status;
        return status;
    }
}
