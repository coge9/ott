package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;

/**
 * Created by aleksandra_lishaeva on 9/25/15.
 */
public class RokuShelfProperties {

    @SerializedName("showrowlabel")
    private Boolean showRowLabel;

    public Boolean getShowRowLabel() {
        return showRowLabel;
    }

    public void setShowRowLabel(Boolean showRowLabel) {
        this.showRowLabel = showRowLabel;
    }


    public boolean verify(RokuShelfJson other) {
        Boolean status = true;
        Utilities.logInfoMessage("Validation of pages");
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Utilities.logInfoMessage("Validation of the field " + field.getAnnotation(SerializedName.class).value());
            try {
                Object expected = field.get(other);
                Object actual = field.get(this);
                if (expected == null || actual == null) {
                    if (actual != null || expected != null) {
                        Utilities.logSevereMessage("Field is wrong. Expected: " + expected + ", but found: " + actual);
                        status = false;
                    } else {
                        Utilities.logInfoMessage("OK!");
                    }
                } else if (!actual.equals(expected)) {
                    Utilities.logSevereMessage("Field is wrong. Expected: " + expected + ", but found: " + actual);
                    status = false;
                } else {
                    Utilities.logInfoMessage("OK!");
                }
            } catch (IllegalArgumentException e) {
                Utilities.logSevereMessage("Error during verification " + Utilities.convertStackTraceToString(e));
            } catch (IllegalAccessException e) {
                Utilities.logSevereMessage("Error during verification " + Utilities.convertStackTraceToString(e));
            }
        }
        if (status) {
            Utilities.logInfoMessage("Validation passed");
        } else {
            Utilities.logSevereMessage("Validation failed");
        }
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RokuShelfProperties other = (RokuShelfProperties) obj;
        if (showRowLabel == null) {
            if (other.showRowLabel != null) {
                return false;
            }
        } else if (!showRowLabel == (other.showRowLabel)) {
            return false;
        }
        return true;
    }

}
