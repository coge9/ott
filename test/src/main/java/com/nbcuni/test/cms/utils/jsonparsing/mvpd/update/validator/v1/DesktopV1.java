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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesktopV1 extends AbstractValueValidator implements
        IValueValidator {

    private static final MvpdEntitlementsServiceVersion VERSION = MvpdEntitlementsServiceVersion.V1;
    private static final Platform PLATFORM = Platform.DESKTOP;
    private final Instance instance;

    public DesktopV1(Instance instance) {
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
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = getMappingForLogoGeneral();
        status = (validateLogosByPattern(listOfFields, logoMapping, mvpdFromJson, mvpdFeed.getFilePath())) && status;
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
        status = validateFieldsWithDirectMapping(listOfFields, mvpdFromJson, mvpd)  && status;
        // validate other fields
        String expectedEnableNewWindowWorkFlow;
        if (mvpd.isEnableNewWindowWorkflow() && !brand.isDisableNewWindowWorkflow()) {
            expectedEnableNewWindowWorkFlow = "1";
        } else {
            expectedEnableNewWindowWorkFlow = "0";
        }
        status = verifyField(MvpdJsonAllAvailableFields.FIELD_ENABLE_NEW_WINDOW_WORKFLOW.getFieldNameInJson(),
                mvpdFromJson.getEnableNewWindowWorkflow(), expectedEnableNewWindowWorkFlow)  && status;
        status = verifyField(MvpdJsonAllAvailableFields.FIELD_MVPD_LOGO_ALTERNATIVE.getFieldNameInJson(),
                mvpdFromJson.getMvpdLogoAlternative(), "0") && status;
        String expectedFeatured;
        if (mvpd.isFeatured()) {
            expectedFeatured = "1";
        } else {
            expectedFeatured = "0";
        }
        status = verifyField(MvpdJsonAllAvailableFields.FEATURED.getFieldNameInJson(),
                mvpdFromJson.getFeatured(), expectedFeatured) && status;
        // validate error messages
        status = verifyErrorMessages(mvpd, mvpdFromJson) && status;
        // validate logos
        status = validateLogos(listOfFields, getMappingForLogoGeneral(), mvpdFromJson, mvpd, mvpdFeed.getFilePath()) && status;
        return status;
    }


    private Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> getMappingForLogoGeneral() {
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = new HashMap<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl>();
        logoMapping.put(MvpdJsonAllAvailableFields.FIELD_MVPD_LOGO_DARK, new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
        logoMapping.put(MvpdJsonAllAvailableFields.FIELD_MVPD_LOGO_LIGHT, new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
        logoMapping.put(MvpdJsonAllAvailableFields.FIELD_MVPD_DARK_COLOR, new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
        logoMapping.put(MvpdJsonAllAvailableFields.FIELD_MVPD_LIGHT_COLOR, new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
        logoMapping.put(MvpdJsonAllAvailableFields.FIELD_WHITE_LOGO_2X, new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
        logoMapping.put(MvpdJsonAllAvailableFields.FIELD_COLOR_LOGO_2X, new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
        return logoMapping;
    }

    private boolean verifyErrorMessages(Mvpd mvpd, MvpdFromJson mvpdFromJson) {
        Boolean status = true;
        List<MvpdErrorMessage> errorMessages = mvpd.getAllMessagesForMvpd();
        if (errorMessages != null && !errorMessages.isEmpty()) {
            for (final MvpdErrorMessage error : errorMessages) {
                if ("User Not Authorized Error".equals(error.getMessageId())) {
                    if (!error.isUseAdobeDesc()) {
                        status = verifyField(MvpdJsonAllAvailableFields.FIELD_MVPD_AUTHORIZED_ERR.getFieldNameInJson(), mvpdFromJson.getAuthorizedError(), error.getMessageBody()) && status;
                    } else {
                        status = verifyField(MvpdJsonAllAvailableFields.FIELD_MVPD_AUTHORIZED_ERR.getFieldNameInJson(), mvpdFromJson.getAuthorizedError(), "") && status;
                    }
                }
                if ("Generic Authorization Error".equals(error.getMessageId())) {
                    if (!error.isUseAdobeDesc()) {
                        status = verifyField(MvpdJsonAllAvailableFields.FIELD_MVPD_GENERIC_ERR.getFieldNameInJson(), mvpdFromJson.getGenericError(), error.getMessageBody()) && status;
                    } else {
                        status = verifyField(MvpdJsonAllAvailableFields.FIELD_MVPD_GENERIC_ERR.getFieldNameInJson(), mvpdFromJson.getGenericError(), "") && status;
                    }
                }
                if ("Internal Authorization Error".equals(error.getMessageId())) {
                    if (!error.isUseAdobeDesc()) {
                        status = verifyField(MvpdJsonAllAvailableFields.FIELD_MVPD_INTERNAL_ERR.getFieldNameInJson(), mvpdFromJson.getInternalError(), error.getMessageBody()) && status;
                    } else {
                        status = verifyField(MvpdJsonAllAvailableFields.FIELD_MVPD_INTERNAL_ERR.getFieldNameInJson(), mvpdFromJson.getInternalError(), "") && status;
                    }
                }
            }
        }
        return status;
    }

}
