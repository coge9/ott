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

public class AppleTvV1 extends AbstractValueValidator implements IValueValidator {

    private static final MvpdEntitlementsServiceVersion VERSION = MvpdEntitlementsServiceVersion.V1;
    private static final Platform PLATFORM = Platform.APPLETV;
    private final Instance instance;

    public AppleTvV1(Instance instance) {
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

    private boolean validateDefault(MvpdFeed mvpdFeed, Mvpd mvpd,
                                    Brand brand) {
        Boolean status = true;
        List<MvpdJsonAllAvailableFields> listOfFields = MvpdJsonAllAvailableFields.getListOfFields(VERSION, PLATFORM,
                BrandsMvpdAdmin.getBrandById(brand.getBrandRequestorId()));
        MvpdFromJson mvpdFromJson = mvpdFeed.getMvpdFromFeedById(mvpd.getId());
        Utilities.logInfoMessage("Validation of mvpd " + mvpd.getId());
        // validate fields with direct mapping
        status = validateFieldsWithDirectMapping(listOfFields, mvpdFromJson, mvpd) &&  status;
        // validate other fields
        String expectedTier;
        if (mvpd.isFeatured()) {
            expectedTier = "1";
        } else {
            expectedTier = "2";
        }
        status = verifyField(MvpdJsonAllAvailableFields.TIER.getFieldNameInJson(),
                mvpdFromJson.getFeatured(), expectedTier) && status;

        // validate error messages
        if (BrandsMvpdAdmin.getBrandById(brand.getBrandRequestorId()).equals(BrandsMvpdAdmin.CNBC)) {
            status = verifyErrorMessages(mvpd, mvpdFromJson) && status;
        } else {
            status = verifyAllMessages(mvpd, mvpdFromJson) && status;
        }
        // validate logos
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = getMappingForLogoV1(brand.getMvpdLogoSettings(), mvpd.getId(), brand.getBrandRequestorId(), PLATFORM);
        status = (validateLogos(listOfFields, logoMapping, mvpdFromJson, mvpd, mvpdFeed.getFilePath())) && status;
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
