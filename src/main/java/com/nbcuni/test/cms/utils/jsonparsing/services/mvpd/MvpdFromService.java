package com.nbcuni.test.cms.utils.jsonparsing.services.mvpd;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Mvpd;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class MvpdFromService {

    List<String> mvpdIds;
    String k2Id;
    String url;
    String freewheelHash;
    String proxy;
    String iosAppUrl;
    String androidAppUrl;
    String amazonAppUrl;
    String usernamePwRecoveryUrl;
    String accountRegistrationUrl;
    String contactPhoneNumber;
    String contactChatUrl;
    String contactMailtoLink;
    String contactTwitterUrl;
    String contactFaqLink;
    String companyUrl;
    String forgotUsernameUrl;
    String title;

    public MvpdFromService() {
        super();
        mvpdIds = new LinkedList<String>();
    }

    public List<String> getMvpdIds() {
        return mvpdIds;
    }

    public void setMvpdIds(List<String> mvpdIds) {
        this.mvpdIds = mvpdIds;
    }

    public void addMvpdId(final String id) {
        mvpdIds.add(id);
    }

    public void clearIds() {
        mvpdIds.clear();
    }

    public String getK2Id() {
        return k2Id;
    }

    public void setK2Id(String k2Id) {
        this.k2Id = k2Id;
    }

    public String getMvpdUrl() {
        return url;
    }

    public void setMvpdUrl(String mvpdUrl) {
        this.url = mvpdUrl;
    }

    public String getFreewheelHash() {
        return freewheelHash;
    }

    public void setFreewheelHash(String freewheelHash) {
        this.freewheelHash = freewheelHash;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getIosAppUrl() {
        return iosAppUrl;
    }

    public void setIosAppUrl(String iosAppUrl) {
        this.iosAppUrl = iosAppUrl;
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

    public String getPwRecoveryUrl() {
        return usernamePwRecoveryUrl;
    }

    public void setPwRecoveryUrl(String pwRecoveryUrl) {
        this.usernamePwRecoveryUrl = pwRecoveryUrl;
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

    public String getContactTwitterUrl() {
        return contactTwitterUrl;
    }

    public void setContactTwitterUrl(String contactTwitterUrl) {
        this.contactTwitterUrl = contactTwitterUrl;
    }

    public String getContactFaqLink() {
        return contactFaqLink;
    }

    public void setContactFaqLink(String contactFaqLink) {
        this.contactFaqLink = contactFaqLink;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getForgotUserNameUrl() {
        return forgotUsernameUrl;
    }

    public void setForgotUserNameUrl(String forgotUserNameUrl) {
        this.forgotUsernameUrl = forgotUserNameUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean verifyInfo(Mvpd mvpd) {
        Boolean status = true;
        Utilities.logInfoMessage("Verify JSON element with title " + title);
        for (MVPDServicesFields field : MVPDServicesFields.values()) {
            status = status & verifyField(mvpd, field);
        }
        return status;
    }

    public boolean verifyInfo(Mvpd mvpd, CustomFilterBuilder filters) {
        Boolean status = true;
        if (filters.isAllFiltersDisable()) {
            status = verifyInfo(mvpd);
        } else {
            if (filters.isFilterEnabled(MvpdServiceFilters.BASIC_FILTER)) {
                for (MVPDServicesFields field : MVPDServicesFields
                        .getFieldsForBasicFilter()) {
                    status = status & verifyField(mvpd, field);
                }
            }
            if (filters.isFilterEnabled(MvpdServiceFilters.SUPPORT_FILTER)) {
                for (MVPDServicesFields field : MVPDServicesFields
                        .getFieldsForSupportFilter()) {
                    status = status & verifyField(mvpd, field);
                }
            }
            if (filters.isFilterEnabled(MvpdServiceFilters.MVPD_IDS_FILTER)) {
                MVPDServicesFields field = MVPDServicesFields.FIELD_MVPD_IDS;
                status = status & verifyField(mvpd, field);
            }

            for (MvpdServiceFilters filter : MvpdServiceFilters.values()) {
                if (!filter.equals(MvpdServiceFilters.BASIC_FILTER)
                        && !filter.equals(MvpdServiceFilters.SUPPORT_FILTER)
                        && !filter
                        .equals(MvpdServiceFilters.NOT_EXISTED_FILTER)
                        && !filter.equals(MvpdServiceFilters.MVPD_IDS_FILTER)) {
                    if (filters.isFilterEnabled(filter)) {
                        String filterName = filter.get();
                        MVPDServicesFields field = MVPDServicesFields
                                .getFieldByName(filterName);
                        if (field != null) {
                            status = status & verifyField(mvpd, field);
                        } else {
                            Utilities.logSevereMessage("There is no field named "
                                    + filterName);
                        }
                    }
                }
            }
            status = status & verifyField(mvpd, MVPDServicesFields.TITLE);
        }
        return status;
    }

    private boolean verifyField(Mvpd mvpd, MVPDServicesFields field) {
        Boolean status = true;
        Utilities.logInfoMessage("Verification of " + field.getFieldName());
        if (field.equals(MVPDServicesFields.FIELD_MVPD_IDS)) {
            Utilities.logInfoMessage("Verification of main ID");
            if (!this.getMvpdIds().isEmpty()) {
                String expectedId = mvpd.getId();
                String actualId = this.getMvpdIds().get(0);
                status = verifyValues(expectedId, actualId);
                Utilities.logInfoMessage("Verification of alternative IDs");
                int expectedNumberOfLogos = mvpd.getAlternateMVPDIDs().size();
                int actualNumberOfLogos = this.getMvpdIds().size() - 1;
                if (expectedNumberOfLogos == actualNumberOfLogos) {
                    Utilities.logInfoMessage("Number of alternative IDs is correct");
                } else {
                    status = false;
                    Utilities.logSevereMessage("Number of alternative IDs is wrong. Expected: "
                            + expectedNumberOfLogos
                            + ", but found: "
                            + actualNumberOfLogos);
                }
                for (int i = 1; i < this.getMvpdIds().size(); i++) {
                    Boolean isCorrectAltId = false;
                    for (String id : mvpd.getAlternateMVPDIDs()) {
                        if (getMvpdIds().get(i).equals(id)) {
                            isCorrectAltId = true;
                            break;
                        }
                    }
                    if (isCorrectAltId) {
                        Utilities.logInfoMessage("Id " + getMvpdIds().get(i)
                                + " is correct");
                    } else {
                        Utilities.logSevereMessage("Id " + getMvpdIds().get(i)
                                + " is not found in expected ids");
                        status = false;
                    }
                }
            }
        } else {
            if (!field.equals(MVPDServicesFields.TITLE)) {
                try {
                    Field mvpdFromJsonField = MvpdFromService.class
                            .getDeclaredField(field.getJavaName());
                    mvpdFromJsonField.setAccessible(true);
                    Field mvpdField = Mvpd.class.getDeclaredField(field
                            .getJavaName());
                    mvpdField.setAccessible(true);
                    String expectedValue = null;
                    expectedValue = (String) mvpdField.get(mvpd);
                    if (expectedValue.equals("")) {
                        expectedValue = null;
                    }
                    String actualValue = (String) mvpdFromJsonField.get(this);
                    status = verifyValues(expectedValue, actualValue);
                } catch (NoSuchFieldException | SecurityException e) {
                    Utilities.logSevereMessage("Unable to getBrandName field"
                            + e.getMessage());
                } catch (IllegalArgumentException e) {
                    Utilities.logSevereMessage("Unable to getBrandName field"
                            + e.getMessage());
                } catch (IllegalAccessException e) {
                    Utilities.logSevereMessage("Unable to getBrandName field"
                            + e.getMessage());
                }
            } else {
                String expectedValue = mvpd.getDisplayName().equals("") ? null
                        : mvpd.getDisplayName();
                String actualValue = getTitle();
                status = verifyValues(expectedValue, actualValue);
            }
        }
        return status;
    }

    private Boolean verifyValues(String expectedValue, String actualValue) {
        Boolean status = true;
        if (expectedValue == null && actualValue == null) {
            Utilities.logInfoMessage("Validation of field passed!");
        } else if (expectedValue == null || actualValue == null) {
            Utilities.logSevereMessage("Error in field. Expected: " + expectedValue
                    + " but found " + actualValue);
            status = false;
        } else {
            if (expectedValue.equals(actualValue)) {
                Utilities.logInfoMessage("Validation of field passed!");
            } else {
                Utilities.logSevereMessage("Error in field. Expected: " + expectedValue
                        + " but found " + actualValue);
                status = false;
            }
        }
        return status;
    }

    @Override
    public String toString() {
        return "MvpdFromService [mvpdIds=" + mvpdIds + ", k2Id=" + k2Id
                + ", url=" + url + ", freewheelHash=" + freewheelHash
                + ", proxy=" + proxy + ", iosAppUrl=" + iosAppUrl
                + ", androidAppUrl=" + androidAppUrl + ", amazonAppUrl="
                + amazonAppUrl + ", usernamePwRecoveryUrl="
                + usernamePwRecoveryUrl + ", accountRegistrationUrl="
                + accountRegistrationUrl + ", contactPhoneNumber="
                + contactPhoneNumber + ", contactChatUrl=" + contactChatUrl
                + ", contactMailtoLink=" + contactMailtoLink
                + ", contactTwitterUrl=" + contactTwitterUrl
                + ", contactFaqLink=" + contactFaqLink + ", companyUrl="
                + companyUrl + ", forgotUsernameUrl=" + forgotUsernameUrl
                + ", title=" + title + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((accountRegistrationUrl == null) ? 0
                : accountRegistrationUrl.hashCode());
        result = prime * result
                + ((amazonAppUrl == null) ? 0 : amazonAppUrl.hashCode());
        result = prime * result
                + ((androidAppUrl == null) ? 0 : androidAppUrl.hashCode());
        result = prime * result
                + ((companyUrl == null) ? 0 : companyUrl.hashCode());
        result = prime * result
                + ((contactChatUrl == null) ? 0 : contactChatUrl.hashCode());
        result = prime * result
                + ((contactFaqLink == null) ? 0 : contactFaqLink.hashCode());
        result = prime
                * result
                + ((contactMailtoLink == null) ? 0 : contactMailtoLink
                .hashCode());
        result = prime
                * result
                + ((contactPhoneNumber == null) ? 0 : contactPhoneNumber
                .hashCode());
        result = prime
                * result
                + ((contactTwitterUrl == null) ? 0 : contactTwitterUrl
                .hashCode());
        result = prime
                * result
                + ((forgotUsernameUrl == null) ? 0 : forgotUsernameUrl
                .hashCode());
        result = prime * result
                + ((freewheelHash == null) ? 0 : freewheelHash.hashCode());
        result = prime * result
                + ((iosAppUrl == null) ? 0 : iosAppUrl.hashCode());
        result = prime * result + ((k2Id == null) ? 0 : k2Id.hashCode());
        result = prime * result + ((mvpdIds == null) ? 0 : mvpdIds.hashCode());
        result = prime * result + ((proxy == null) ? 0 : proxy.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime
                * result
                + ((usernamePwRecoveryUrl == null) ? 0 : usernamePwRecoveryUrl
                .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Utilities.logInfoMessage("Comporation of two mvpds from JSON MVPD service");
        Boolean status = true;
        if (this == obj) {
            Utilities.logInfoMessage("Validation passed.");
            return true;
        }
        if (obj == null) {
            Utilities.logSevereMessage("There is error in validation");
            return false;
        }
        if (getClass() != obj.getClass()) {
            Utilities.logSevereMessage("Wrong object is passed.");
            return false;
        }
        MvpdFromService other = (MvpdFromService) obj;
        if (accountRegistrationUrl == null) {
            if (other.accountRegistrationUrl != null) {
                status = false;
                Utilities.logSevereMessage("Error in accountRegistrationUrl. Expected: "
                        + other.getAccountRegistrationUrl() + " found: "
                        + this.getAccountRegistrationUrl());
            }
        } else if (!accountRegistrationUrl.equals(other.accountRegistrationUrl)) {
            Utilities.logSevereMessage("Error in accountRegistrationUrl. Expected: "
                    + other.getAccountRegistrationUrl() + " found: "
                    + this.getAccountRegistrationUrl());
            status = false;
        }
        if (companyUrl == null) {
            if (other.companyUrl != null) {
                status = false;
                Utilities.logSevereMessage("Error in companyUrl. Expected: "
                        + other.getCompanyUrl() + " found: "
                        + this.getCompanyUrl());
            }
        } else if (!companyUrl.equals(other.companyUrl)) {
            Utilities.logSevereMessage("Error in companyUrl. Expected: "
                    + other.getCompanyUrl() + " found: " + this.getCompanyUrl());
            status = false;
        }
        if (contactChatUrl == null) {
            if (other.contactChatUrl != null) {
                status = false;
                Utilities.logSevereMessage("Error in contactChatUrl. Expected: "
                        + other.getContactChatUrl() + " found: "
                        + this.getContactChatUrl());
            }
        } else if (!contactChatUrl.equals(other.contactChatUrl)) {
            status = false;
            Utilities.logSevereMessage("Error in contactChatUrl. Expected: "
                    + other.getContactChatUrl() + " found: "
                    + this.getContactChatUrl());
        }
        if (contactFaqLink == null) {
            if (other.contactFaqLink != null) {
                status = false;
                Utilities.logSevereMessage("Error in contactFaqLink. Expected: "
                        + other.getContactFaqLink() + " found: "
                        + this.getContactFaqLink());
            }
        } else if (!contactFaqLink.equals(other.contactFaqLink)) {
            status = false;
            Utilities.logSevereMessage("Error in contactFaqLink. Expected: "
                    + other.getContactFaqLink() + " found: "
                    + this.getContactFaqLink());
        }
        if (contactMailtoLink == null) {
            if (other.contactMailtoLink != null) {
                status = false;
                Utilities.logSevereMessage("Error in contactMailtoLink. Expected: "
                        + other.getContactMailtoLink() + " found: "
                        + this.getContactMailtoLink());
            }
        } else if (!contactMailtoLink.equals(other.contactMailtoLink)) {
            status = false;
            Utilities.logSevereMessage("Error in contactMailtoLink. Expected: "
                    + other.getContactMailtoLink() + " found: "
                    + this.getContactMailtoLink());
        }
        if (contactPhoneNumber == null) {
            if (other.contactPhoneNumber != null) {
                status = false;
                Utilities.logSevereMessage("Error in contactPhoneNumber. Expected: "
                        + other.getContactPhoneNumber() + " found: "
                        + this.getContactPhoneNumber());
            }
        } else if (!contactPhoneNumber.equals(other.contactPhoneNumber)) {
            status = false;
            Utilities.logSevereMessage("Error in contactPhoneNumber. Expected: "
                    + other.getContactPhoneNumber() + " found: "
                    + this.getContactPhoneNumber());
        }
        if (contactTwitterUrl == null) {
            if (other.contactTwitterUrl != null) {
                status = false;
                Utilities.logSevereMessage("Error in contactTwitterUrl. Expected: "
                        + other.getContactTwitterUrl() + " found: "
                        + this.getContactTwitterUrl());
            }
        } else if (!contactTwitterUrl.equals(other.contactTwitterUrl)) {
            status = false;
            Utilities.logSevereMessage("Error in contactTwitterUrl. Expected: "
                    + other.getContactTwitterUrl() + " found: "
                    + this.getContactTwitterUrl());
        }
        if (forgotUsernameUrl == null) {
            if (other.forgotUsernameUrl != null) {
                status = false;
                Utilities.logSevereMessage("Error in forgotUsernameUrl. Expected: "
                        + other.getForgotUserNameUrl() + " found: "
                        + this.getForgotUserNameUrl());
            }
        } else if (!forgotUsernameUrl.equals(other.forgotUsernameUrl)) {
            status = false;
            Utilities.logSevereMessage("Error in forgotUsernameUrl. Expected: "
                    + other.getForgotUserNameUrl() + " found: "
                    + this.getForgotUserNameUrl());
        }
        if (freewheelHash == null) {
            if (other.freewheelHash != null) {
                status = false;
                Utilities.logSevereMessage("Error in freewheelHash. Expected: "
                        + other.getFreewheelHash() + " found: "
                        + this.getFreewheelHash());
            }
        } else if (!freewheelHash.equals(other.freewheelHash)) {
            status = false;
            Utilities.logSevereMessage("Error in freewheelHash. Expected: "
                    + other.getFreewheelHash() + " found: "
                    + this.getFreewheelHash());
        }
        if (k2Id == null) {
            if (other.k2Id != null) {
                status = false;
                Utilities.logSevereMessage("Error in k2Id. Expected: " + other.getK2Id()
                        + " found: " + this.getK2Id());
            }
        } else if (!k2Id.equals(other.k2Id)) {
            status = false;
            Utilities.logSevereMessage("Error in k2Id. Expected: " + other.getK2Id()
                    + " found: " + this.getK2Id());
        }
        if (mvpdIds == null) {
            if (other.mvpdIds != null) {
                status = false;
                Utilities.logSevereMessage("Error in mvpdIds. Expected: "
                        + other.getMvpdIds() + " found: " + this.getMvpdIds());
            }
        } else if (!mvpdIds.equals(other.mvpdIds)) {
            status = false;
            Utilities.logSevereMessage("Error in mvpdIds. Expected: " + other.getMvpdIds()
                    + " found: " + this.getMvpdIds());
        }
        if (proxy == null) {
            if (other.proxy != null) {
                status = false;
                Utilities.logSevereMessage("Error in proxy. Expected: " + other.getProxy()
                        + " found: " + this.getProxy());
            }
        } else if (!proxy.equals(other.proxy)) {
            status = false;
            Utilities.logSevereMessage("Error in proxy. Expected: " + other.getProxy()
                    + " found: " + this.getProxy());
        }
        if (title == null) {
            if (other.title != null) {
                status = false;
                Utilities.logSevereMessage("Error in title. Expected: " + other.getTitle()
                        + " found: " + this.getTitle());
            }
        } else if (!title.equals(other.title)) {
            status = false;
            Utilities.logSevereMessage("Error in title. Expected: " + other.getTitle()
                    + " found: " + this.getTitle());
        }
        if (url == null) {
            if (other.url != null) {
                status = false;
                Utilities.logSevereMessage("Error in url. Expected: " + other.getMvpdUrl()
                        + " found: " + this.getMvpdUrl());
            }
        } else if (!url.equals(other.url)) {
            status = false;
            Utilities.logSevereMessage("Error in url. Expected: " + other.getMvpdUrl()
                    + " found: " + this.getMvpdUrl());
        }
        if (usernamePwRecoveryUrl == null) {
            if (other.usernamePwRecoveryUrl != null) {
                status = false;
                Utilities.logSevereMessage("Error in usernamePwRecoveryUrl. Expected: "
                        + other.getPwRecoveryUrl() + " found: "
                        + this.getPwRecoveryUrl());
            }
        } else if (!usernamePwRecoveryUrl.equals(other.usernamePwRecoveryUrl)) {
            status = false;
            Utilities.logSevereMessage("Error in usernamePwRecoveryUrl. Expected: "
                    + other.getPwRecoveryUrl() + " found: "
                    + this.getPwRecoveryUrl());
        }
        return status;
    }

}
