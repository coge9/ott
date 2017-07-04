package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Platform;
import com.nbcuni.test.cms.pageobjectutils.mvpd.BrandsMvpdAdmin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dzianis_Kulesh
 *
 */
public enum MvpdJsonAllAvailableFields {

    ADOBE_PASS_ERROR_MAPPING("adobePassErrorMappings", "customErrorMessages", ""),

    // DESKTOP
    TITLE("title", "title", "displayName"),
    FIELD_ENABLE_NEW_WINDOW_WORKFLOW("field_enable_new_window_workflow", "enableNewWindowWorkflow", ""),
    FIELD_DISPLAY_NAME("field_mvpd_display_name", "displayName", "displayName"),
    FIELD_FREEWHEEL_HASH("field_mvpd_freewheel_hash", "freewheelHash", "freewheelHash"),
    FIELD_MVPD_ID("field_mvpd_id", "id", "id"),
    FIELD_MVPD_K2_ID("field_mvpd_k2_id", "k2Id", "k2Id"),
    FIELD_MVPD_LOGO_ALTERNATIVE("field_mvpd_logo_alternative", "mvpdLogoAlternative", ""),
    FIELD_MVPD_LOGO_DARK("field_mvpd_logo_dark", "logoDark", ""),
    FIELD_MVPD_LOGO_LIGHT("field_mvpd_logo_light", "logoLight", ""),
    FIELD_MVPD_URL("field_mvpd_url", "url", "url"),
    FIELD_MVPD_DARK_COLOR("field_mvpd_dark_color", "darkColor", ""),
    FIELD_MVPD_LIGHT_COLOR("field_mvpd_light_color", "lightColor", ""),
    FEATURED("featured", "featured", ""),
    WEIGHT("weight", "weight", "weight"),
    FIELD_WHITE_LOGO_2X("field_white_logo_2x", "whiteLogo2x", ""),
    FIELD_COLOR_LOGO_2X("field_color_logo_2x", "colorLogo2x", ""),
    FIELD_MVPD_GENERIC_ERR("field_mvpd_generic_err", "genericError", ""),
    FIELD_MVPD_INTERNAL_ERR("field_mvpd_internal_err", "internalError", ""),
    FIELD_MVPD_AUTHORIZED_ERR("field_mvpd_authorized_err", "authorizedError", ""),
    IS_DARKER_LOGO("is_darker_logo", "isDarkerLogo", ""),

    // MOBILE
    MVPD("mvpd", "id", "id"),
    PICKER_IMAGE("pickerImage", "pickerImage", ""),
    PICKER_IMAGE_2X("pickerImage_2x", "pickerImage2x", ""),
    LOGGED_IN_IMAGE("loggedInImage", "loggedInImage", ""),
    LOGGED_IN_IMAGE_2X("loggedInImage_2x", "loggedInImage2x", ""),
    PHONE_LOGGED_IN_IMAGE("phoneLoggedInImage", "phoneLoggedInImage", ""),
    PHONE_LOGGED_IN_IMAGE_2X("phoneLoggedInImage_2x", "phoneLoggedInImage2x", ""),
    ADOBE_PASS_END_POINT("adobePassEndPoint", "adobePassEndPoint", ""),
    TIER("tier", "featured", ""),
    ADVERTISING_KEY("advertisingKey", "freewheelHash", "freewheelHash"),

    // XBOXONE
    COLOR_LOGO("colorLogo", "colorLogo", ""),
    COLOR_LOGO_2X("colorLogo2x", "colorLogo2x", ""),
    BLACK_LOGO("blackLogo", "blackLogo", ""),
    BLACK_LOGO_2X("blackLogo2x", "blackLogo2x", ""),
    WHITE_LOGO("whiteLogo", "whiteLogo", ""),
    WHITE_LOGO_2X("whiteLogo2x", "whiteLogo2x", ""),
    IS_NEW_WINDOW("is_new_window", "enableNewWindowWorkflow", ""),

