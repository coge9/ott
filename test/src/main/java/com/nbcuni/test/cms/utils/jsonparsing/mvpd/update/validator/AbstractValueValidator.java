package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.validator;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.*;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFromJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdJsonAllAvailableFields;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractValueValidator {

    protected static final String ADOBE_PASS_END_POINT_PROD = "https://sp.auth.adobe.com";
    protected static final String ADOBE_PASS_END_POINT_STAGE = "https://sp.auth-staging.adobe.com";
    private static final String IGNORED_ERROR_MESSAGE = "User not Authorized Error, Your Parental Control settings do not allow you to view this content.";
    private static final String NOT_APPLICAPLE = "NOT_APPLICABLE";
    public static final String FIELD = "field '";

    protected boolean verifyField(String fieldName, String actualValue,
                                  String expectedValue) {
        boolean status = true;
        if (!(expectedValue == null && actualValue == null)) {
            if (expectedValue == null || actualValue == null) {
                status = false;
                Utilities.logSevereMessage(FIELD + fieldName + "' is wrong. Expected ["
                        + expectedValue + "], but found [" + actualValue + "]");
            } else {
                if (!expectedValue.equals(actualValue)) {
                    status = false;
                    Utilities.logSevereMessage(FIELD + fieldName
                            + "' is wrong. Expected [" + expectedValue
                            + "], but found [" + actualValue + "]");
                }
            }
        }
        return status;
    }

    protected boolean verifyFieldByPattern(String fieldName,
                                           String actualValue, String pattern) {
        boolean status = true;
        if (pattern == null) {
            status = false;
            Utilities.logSevereMessage("There is an error during pattern creation");
        } else if (pattern.equalsIgnoreCase(NOT_APPLICAPLE)) {
            if (actualValue != null) {
                status = false;
                Utilities.logSevereMessage(FIELD + fieldName
                        + "' is wrong. Expected [null], but found ["
                        + actualValue + "]");

            }
        } else if (actualValue == null) {
            status = false;
            Utilities.logSevereMessage(FIELD + fieldName + "' is absent in JSON. ");
        } else if (!RegexUtil.isMatch(actualValue, pattern)) {
            status = false;
            Utilities.logSevereMessage(FIELD + fieldName
                    + "' with value '" + actualValue + "'  doesn't match the pattern [" + pattern + "]");
        }
        return status;
    }

    protected boolean validateFieldsWithDirectMapping(
            List<MvpdJsonAllAvailableFields> fields, MvpdFromJson mvpdInJson,
            Mvpd mvpd) {
        Boolean status = true;
        for (MvpdJsonAllAvailableFields field : fields) {
            if (!"".equals(field.getMappingToMvpdObjectField())) {
                Field mvpdInJsonReflectField;
                Field mvpdReflectField;
                try {
                    mvpdInJsonReflectField = mvpdInJson.getClass()
                            .getDeclaredField(
                                    field.getFieldMappingToJavaField());
                    mvpdReflectField = mvpd.getClass().getDeclaredField(
                            field.getMappingToMvpdObjectField());
                    mvpdInJsonReflectField.setAccessible(true);
                    mvpdReflectField.setAccessible(true);
                    String actual = (String) mvpdInJsonReflectField
                            .get(mvpdInJson);
                    String expected = (String) mvpdReflectField.get(mvpd);
                    status = verifyField(field.getFieldNameInJson(), actual,
                            expected) &&  status;
                } catch (Exception e) {
                    Utilities.logSevereMessage("Error during getting field by Reflection mechanism "
                            + e.getMessage());
                }
            }
        }
        return status;
    }

    protected Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> getMappingForLogoV1(
            MvpdLogoSettings mvpdLogoSettings, String mvpdId,
            String brandRequestorId, Platform platform) {
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = new HashMap<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl>();
        MvpdLogoSettingItem settingItem = mvpdLogoSettings
                .getItemByPlatformSectionBrand(platform,
                        SelectLogoSection.APP_PICKER, brandRequestorId);
        switch (settingItem.getType()) {
            case WHITE: {
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
            }
            break;
            case BLACK: {
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
            }
            break;
            case COLOR: {
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
            }
            break;
            case CUSTOM:
                String method = settingItem.getImageServiceMethod();
                String fallBack = settingItem.getFallbackLogo().getName();

                String stitchedLogo = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO, LogoTypes.ATV_LOGO,
                        mvpdId, brandRequestorId, fallBack);
                String stitchedLogo2x = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO_2X,
                        LogoTypes.ATV_LOGO_2X, mvpdId, brandRequestorId, fallBack);
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(null, stitchedLogo));
                logoMapping.put(MvpdJsonAllAvailableFields.PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(null, stitchedLogo2x));
                break;
            default:
                break;
        }
        settingItem = mvpdLogoSettings
                .getItemByPlatformSectionBrand(platform,
                        SelectLogoSection.APP_POST_LOGGED_IN, brandRequestorId);
        switch (settingItem.getType()) {
            case WHITE: {
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
                logoMapping.put(MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
            }
            break;
            case BLACK: {
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
                logoMapping.put(MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
            }
            break;
            case COLOR: {
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
                logoMapping.put(MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
            }
            break;
            case CUSTOM:
                String method = settingItem.getImageServiceMethod();
                String fallBack = settingItem.getFallbackLogo().getName();
                String stitchedLogo = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO, LogoTypes.ATV_LOGO,
                        mvpdId, brandRequestorId, fallBack);
                String stitchedLogo2x = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO_2X,
                        LogoTypes.ATV_LOGO_2X, mvpdId, brandRequestorId, fallBack);
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(null, stitchedLogo));
                logoMapping.put(MvpdJsonAllAvailableFields.LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(null, stitchedLogo2x));
                logoMapping.put(MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(null, stitchedLogo));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.PHONE_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(null, stitchedLogo2x));
                break;
            default:
                break;
        }
        logoMapping.put(MvpdJsonAllAvailableFields.COLOR_LOGO,
                new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
        logoMapping.put(MvpdJsonAllAvailableFields.COLOR_LOGO_2X,
                new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
        logoMapping.put(MvpdJsonAllAvailableFields.BLACK_LOGO,
                new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
        logoMapping.put(MvpdJsonAllAvailableFields.BLACK_LOGO_2X,
                new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
        logoMapping.put(MvpdJsonAllAvailableFields.WHITE_LOGO,
                new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
        logoMapping.put(MvpdJsonAllAvailableFields.WHITE_LOGO_2X,
                new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
        return logoMapping;
    }

    protected Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> getMappingForLogoV2(
            MvpdLogoSettings mvpdLogoSettings, String mvpdId,
            String brandRequestorId, Platform platform) {
        Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping = new HashMap<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl>();
        MvpdLogoSettingItem settingItem = mvpdLogoSettings.getItemByPlatformSectionBrand(platform,
                SelectLogoSection.APP_PICKER, brandRequestorId);
        switch (settingItem.getType()) {
            case WHITE: {
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
            }
            break;
            case BLACK: {
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
            }
            break;
            case COLOR: {
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
            }
            break;
            case CUSTOM:
                String method = settingItem.getImageServiceMethod();
                String fallBack = settingItem.getFallbackLogo().getName();
                String stitchedLogo = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO, LogoTypes.ATV_LOGO,
                        mvpdId, brandRequestorId, fallBack);
                String stitchedLogo2x = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO_2X,
                        LogoTypes.ATV_LOGO_2X, mvpdId, brandRequestorId, fallBack);
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(null, stitchedLogo));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(null, stitchedLogo2x));
                break;
            default:
                break;
        }
        settingItem = mvpdLogoSettings.getItemByPlatformSectionBrand(platform,
                SelectLogoSection.APP_POST_LOGGED_IN, brandRequestorId);
        switch (settingItem.getType()) {
            case WHITE: {
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
            }
            break;
            case BLACK: {
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
            }
            break;
            case COLOR: {
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
            }
            break;
            case CUSTOM:
                String method = settingItem.getImageServiceMethod();
                String fallBack = settingItem.getFallbackLogo().getName();
                String stitchedLogo = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO, LogoTypes.ATV_LOGO,
                        mvpdId, brandRequestorId, fallBack);
                String stitchedLogo2x = Config.getInstance().getStitchedServiceUrl(
                        method, platform, LogoTypes.ATV_LOGO_2X,
                        LogoTypes.ATV_LOGO_2X, mvpdId, brandRequestorId, fallBack);
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(null, stitchedLogo));
                logoMapping.put(MvpdJsonAllAvailableFields.APP_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(null, stitchedLogo2x));
                break;
            default:
                break;
        }
        settingItem = mvpdLogoSettings.getItemByPlatformSectionBrand(platform,
                SelectLogoSection.ACTIVATION_PAGE_PICKER, brandRequestorId);
        switch (settingItem.getType()) {
            case WHITE: {
                logoMapping.put(MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
            }
            break;
            case BLACK: {
                logoMapping.put(MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
            }
            break;
            case COLOR: {
                logoMapping.put(MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
            }
            break;
            case NA:
                logoMapping.put(MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.NONE, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_PICKER_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.NONE, null));
                break;
            default:
                break;
        }
        settingItem = mvpdLogoSettings.getItemByPlatformSectionBrand(platform,
                SelectLogoSection.ACTIVATION_PAGE_POST_LOGGED_IN, brandRequestorId);
        switch (settingItem.getType()) {
            case WHITE: {
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.WHITE_LOGO_2X, null));
            }
            break;
            case BLACK: {
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.BLACK_LOGO_2X, null));
            }
            break;
            case COLOR: {
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.COLOR_LOGO_2X, null));
            }
            break;
            case NA:
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE,
                        new LogoTypeWithCustomUrl(LogoTypes.NONE, null));
                logoMapping.put(
                        MvpdJsonAllAvailableFields.ACTIVATION_LOGGED_IN_IMAGE_2X,
                        new LogoTypeWithCustomUrl(LogoTypes.NONE, null));
                break;
            default:
                break;
        }
        return logoMapping;
    }

    protected boolean validateLogos(
            List<MvpdJsonAllAvailableFields> listOfFields,
            Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping,
            MvpdFromJson mvpdFromJson, Mvpd mvpd, String globalPath) {
        Boolean status = true;
        for (MvpdJsonAllAvailableFields jsonField : listOfFields) {
            LogoTypeWithCustomUrl logoTypeForField = logoMapping.get(jsonField);
            if (logoTypeForField == null) {
                continue;
            }
            String expected;
            if (logoTypeForField.getUrl() != null) {
                expected = logoTypeForField.getUrl();
            } else if (logoTypeForField.getType().equals(LogoTypes.NONE)) {
                expected = null;
            } else {
                MvpdLogo logo = mvpd.getLogoByType(logoTypeForField.getType());
                if (logo == null) {
                    expected = "";
                } else {
                    expected = logo.getPath();
                }
            }
            if (globalPath != null && expected != null) {
                expected = expected.replace(globalPath, "");
            }
            String actual = mvpdFromJson.getFieldValue(jsonField);
            status = verifyField(jsonField.getFieldNameInJson(), actual,
                    expected) && status;
        }
        return status;
    }

    protected boolean validateLogosByPattern(
            List<MvpdJsonAllAvailableFields> listOfFields,
            Map<MvpdJsonAllAvailableFields, LogoTypeWithCustomUrl> logoMapping,
            MvpdFromJson mvpdFromJson, String globalPath) {
        Boolean status = true;
        for (MvpdJsonAllAvailableFields jsonField : listOfFields) {
            LogoTypeWithCustomUrl logoTypeForField = logoMapping.get(jsonField);
            if (logoTypeForField == null) {
                continue;
            }
            String expectedPattern;
            if (logoTypeForField.getType().equals(LogoTypes.NONE)) {
                expectedPattern = "NOT_APPLICABLE";
            } else {
                String logoPart = logoTypeForField.getType()
                        .getBulkUploadName();
                String pattern = Config.getInstance()
                        .getMvpdImagesUrlPatternConstantPart();
                String mvpdUrl = Config.getInstance().getMvpdAdminURL() + "/";
                if (globalPath != null) {
                    mvpdUrl = "";
                }
                expectedPattern = mvpdUrl
                        + String.format(pattern, mvpdFromJson.getId(),
                        mvpdFromJson.getId(), logoPart);
            }
            String actual = mvpdFromJson.getFieldValue(jsonField);
            if (actual != null) {
                actual = actual.toLowerCase();
            }
            status = verifyFieldByPattern(jsonField.getFieldNameInJson(),
                    actual, expectedPattern.toLowerCase()) && status;
        }
        return status;
    }

    protected boolean verifyOverridenMessages(Mvpd mvpd,
                                              MvpdFromJson mvpdFromJson) {
        return verifyErrors(mvpd.getCustomErrorMessages(),
                mvpdFromJson.getCustomErrorMessages());
    }

    protected boolean verifyAllMessages(Mvpd mvpd, MvpdFromJson mvpdFromJson) {
        List<MvpdErrorMessage> expList = new ArrayList<MvpdErrorMessage>(
                mvpd.getAllMessagesForMvpd());
        MvpdErrorMessage errorMessage = mvpd
                .getErrorMessageById(IGNORED_ERROR_MESSAGE);
        if (errorMessage != null) {
            expList.remove(errorMessage);
        }
        return verifyErrors(expList, mvpdFromJson.getCustomErrorMessages());
    }

    private boolean verifyErrors(List<MvpdErrorMessage> expList,
                                 List<MvpdErrorMessage> actualList) {
        boolean status = true;
        List<String> idsList = new ArrayList<String>();

        if (expList.size() != actualList.size()) {
            Utilities.logSevereMessage("Error messages lists have a diferent sizes. Expected: "
                    + expList.size() + ", but found: " + actualList.size());
            status = false;
        } else {
            for (MvpdErrorMessage mvpdErrorMessage : expList) {
                boolean errorPresent = false;
                for (MvpdErrorMessage mvpdFromJsonErrorMessage : actualList) {
                    if (mvpdErrorMessage.getMessageId().equals(
                            mvpdFromJsonErrorMessage.getMessageId())) {
                        errorPresent = true;
                        status = mvpdErrorMessage
                                .verifyErrorMessages(mvpdFromJsonErrorMessage) && status;
                        idsList.add(mvpdFromJsonErrorMessage.getMessageId());
                        break;
                    }
                }
                if (!errorPresent) {
                    status = false;
                    Utilities.logInfoMessage("Error message with id ["
                            + mvpdErrorMessage.getMessageId()
                            + "]dosent present in error message list");
                }

            }
            for (MvpdErrorMessage mvpdFromJsonErrorMessage : actualList) {
                if (!idsList.contains(mvpdFromJsonErrorMessage.getMessageId())) {
                    Utilities.logInfoMessage("Error message with id ["
                            + mvpdFromJsonErrorMessage.getMessageId()
                            + "] is extra");
                }
            }
        }
        return status;
    }

}
