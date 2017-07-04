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

public class IosV1 extends AbstractValueValidator implements IValueValidator {

    private static final MvpdEntitlementsServiceVersion VERSION = MvpdEntitlementsServiceVersion.V1;
    private static final Platform PLATFORM = Platform.IOS;
    private final Instance instance;

    public IosV1(Instance instance) {
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
        status = status & (validateLogosByPattern(listOfFields, logoMapping, mvpdFromJson, mvpdFeed.getFilePath()));
        return status;
    }

    public boolean validateDefault(MvpdFeed mvpdFeed, Mvpd mvpd, Brand brand) {
        List<MvpdJsonAllAvailableFields> listOfFields = MvpdJsonAllAvailableFields.getListOfFields(VERSION, PLATFORM,
                BrandsMvpdAdmin.getBrandById(brand.getBrandRequestorId()));
        Boolean status = true;
        MvpdFromJson mvpdFromJson = mvpdFeed.getMvpdFromFeedById(mvpd.getId());
        Utilities.logInfoMessage("Validation of mvpd " + mvpd.getId());
        // validate fields with direct mapping
        status = status & validateFieldsWithDirectMapping(listOfFields, mvpdFromJson, mvpd);
        // validate other fields
        String tier;
        if (mvpd.isFeatured()) {
            tier = "1";
        } else {
            tier = "2";
        }
        status = status & verifyField(MvpdJsonAllAvailableFields.FEATURED.getFieldNameInJson(),
                mvpdFromJson.getFeatured(), tier);

        String expectedAdobePassEndPoint;
        if (instance.equals(Instance.PROD)) {
            expectedAdobePassEndPoint = ADOBE_PASS_END_POINT_PROD;
        } else {
            expectedAdobePassEndPoint = ADOBE_PASS_END_POINT_STAGE;
        }

        status = status & verifyField(MvpdJsonAllAvailableFields.ADOBE_PASS_END_POINT.getFieldNameInJson(),
                mvpdFromJson.getAdobePassEndPoint(), expectedAdobePassEndPoint);

        // validate error messages
        if (BrandsMvpdAdmin.getBrandById(brand.getBrandRequestorId()).equals(BrandsMvpdAdmin.CNBC)) {
            status = status & verifyErrorMessages(mvpd, mvpdFromJson);
        } else {
            status = status & verifyAllMessages(mvpd, mvpdFromJson);
        }
        // validate logos
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = getMappingForLogoV1(brand.getMvpdLogoSettings(), mvpd.getId(), brand.getBrandRequestorId(), PLATFORM);
        status = status & (validateLogos(listOfFields, logoMapping, mvpdFromJson, mvpd, mvpdFeed.getFilePath()));
        return status;
    }

    private boolean verifyErrorMessages(Mvpd mvpd, MvpdFromJson mvpdFromJson) {
        if (!mvpdFromJson.getCustomErrorMessages().isEmpty()) {
            Utilities.logSevereMessage("List of error messages is not empty for mvpd with id: " + mvpd.getId());
            return false;
        }
        return true;
    }


}
