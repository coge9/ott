package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import com.nbcuni.test.cms.bussinesobjects.mvpd.TestCredentialsEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.mvpd.MVPDServicesFields;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mvpd {

    private static List<MvpdErrorMessage> globalErrorMessages = new ArrayList<MvpdErrorMessage>();

    private String displayName = "";
    private String id = "";
    private String k2Id = "";
    private String url = "";
    private String freewheelHash = "";
    private List<String> alternateMVPDIDs = new ArrayList<String>();
    private boolean isFeatured;
    private String weight;
    private boolean enableNewWindowWorkflow;
    private boolean isDarkerLogo;
    private List<MvpdLogo> logos = new ArrayList<MvpdLogo>();
    private List<MvpdErrorMessage> customErrorMessages = new ArrayList<MvpdErrorMessage>();
    private List<Entitlement> entitlements = new ArrayList<Entitlement>();
    private String usernamePwRecoveryUrl = "";
    private String accountRegistrationUrl = "";
    private String contactPhoneNumber = "";
    private String contactChatUrl = "";
    private String contactMailtoLink = "";
    private String contactFaqLink = "";
    private String contactTwitterUrl = "";
    private String companyUrl = "";
    private String forgotUsernameUrl = "";
    private String proxy = "";
    private String iosAppUrl = "";
    private String androidAppUrl = "";
    private String amazonAppUrl = "";
    private List<TestCredentialsEntity> testCredentials;

    public Mvpd(final String displayName, final String id, final String k2Id,
                final String url, final String freewheelHash,
                final List<String> alternateMVPDIDs,
                final boolean enableNewWindowWorkflow, final String proxy) {
        super();
        this.displayName = displayName;
        this.id = id;
        this.k2Id = k2Id;
        this.url = url;
        this.freewheelHash = freewheelHash;
        this.alternateMVPDIDs = alternateMVPDIDs;
        this.enableNewWindowWorkflow = enableNewWindowWorkflow;
        isDarkerLogo = true;
        this.proxy = proxy;
    }

    public Mvpd() {
        super();
    }

    public static List<MvpdErrorMessage> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public static void setGlobalErrorMessages(
            List<MvpdErrorMessage> globalErrorMessages) {
        Mvpd.globalErrorMessages = globalErrorMessages;
    }

    public boolean isDarkerLogo() {
        return isDarkerLogo;
    }

    public void setDarkerLogo(boolean isDarkerLogo) {
        this.isDarkerLogo = isDarkerLogo;
    }

    public boolean isEnableNewWindowWorkflow() {
        return enableNewWindowWorkflow;
    }

    public void setEnableNewWindowWorkflow(final boolean enableNewWindowWorkflow) {
        this.enableNewWindowWorkflow = enableNewWindowWorkflow;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getK2Id() {
        return k2Id;
    }

    public void setK2Id(final String k2Id) {
        this.k2Id = k2Id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getFreewheelHash() {
        return freewheelHash;
    }

    public void setFreewheelHash(final String freewheelHash) {
        this.freewheelHash = freewheelHash;
    }

    public List<String> getAlternateMVPDIDs() {
        return alternateMVPDIDs;
    }

    public void setAlternateMVPDIDs(final List<String> alternateMVPDIDs) {
        this.alternateMVPDIDs = alternateMVPDIDs;
    }

    public void addAlternativeId(String altId) {
        alternateMVPDIDs.add(altId);
    }

    public void clearAlternativeId() {
        alternateMVPDIDs.clear();
    }

    public List<MvpdErrorMessage> getCustomErrorMessages() {
        return customErrorMessages;
    }

    public void setCustomErrorMessages(
            final List<MvpdErrorMessage> errorMessages) {
        this.customErrorMessages = errorMessages;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(final boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
    }

    public List<MvpdLogo> getLogos() {
        return logos;
    }

    public void setLogos(final List<MvpdLogo> logos) {
        this.logos = logos;
    }

    public void removeLogoByType(LogoTypes type) {
        Iterator<MvpdLogo> it = logos.iterator();
        while (it.hasNext()) {
            if (it.next().getType().equals(type)) {
                it.remove();
            }
        }
    }

    public MvpdLogo getLogoByType(LogoTypes type) {
        for (MvpdLogo logo : logos) {
            if (logo.getType().equals(type)) {
                return logo;
            }
        }
        return null;
    }

    public void clearLogos() {
        this.getLogos().clear();
    }

    public void addLogo(final String path, final String type) {
        LogoTypes logoType = null;
        for (final LogoTypes typeElement : LogoTypes.values()) {
            if (typeElement.get().equals(type)) {
                logoType = typeElement;
            }
        }
        final MvpdLogo logo = new MvpdLogo(logoType, path);
        logos.add(logo);
    }

    public void addLogo(MvpdLogo logo) {
        logos.add(logo);
    }

    public void addErrorMessage(final String messageId,
                                final String messageTitle, final String messageBody,
                                final Boolean useAdobeDesc) {
        final MvpdErrorMessage error = new MvpdErrorMessage(messageId,
                messageTitle, messageBody, useAdobeDesc);
        customErrorMessages.add(error);
    }

    public void addEntitlement(Platform platform, Instance instance,
                               String brandName, Boolean whitelisted) {
        Entitlement entitlement = new Entitlement(platform, instance,
                brandName, whitelisted);
        entitlements.add(entitlement);
    }

    public List<Entitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<Entitlement> entitlements) {
        this.entitlements = entitlements;
    }

    public String getUsernamePwRecoveryUrl() {
        return usernamePwRecoveryUrl;
    }

    public void setUsernamePwRecoveryUrl(String usernamePwRecoveryUrl) {
        this.usernamePwRecoveryUrl = usernamePwRecoveryUrl;
    }

    public String getAccountRegistrationUrl() {
        return accountRegistrationUrl;
    }

    public void setAccountRegistrationUrl(String accountRegistrationUrl) {
        this.accountRegistrationUrl = accountRegistrationUrl;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactChatUrl() {
        return contactChatUrl;
    }

    public void setContactChatUrl(String contactChatUrl) {
        this.contactChatUrl = contactChatUrl;
    }

    public String getContactMailtoLink() {
        return contactMailtoLink;
    }

    public void setContactMailtoLink(String contactMailtoLink) {
        this.contactMailtoLink = contactMailtoLink;
    }

    public String getContactFaqLink() {
        return contactFaqLink;
    }

    public void setContactFaqLink(String contactFaqLink) {
        this.contactFaqLink = contactFaqLink;
    }

    public String getContactTwitterUrl() {
        return contactTwitterUrl;
    }

    public void setContactTwitterUrl(String contactTwitterUrl) {
        this.contactTwitterUrl = contactTwitterUrl;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getForgotUsernameUrl() {
        return forgotUsernameUrl;
    }

    public void setForgotUsernameUrl(String forgotUsernameUrl) {
        this.forgotUsernameUrl = forgotUsernameUrl;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getiOsAppUrl() {
        return iosAppUrl;
    }

    public void setiOsAppUrl(String iOsAppUrl) {
        this.iosAppUrl = iOsAppUrl;
    }

    public String getAndroidAppUrl() {
        return androidAppUrl;
    }

    public void setAndroidAppUrl(String androidAppUrl) {
        this.androidAppUrl = androidAppUrl;
    }

    public String getAmazonAppUrl() {
        return amazonAppUrl;
    }

    public void setAmazonAppUrl(String amazonAppUrl) {
        this.amazonAppUrl = amazonAppUrl;
    }

    public String getIosAppUrl() {
        return iosAppUrl;
    }

    public void setIosAppUrl(String iosAppUrl) {
        this.iosAppUrl = iosAppUrl;
    }

    public List<TestCredentialsEntity> getTestCredentials() {
        return testCredentials;
    }

    public void setTestCredentials(List<TestCredentialsEntity> testCredentials) {
        this.testCredentials = testCredentials;
    }

    public MvpdErrorMessage getCustomErrorMessageById(String id) {
        for (MvpdErrorMessage error : customErrorMessages) {
            if (error.getMessageId().equals(id)) {
                return error;
            }
        }
        return null;
    }

    public MvpdErrorMessage getErrorMessageById(String id) {
        for (MvpdErrorMessage error : getAllMessagesForMvpd()) {
            if (error.getMessageId().equals(id)) {
                return error;
            }
        }
        return null;
    }

    public List<MvpdErrorMessage> getAllMessagesForMvpd() {
        List<MvpdErrorMessage> errors = new ArrayList<MvpdErrorMessage>();
        for (MvpdErrorMessage error : customErrorMessages) {
            errors.add(error);
        }

        for (MvpdErrorMessage defaultError : globalErrorMessages) {
            Boolean isOverriden = false;
            for (MvpdErrorMessage error : customErrorMessages) {
                if (error.getMessageId().equals(defaultError.getMessageId())) {
                    isOverriden = true;
                }
            }
            if (!isOverriden) {
                errors.add(defaultError);
            }
        }
        return errors;
    }

    public String getMvpdField(MVPDServicesFields mvpdField) {
        switch (mvpdField) {
            case FIELD_ACCOUNT_REGISTRATION_URL:
                return accountRegistrationUrl;
            case FIELD_ANDROID_APP_URL:
                return androidAppUrl;
            case FIELD_CONTACT_CHAT_URL:
                return contactChatUrl;
            case FIELD_CONTACT_FAQ_LINK:
                return contactFaqLink;
            case FIELD_CONTACT_MAILTO_LINK:
                return contactMailtoLink;
            case FIELD_CONTACT_PHONE_NUMBER:
                return contactPhoneNumber;
            case FIELD_CONTACT_TWITTER_URL:
                return contactTwitterUrl;
            case FIELD_IOS_APP_URL:
                return iosAppUrl;
            case FIELD_AMAZON_URL:
                return amazonAppUrl;
            case FIELD_MVPD_COMPANY_URL:
                return companyUrl;
            case FIELD_MVPD_FORGOT_USERNAME_URL:
                return forgotUsernameUrl;
            case FIELD_MVPD_FREEWHEEL_HASH:
                return freewheelHash;
            case FIELD_MVPD_K2_ID:
                return k2Id;
            case FIELD_MVPD_PROXY:
                return proxy;
            case FIELD_MVPD_URL:
                return url;
            case FIELD_USERNAME_PW_RECOVERY_URL:
                return usernamePwRecoveryUrl;
            case TITLE:
                return displayName;
            default:
                throw new TestRuntimeException("Wrong MVPD field is passed");
        }
    }

    @Override
    public String toString() {
        return "Mvpd [displayName=" + displayName + ", id=" + id + ", k2Id="
                + k2Id + ", url=" + url + ", freewheelHash=" + freewheelHash
                + ", alternateMVPDIDs=" + alternateMVPDIDs + ", isFeatured="
                + isFeatured + ", weight=" + weight
                + ", enableNewWindowWorkflow=" + enableNewWindowWorkflow
                + ", isDarkerLogo=" + isDarkerLogo + ", usernamePwRecoveryUrl="
                + usernamePwRecoveryUrl + ", accountRegistrationUrl="
                + accountRegistrationUrl + ", contactPhoneNumber="
                + contactPhoneNumber + ", contactChatUrl=" + contactChatUrl
                + ", contactMailtoLink=" + contactMailtoLink
                + ", contactFaqLink=" + contactFaqLink + ", contactTwitterUrl="
                + contactTwitterUrl + ", companyUrl=" + companyUrl
                + ", forgotUsernameUrl=" + forgotUsernameUrl + ", proxy="
                + proxy + ", iosAppUrl=" + iosAppUrl + ", androidAppUrl="
                + androidAppUrl + ", amazonAppUrl=" + amazonAppUrl + "]";
    }

}