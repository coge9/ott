package com.nbcuni.test.cms.bussinesobjects.abstractentity;

import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;

/**
 * Created by alekca on 29.05.2016.
 */
public class AbstractEntity implements EntityInterface {

    public static final String CUSTOM_ERROR_MESSAGES = "customErrorMessages";

    @Override
    public boolean verifyObject(Object expected) {
        if (this == expected) {
            return true;
        }
        if (expected == null) {
            Utilities.logSevereMessageThenFail("Null object was passed for validation");
            return false;
        }
        Boolean status = true;
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(CUSTOM_ERROR_MESSAGES)) {
                continue;
            }
            status = verifySingleField(expected, field) && status;
        }
        return status;
    }

    private Boolean verifySingleField(Object expected, Field field) {
        Boolean status = false;
        try {
            field.setAccessible(true);
            Object actValue = field.get(this);
            Object expValue = field.get(expected);
            status = validation(field, actValue, expValue);
        } catch (SecurityException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        } catch (IllegalArgumentException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        } catch (IllegalAccessException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        return status;
    }

    private Boolean validation(Field field, Object actValue, Object expValue) {
        if (!(actValue == null && expValue == null)) {
            if (actValue == null || expValue == null) {
                Utilities.logSevereMessage("Field " + field.getName()
                        + " is differ: expected [" + expValue
                        + "], but found [" + actValue + "]");
               return false;
            } else {
                if (!actValue.equals(expValue)) {
                    Utilities.logSevereMessage("Field " + field.getName()
                            + " is differ: expected [" + expValue
                            + "], but found [" + actValue + "]");
                    return false;
                }
            }
        }
        return true;
    }
}