    // APPLETV
    DISPLAY_NAME("displayName", "displayName", "displayName"),

    ///////////////////////////// V2 FIELDS //////////////////////////////////
    MVPD_DISPLAY_NAME("mvpd_display_name", "displayName", "displayName"),
    MVPD_URL("mvpd_url", "url", "url"),
    ENABLE_NEW_WINDOW_WORFLOW("enable_new_window_workflow", "enableNewWindowWorkflow", ""),
    ACTIVATION_PICKER_IMAGE("activationpickerImage", "activationpickerImage", ""),
    ACTIVATION_PICKER_IMAGE_2X("activationpickerImage_2x", "activationpickerImage2x", ""),
    ACTIVATION_LOGGED_IN_IMAGE("activationloggedInImage", "activationloggedInImage", ""),
    ACTIVATION_LOGGED_IN_IMAGE_2X("activationloggedInImage_2x", "activationloggedInImage2x", ""),
    APP_PICKER_IMAGE("apppickerImage", "appPickerImage", ""),
    APP_PICKER_IMAGE_2X("apppickerImage_2x", "appPickerImage2x", ""),
    APP_LOGGED_IN_IMAGE("apploggedInImage", "appLoggedInImage", ""),
    APP_LOGGED_IN_IMAGE_2X("apploggedInImage_2x", "appLoggedInImage2x", ""),
    ORDINAL("ordinal", "weight", "weight"),

    /////////////////////////////Logo type //////////////////////////////////
    MVPD_LOGO("mvpdLogo", "mvpdLogo", "");

    private static MvpdJsonAllAvailableFields[] desktopV1General = {TITLE, FIELD_ENABLE_NEW_WINDOW_WORKFLOW, FIELD_DISPLAY_NAME, FIELD_FREEWHEEL_HASH, FIELD_MVPD_ID, FIELD_MVPD_K2_ID,
            FIELD_MVPD_LOGO_ALTERNATIVE, FIELD_MVPD_LOGO_DARK, FIELD_MVPD_LOGO_LIGHT, FIELD_MVPD_URL, FIELD_MVPD_DARK_COLOR, FIELD_MVPD_LIGHT_COLOR, FEATURED, WEIGHT, FIELD_WHITE_LOGO_2X,
            FIELD_COLOR_LOGO_2X, FIELD_MVPD_GENERIC_ERR, FIELD_MVPD_INTERNAL_ERR, FIELD_MVPD_AUTHORIZED_ERR, IS_DARKER_LOGO};
    private static MvpdJsonAllAvailableFields[] desktopV2General = {MVPD, MVPD_DISPLAY_NAME, MVPD_URL, ENABLE_NEW_WINDOW_WORFLOW, APP_PICKER_IMAGE, APP_PICKER_IMAGE_2X, APP_LOGGED_IN_IMAGE,
            APP_LOGGED_IN_IMAGE_2X, TIER, ORDINAL, ADVERTISING_KEY, ADOBE_PASS_ERROR_MAPPING};
    private static MvpdJsonAllAvailableFields[] mobileV1General = {MVPD, PICKER_IMAGE, PICKER_IMAGE_2X, LOGGED_IN_IMAGE, LOGGED_IN_IMAGE_2X, PHONE_LOGGED_IN_IMAGE, PHONE_LOGGED_IN_IMAGE_2X,
            ADOBE_PASS_END_POINT, TIER, ADVERTISING_KEY, ADOBE_PASS_ERROR_MAPPING};
    private static MvpdJsonAllAvailableFields[] mobileV2General = {MVPD, MVPD_URL, MVPD_DISPLAY_NAME, ACTIVATION_PICKER_IMAGE, ACTIVATION_PICKER_IMAGE_2X, ACTIVATION_LOGGED_IN_IMAGE, ACTIVATION_LOGGED_IN_IMAGE_2X, APP_PICKER_IMAGE, APP_PICKER_IMAGE_2X, APP_LOGGED_IN_IMAGE, APP_LOGGED_IN_IMAGE_2X, TIER, ADVERTISING_KEY, ADOBE_PASS_ERROR_MAPPING};
    private static MvpdJsonAllAvailableFields[] xboxoneV1General = {MVPD, TITLE, PICKER_IMAGE, PICKER_IMAGE_2X, LOGGED_IN_IMAGE, LOGGED_IN_IMAGE_2X, PHONE_LOGGED_IN_IMAGE, PHONE_LOGGED_IN_IMAGE_2X,
            ADOBE_PASS_END_POINT, TIER, COLOR_LOGO, COLOR_LOGO_2X, BLACK_LOGO, BLACK_LOGO_2X, WHITE_LOGO, WHITE_LOGO_2X, ADVERTISING_KEY, IS_NEW_WINDOW, ADOBE_PASS_ERROR_MAPPING};
    private static MvpdJsonAllAvailableFields[] appleTvV1General = {MVPD, TITLE, PICKER_IMAGE, PICKER_IMAGE_2X, LOGGED_IN_IMAGE, LOGGED_IN_IMAGE_2X, PHONE_LOGGED_IN_IMAGE, PHONE_LOGGED_IN_IMAGE_2X,
            ADOBE_PASS_END_POINT, TIER, ADVERTISING_KEY, ADOBE_PASS_ERROR_MAPPING};
    private static MvpdJsonAllAvailableFields[] win8V1General = {MVPD, TITLE, PICKER_IMAGE, PICKER_IMAGE_2X, LOGGED_IN_IMAGE, LOGGED_IN_IMAGE_2X, PHONE_LOGGED_IN_IMAGE, PHONE_LOGGED_IN_IMAGE_2X,
            ADOBE_PASS_END_POINT, TIER, ADVERTISING_KEY, IS_NEW_WINDOW, ADOBE_PASS_ERROR_MAPPING};
    String nameInJson;
    String mappingToJavaField;
    String mappingToMvpdObjectField;

