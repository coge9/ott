package com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities;

import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.MvpdErrorMessage;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdJsonAllAvailableFields;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.List;

public class MvpdFromJson {

    private String title;
    private String enableNewWindowWorkflow;
    private String displayName;
    private String freewheelHash;
    private String id;
    private String k2Id;
    private String mvpdLogoAlternative;
    private String logoDark;
    private String logoLight;
    private String url;
    private String darkColor;
    private String lightColor;
    private String featured;
    private String weight;
    private String whiteLogo2x;
    private String colorLogo2x;
    private String genericError;
    private String internalError;
    private String authorizedError;
    private String isDarkerLogo;

    // MOBILE
    private String pickerImage;
    private String pickerImage2x;
    private String loggedInImage;
    private String loggedInImage2x;
    private String phoneLoggedInImage;
    private String phoneLoggedInImage2x;
    private String adobePassEndPoint;

    // XBOXONE
    private String colorLogo;
    private String blackLogo;
    private String blackLogo2x;
    private String whiteLogo;

    // /////////// V2 FIELDS ////////////////////
    private String appPickerImage;
    private String appPickerImage2x;
    private String appLoggedInImage;
    private String appLoggedInImage2x;
    private String activationpickerImage;
    private String activationpickerImage2x;
    private String activationloggedInImage;
    private String activationloggedInImage2x;

    private String mvpdLogo;

    private List<MvpdErrorMessage> customErrorMessages;

    public MvpdFromJson() {
        super();
    }