    MvpdJsonAllAvailableFields(String nameInJson, String mappingToJavaField, String mappingToMvpdObjectField) {
        this.nameInJson = nameInJson;
        this.mappingToJavaField = mappingToJavaField;
        this.mappingToMvpdObjectField = mappingToMvpdObjectField;
    }

    public static List<MvpdJsonAllAvailableFields> getListOfFields(MvpdEntitlementsServiceVersion version, Platform platform, BrandsMvpdAdmin brand) {
        switch (version) {
            case V1:
                switch (platform) {
                    case DESKTOP:
                        if (brand == null) {
                            return getDesktopV1GeneralFields();
                        }
                        switch (brand) {
                            case USA:
                                return getDesktopV1NbcEntAndUsaFields();
                            case NBCENTERTAINMENT:
                                return getDesktopV1NbcEntAndUsaFields();
                            default:
                                return getDesktopV1GeneralFields();
                        }
                    case IOS:
                    case MOBILE:
                        if (brand == null) {
                            return getMobileV1GeneralFields();
                        }
                        switch (brand) {
                            case CNBC:
                                return getMobileV1CnbcFields();
                            default:
                                return getMobileV1GeneralFields();
                        }
                    case XBOXONE:
                        if (brand == null) {
                            return getXboxoneV1GeneralFields();
                        }
                        switch (brand) {
                            default:
                                return getXboxoneV1GeneralFields();
                        }
                    case APPLETV:
                        if (brand == null) {
                            return getApplteTvV1GeneralFields();
                        }
                        switch (brand) {
                            case CNBC:
                                return getApplteTvV1CnbcFields();
                            default:
                                return getApplteTvV1GeneralFields();
                        }
                    case WIN8:
                        if (brand == null) {
                            return getWin8V1GeneralFields();
                        }
                        switch (brand) {
                            default:
                                return getWin8V1GeneralFields();
                        }
                    case ROKU:
                    case ANDROID:
                    case AMAZONFIRETV:
                        if (brand == null) {
                            return getApplteTvV1GeneralFields();
                        }
                }
                break;
            case V2:
                switch (platform) {
                    case DESKTOP:
                        if (brand == null) {
                            return getDesktopV2GeneralFields();
                        }
                        switch (brand) {
                            default:
                                return getDesktopV2GeneralFields();
                        }
                    case MOBILE:
                    case XBOXONE:
                    case APPLETV:
                    case WIN8:
                    case IOS:
                    case ANDROID:
                    case AMAZONFIRETV:
                    default:
                        if (brand == null) {
                            return getMobileV2GeneralFields();
                        }
                        switch (brand) {
                            default:
                                return getMobileV2GeneralFields();
                        }
                }
                //break;
        }
        return null;
    }