    public String getMvpdName() {
        String mvpdName = title;
        if (mvpdName == null) {
            mvpdName = displayName;
        }
        return mvpdName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnableNewWindowWorkflow() {
        return enableNewWindowWorkflow;
    }

    public void setEnableNewWindowWorkflow(String enableNewWindowWorkflow) {
        this.enableNewWindowWorkflow = enableNewWindowWorkflow;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFreewheelHash() {
        return freewheelHash;
    }

    public void setFreewheelHash(String freewheelHash) {
        this.freewheelHash = freewheelHash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getK2Id() {
        return k2Id;
    }

    public void setK2Id(String k2Id) {
        this.k2Id = k2Id;
    }

    public String getMvpdLogoAlternative() {
        return mvpdLogoAlternative;
    }

    public void setMvpdLogoAlternative(String mvpdLogoAlternative) {
        this.mvpdLogoAlternative = mvpdLogoAlternative;
    }

    public String getLogoDark() {
        return logoDark;
    }

    public void setLogoDark(String logoDark) {
        this.logoDark = logoDark;
    }

    public String getLogoLight() {
        return logoLight;
    }

    public void setLogoLight(String logoLight) {
        this.logoLight = logoLight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDarkColor() {
        return darkColor;
    }

    public void setDarkColor(String darkColor) {
        this.darkColor = darkColor;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWhiteLogo2x() {
        return whiteLogo2x;
    }

    public void setWhiteLogo2x(String whiteLogo2x) {
        this.whiteLogo2x = whiteLogo2x;
    }

    public String getColorLogo2x() {
        return colorLogo2x;
    }

    public void setColorLogo2x(String colorLogo2x) {
        this.colorLogo2x = colorLogo2x;
    }

    public String getGenericError() {
        return genericError;
    }

    public void setGenericError(String genericError) {
        this.genericError = genericError;
    }

    public String getInternalError() {
        return internalError;
    }

    public void setInternalError(String internalError) {
        this.internalError = internalError;
    }

    public String getAuthorizedError() {
        return authorizedError;
    }

    public void setAuthorizedError(String authorizedError) {
        this.authorizedError = authorizedError;
    }

    public String getIsDarkerLogo() {
        return isDarkerLogo;
    }

    public void setIsDarkerLogo(String isDarkerLogo) {
        this.isDarkerLogo = isDarkerLogo;
    }

    public String getPickerImage() {
        return pickerImage;
    }

    public void setPickerImage(String pickerImage) {
        this.pickerImage = pickerImage;
    }

    public String getPickerImage2x() {
        return pickerImage2x;
    }

    public void setPickerImage2x(String pickerImage2x) {
        this.pickerImage2x = pickerImage2x;
    }

    public String getLoggedInImage() {
        return loggedInImage;
    }

    public void setLoggedInImage(String loggedInImage) {
        this.loggedInImage = loggedInImage;
    }

    public String getLoggedInImage2x() {
        return loggedInImage2x;
    }

    public void setLoggedInImage2x(String loggedInImage2x) {
        this.loggedInImage2x = loggedInImage2x;
    }

    public String getPhoneLoggedInImage() {
        return phoneLoggedInImage;
    }

    public void setPhoneLoggedInImage(String phoneLoggedInImage) {
        this.phoneLoggedInImage = phoneLoggedInImage;
    }

    public String getPhoneLoggedInImage2x() {
        return phoneLoggedInImage2x;
    }

    public void setPhoneLoggedInImage2x(String phoneLoggedInImage2x) {
        this.phoneLoggedInImage2x = phoneLoggedInImage2x;
    }

    public String getAdobePassEndPoint() {
        return adobePassEndPoint;
    }

    public void setAdobePassEndPoint(String adobePassEndPoint) {
        this.adobePassEndPoint = adobePassEndPoint;
    }

    public String getColorLogo() {
        return colorLogo;
    }

    public void setColorLogo(String colorLogo) {
        this.colorLogo = colorLogo;
    }

    public String getBlackLogo() {
        return blackLogo;
    }

    public void setBlackLogo(String blackLogo) {
        this.blackLogo = blackLogo;
    }

    public String getBlackLogo2x() {
        return blackLogo2x;
    }

    public void setBlackLogo2x(String blackLogo2x) {
        this.blackLogo2x = blackLogo2x;
    }

    public String getWhiteLogo() {
        return whiteLogo;
    }

    public void setWhiteLogo(String whiteLogo) {
        this.whiteLogo = whiteLogo;
    }

    public String getAppPickerImage() {
        return appPickerImage;
    }

    public void setAppPickerImage(String appPickerImage) {
        this.appPickerImage = appPickerImage;
    }

    public String getAppPickerImage2x() {
        return appPickerImage2x;
    }

    public void setAppPickerImage2x(String appPickerImage2x) {
        this.appPickerImage2x = appPickerImage2x;
    }

    public String getAppLoggedInImage() {
        return appLoggedInImage;
    }

    public void setAppLoggedInImage(String appLoggedInImage) {
        this.appLoggedInImage = appLoggedInImage;
    }

    public String getAppLoggedInImage2x() {
        return appLoggedInImage2x;
    }

    public void setAppLoggedInImage2x(String appLoggedInImage2x) {
        this.appLoggedInImage2x = appLoggedInImage2x;
    }

    public String getActivationpickerImage() {
        return activationpickerImage;
    }

    public void setActivationpickerImage(String activationpickerImage) {
        this.activationpickerImage = activationpickerImage;
    }

    public String getActivationpickerImage2x() {
        return activationpickerImage2x;
    }

    public void setActivationpickerImage2x(String activationpickerImage2x) {
        this.activationpickerImage2x = activationpickerImage2x;
    }

    public String getActivationloggedInImage() {
        return activationloggedInImage;
    }

    public void setActivationloggedInImage(String activationloggedInImage) {
        this.activationloggedInImage = activationloggedInImage;
    }

    public String getActivationloggedInImage2x() {
        return activationloggedInImage2x;
    }

    public void setActivationloggedInImage2x(String activationloggedInImage2x) {
        this.activationloggedInImage2x = activationloggedInImage2x;
    }

    public String getMvpdLogo() {
        return mvpdLogo;
    }

    public void setMvpdLogo(String mvpdLogo) {
        this.mvpdLogo = mvpdLogo;
    }

    public List<MvpdErrorMessage> getCustomErrorMessages() {
        return customErrorMessages;
    }

    public void setCustomErrorMessages(
            List<MvpdErrorMessage> customErrorMessages) {
        this.customErrorMessages = customErrorMessages;
    }

    public void addCustomErrorMessages(MvpdErrorMessage customError) {
        if (customErrorMessages != null) {
            customErrorMessages.add(customError);
        }
    }

    public String getFieldValue(MvpdJsonAllAvailableFields field) {
        String value;
        try {
            Field javaField = this.getClass().getDeclaredField(
                    field.getFieldMappingToJavaField());
            value = (String) javaField.get(this);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get field"
                    + field.getFieldMappingToJavaField()
                    + " from MvpdFromJson.class using reflection");
        }
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((activationloggedInImage == null) ? 0
                : activationloggedInImage.hashCode());
        result = prime
                * result
                + ((activationloggedInImage2x == null) ? 0
                : activationloggedInImage2x.hashCode());
        result = prime
                * result
                + ((activationpickerImage == null) ? 0 : activationpickerImage
                .hashCode());
        result = prime
                * result
                + ((activationpickerImage2x == null) ? 0
                : activationpickerImage2x.hashCode());
        result = prime
                * result
                + ((adobePassEndPoint == null) ? 0 : adobePassEndPoint
                .hashCode());
        result = prime
                * result
                + ((appLoggedInImage == null) ? 0 : appLoggedInImage.hashCode());
        result = prime
                * result
                + ((appLoggedInImage2x == null) ? 0 : appLoggedInImage2x
                .hashCode());
        result = prime * result
                + ((appPickerImage == null) ? 0 : appPickerImage.hashCode());
        result = prime
                * result
                + ((appPickerImage2x == null) ? 0 : appPickerImage2x.hashCode());
        result = prime * result
                + ((authorizedError == null) ? 0 : authorizedError.hashCode());
        result = prime * result
                + ((blackLogo == null) ? 0 : blackLogo.hashCode());
        result = prime * result
                + ((blackLogo2x == null) ? 0 : blackLogo2x.hashCode());
        result = prime * result
                + ((colorLogo == null) ? 0 : colorLogo.hashCode());
        result = prime * result
                + ((colorLogo2x == null) ? 0 : colorLogo2x.hashCode());
        result = prime
                * result
                + ((customErrorMessages == null) ? 0 : customErrorMessages
                .hashCode());
        result = prime * result
                + ((darkColor == null) ? 0 : darkColor.hashCode());
        result = prime * result
                + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime
                * result
                + ((enableNewWindowWorkflow == null) ? 0
                : enableNewWindowWorkflow.hashCode());
        result = prime * result
                + ((featured == null) ? 0 : featured.hashCode());
        result = prime * result
                + ((freewheelHash == null) ? 0 : freewheelHash.hashCode());
        result = prime * result
                + ((genericError == null) ? 0 : genericError.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((internalError == null) ? 0 : internalError.hashCode());
        result = prime * result
                + ((isDarkerLogo == null) ? 0 : isDarkerLogo.hashCode());
        result = prime * result + ((k2Id == null) ? 0 : k2Id.hashCode());
        result = prime * result
                + ((lightColor == null) ? 0 : lightColor.hashCode());
        result = prime * result
                + ((loggedInImage == null) ? 0 : loggedInImage.hashCode());
        result = prime * result
                + ((loggedInImage2x == null) ? 0 : loggedInImage2x.hashCode());
        result = prime * result
                + ((logoDark == null) ? 0 : logoDark.hashCode());
        result = prime * result
                + ((logoLight == null) ? 0 : logoLight.hashCode());
        result = prime
                * result
                + ((mvpdLogoAlternative == null) ? 0 : mvpdLogoAlternative
                .hashCode());
        result = prime
                * result
                + ((phoneLoggedInImage == null) ? 0 : phoneLoggedInImage
                .hashCode());
        result = prime
                * result
                + ((phoneLoggedInImage2x == null) ? 0 : phoneLoggedInImage2x
                .hashCode());
        result = prime * result
                + ((pickerImage == null) ? 0 : pickerImage.hashCode());
        result = prime * result
                + ((pickerImage2x == null) ? 0 : pickerImage2x.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((weight == null) ? 0 : weight.hashCode());
        result = prime * result
                + ((whiteLogo == null) ? 0 : whiteLogo.hashCode());
        result = prime * result
                + ((whiteLogo2x == null) ? 0 : whiteLogo2x.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MvpdFromJson other = (MvpdFromJson) obj;
        if (activationloggedInImage == null) {
            if (other.activationloggedInImage != null)
                return false;
        } else if (!activationloggedInImage
                .equals(other.activationloggedInImage))
            return false;
        if (activationloggedInImage2x == null) {
            if (other.activationloggedInImage2x != null)
                return false;
        } else if (!activationloggedInImage2x
                .equals(other.activationloggedInImage2x))
            return false;
        if (activationpickerImage == null) {
            if (other.activationpickerImage != null)
                return false;
        } else if (!activationpickerImage.equals(other.activationpickerImage))
            return false;
        if (activationpickerImage2x == null) {
            if (other.activationpickerImage2x != null)
                return false;
        } else if (!activationpickerImage2x
                .equals(other.activationpickerImage2x))
            return false;
        if (adobePassEndPoint == null) {
            if (other.adobePassEndPoint != null)
                return false;
        } else if (!adobePassEndPoint.equals(other.adobePassEndPoint))
            return false;
        if (appLoggedInImage == null) {
            if (other.appLoggedInImage != null)
                return false;
        } else if (!appLoggedInImage.equals(other.appLoggedInImage))
            return false;
        if (appLoggedInImage2x == null) {
            if (other.appLoggedInImage2x != null)
                return false;
        } else if (!appLoggedInImage2x.equals(other.appLoggedInImage2x))
            return false;
        if (appPickerImage == null) {
            if (other.appPickerImage != null)
                return false;
        } else if (!appPickerImage.equals(other.appPickerImage))
            return false;
        if (appPickerImage2x == null) {
            if (other.appPickerImage2x != null)
                return false;
        } else if (!appPickerImage2x.equals(other.appPickerImage2x))
            return false;
        if (authorizedError == null) {
            if (other.authorizedError != null)
                return false;
        } else if (!authorizedError.equals(other.authorizedError))
            return false;
        if (blackLogo == null) {
            if (other.blackLogo != null)
                return false;
        } else if (!blackLogo.equals(other.blackLogo))
            return false;
        if (blackLogo2x == null) {
            if (other.blackLogo2x != null)
                return false;
        } else if (!blackLogo2x.equals(other.blackLogo2x))
            return false;
        if (colorLogo == null) {
            if (other.colorLogo != null)
                return false;
        } else if (!colorLogo.equals(other.colorLogo))
            return false;
        if (colorLogo2x == null) {
            if (other.colorLogo2x != null)
                return false;
        } else if (!colorLogo2x.equals(other.colorLogo2x))
            return false;
        if (customErrorMessages == null) {
            if (other.customErrorMessages != null)
                return false;
        } else if (!customErrorMessages.equals(other.customErrorMessages))
            return false;
        if (darkColor == null) {
            if (other.darkColor != null)
                return false;
        } else if (!darkColor.equals(other.darkColor))
            return false;
        if (displayName == null) {
            if (other.displayName != null)
                return false;
        } else if (!displayName.equals(other.displayName))
            return false;
        if (enableNewWindowWorkflow == null) {
            if (other.enableNewWindowWorkflow != null)
                return false;
        } else if (!enableNewWindowWorkflow
                .equals(other.enableNewWindowWorkflow))
            return false;
        if (featured == null) {
            if (other.featured != null)
                return false;
        } else if (!featured.equals(other.featured))
            return false;
        if (freewheelHash == null) {
            if (other.freewheelHash != null)
                return false;
        } else if (!freewheelHash.equals(other.freewheelHash))
            return false;
        if (genericError == null) {
            if (other.genericError != null)
                return false;
        } else if (!genericError.equals(other.genericError))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (internalError == null) {
            if (other.internalError != null)
                return false;
        } else if (!internalError.equals(other.internalError))
            return false;
        if (isDarkerLogo == null) {
            if (other.isDarkerLogo != null)
                return false;
        } else if (!isDarkerLogo.equals(other.isDarkerLogo))
            return false;
        if (k2Id == null) {
            if (other.k2Id != null)
                return false;
        } else if (!k2Id.equals(other.k2Id))
            return false;
        if (lightColor == null) {
            if (other.lightColor != null)
                return false;
        } else if (!lightColor.equals(other.lightColor))
            return false;
        if (loggedInImage == null) {
            if (other.loggedInImage != null)
                return false;
        } else if (!loggedInImage.equals(other.loggedInImage))
            return false;
        if (loggedInImage2x == null) {
            if (other.loggedInImage2x != null)
                return false;
        } else if (!loggedInImage2x.equals(other.loggedInImage2x))
            return false;
        if (logoDark == null) {
            if (other.logoDark != null)
                return false;
        } else if (!logoDark.equals(other.logoDark))
            return false;
        if (logoLight == null) {
            if (other.logoLight != null)
                return false;
        } else if (!logoLight.equals(other.logoLight))
            return false;
        if (mvpdLogoAlternative == null) {
            if (other.mvpdLogoAlternative != null)
                return false;
        } else if (!mvpdLogoAlternative.equals(other.mvpdLogoAlternative))
            return false;
        if (phoneLoggedInImage == null) {
            if (other.phoneLoggedInImage != null)
                return false;
        } else if (!phoneLoggedInImage.equals(other.phoneLoggedInImage))
            return false;
        if (phoneLoggedInImage2x == null) {
            if (other.phoneLoggedInImage2x != null)
                return false;
        } else if (!phoneLoggedInImage2x.equals(other.phoneLoggedInImage2x))
            return false;
        if (pickerImage == null) {
            if (other.pickerImage != null)
                return false;
        } else if (!pickerImage.equals(other.pickerImage))
            return false;
        if (pickerImage2x == null) {
            if (other.pickerImage2x != null)
                return false;
        } else if (!pickerImage2x.equals(other.pickerImage2x))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (weight == null) {
            if (other.weight != null)
                return false;
        } else if (!weight.equals(other.weight))
            return false;
        if (whiteLogo == null) {
            if (other.whiteLogo != null)
                return false;
        } else if (!whiteLogo.equals(other.whiteLogo))
            return false;
        if (whiteLogo2x == null) {
            if (other.whiteLogo2x != null)
                return false;
        } else if (!whiteLogo2x.equals(other.whiteLogo2x))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MvpdFromJson [title=" + title + ", enableNewWindowWorkflow="
                + enableNewWindowWorkflow + ", displayName=" + displayName
                + ", freewheelHash=" + freewheelHash + ", id=" + id + ", k2Id="
                + k2Id + ", mvpdLogoAlternative=" + mvpdLogoAlternative
                + ", logoDark=" + logoDark + ", logoLight=" + logoLight
                + ", url=" + url + ", darkColor=" + darkColor + ", lightColor="
                + lightColor + ", featured=" + featured + ", weight=" + weight
                + ", whiteLogo2x=" + whiteLogo2x + ", colorLogo2x="
                + colorLogo2x + ", genericError=" + genericError
                + ", internalError=" + internalError + ", authorizedError="
                + authorizedError + ", isDarkerLogo=" + isDarkerLogo
                + ", pickerImage=" + pickerImage + ", pickerImage2x="
                + pickerImage2x + ", loggedInImage=" + loggedInImage
                + ", loggedInImage2x=" + loggedInImage2x
                + ", phoneLoggedInImage=" + phoneLoggedInImage
                + ", phoneLoggedInImage2x=" + phoneLoggedInImage2x
                + ", adobePassEndPoint=" + adobePassEndPoint + ", colorLogo="
                + colorLogo + ", blackLogo=" + blackLogo + ", blackLogo2x="
                + blackLogo2x + ", whiteLogo=" + whiteLogo
                + ", appPickerImage=" + appPickerImage + ", appPickerImage2x="
                + appPickerImage2x + ", appLoggedInImage=" + appLoggedInImage
                + ", appLoggedInImage2x=" + appLoggedInImage2x
                + ", activationpickerImage=" + activationpickerImage
                + ", activationpickerImage2x=" + activationpickerImage2x
                + ", activationloggedInImage=" + activationloggedInImage
                + ", activationloggedInImage2x=" + activationloggedInImage2x
                + ", customErrorMessages=" + customErrorMessages + "]";
    }

    public boolean verifyObject(MvpdFromJson mvpd) {
        if (this == mvpd) {
            return true;
        }
        if (mvpd == null) {
            Utilities.logSevereMessage("Null object was passed for validation");
            return false;
        }
        Boolean status = true;
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("customErrorMessages")) {
                continue;
            }
            try {
                field.setAccessible(true);
                String actValue = (String) field.get(this);
                String expValue = (String) field.get(mvpd);
                if (!(actValue == null && expValue == null)) {
                    if (actValue == null || expValue == null) {
                        Utilities.logSevereMessage("Field " + field.getName()
                                + " is differ: expected [" + expValue
                                + "], but found [" + actValue + "]");
                        status = false;
                    } else {
                        if (!actValue.equals(expValue)) {
                            Utilities.logSevereMessage("Field " + field.getName()
                                    + " is differ: expected [" + expValue
                                    + "], but found [" + actValue + "]");
                            status = false;
                        }
                    }
                }
            } catch (SecurityException e) {

                e.printStackTrace();
            } catch (IllegalArgumentException e) {

                e.printStackTrace();
            } catch (IllegalAccessException e) {

                e.printStackTrace();
            }
        }
        return status;
    }


    public boolean verifyObjectIncludingErrors(MvpdFromJson mvpd) {
        SoftAssert asertion = new SoftAssert();
        asertion.assertTrue(verifyObject(mvpd), "There are errors in custom fields");
        List<MvpdErrorMessage> expErrors = mvpd.getCustomErrorMessages();
        List<MvpdErrorMessage> actErrors = this.getCustomErrorMessages();
        asertion.assertEquals(expErrors, actErrors, "There are errors in 'error message section'");
        return asertion.isNotAnyError();
    }


}