    // return list of fields for V1 desktop platform (general brand)
    private static List<MvpdJsonAllAvailableFields> getDesktopV1GeneralFields() {
        return Arrays.asList(desktopV1General);
    }

    // return list of fields for V1 desktop platform (for USA and NBCentertainment brands)
    private static List<MvpdJsonAllAvailableFields> getDesktopV1NbcEntAndUsaFields() {
        List<MvpdJsonAllAvailableFields> toReturn = new ArrayList<MvpdJsonAllAvailableFields>(getDesktopV1GeneralFields());
        toReturn.remove(MvpdJsonAllAvailableFields.FIELD_WHITE_LOGO_2X);
        toReturn.remove(MvpdJsonAllAvailableFields.FIELD_COLOR_LOGO_2X);
        return toReturn;
    }

    // return list of fields for V1 desktop platform (general brand)
    private static List<MvpdJsonAllAvailableFields> getDesktopV2GeneralFields() {
        return Arrays.asList(desktopV2General);
    }

/*
 *   GET DESKTOP FIELDS
 * 
 */

    /*
     *   GET MOBILE FIELDS
     *
     */
    private static List<MvpdJsonAllAvailableFields> getMobileV1GeneralFields() {
        return Arrays.asList(mobileV1General);
    }

    private static List<MvpdJsonAllAvailableFields> getMobileV1CnbcFields() {
        List<MvpdJsonAllAvailableFields> toReturn = new ArrayList<MvpdJsonAllAvailableFields>(getMobileV1GeneralFields());
        toReturn.add(MvpdJsonAllAvailableFields.DISPLAY_NAME);
        return toReturn;
    }

    private static List<MvpdJsonAllAvailableFields> getMobileV2GeneralFields() {
        return Arrays.asList(mobileV2General);
    }

    /*
     *   GET XBOXONE FIELDS
     *
     */
    private static List<MvpdJsonAllAvailableFields> getXboxoneV1GeneralFields() {
        return Arrays.asList(xboxoneV1General);
    }

    /*
     *   GET APPLTETV FIELDS
     *
     */
    private static List<MvpdJsonAllAvailableFields> getApplteTvV1GeneralFields() {
        return Arrays.asList(appleTvV1General);
    }

    private static List<MvpdJsonAllAvailableFields> getApplteTvV1CnbcFields() {
        List<MvpdJsonAllAvailableFields> toReturn = new ArrayList<MvpdJsonAllAvailableFields>(getApplteTvV1GeneralFields());
        toReturn.add(MvpdJsonAllAvailableFields.DISPLAY_NAME);
        return toReturn;
    }

    /*
     *   GET APPLTETV FIELDS
     *
     */
    private static List<MvpdJsonAllAvailableFields> getWin8V1GeneralFields() {
        return Arrays.asList(win8V1General);
    }

    public String getFieldNameInJson() {
        return nameInJson;
    }

    public String getFieldMappingToJavaField() {
        return mappingToJavaField;
    }

    public String getMappingToMvpdObjectField() {
        return mappingToMvpdObjectField;
    }
}
