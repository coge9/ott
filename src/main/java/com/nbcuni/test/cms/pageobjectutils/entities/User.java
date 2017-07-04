package com.nbcuni.test.cms.pageobjectutils.entities;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.webdriver.Utilities;

public class User extends AbstractEntity {


    private static String email;
    private static String password = null;
    private static String username;

    public User(final UserType type) {
        final Config config = Config.getInstance();
        switch (type) {
            case MVPD: {
                email = config.getEnvDependantProperty("mvpdAdmin.user.login");
                username = config.getEnvDependantProperty("mvpdAdmin.user.name");
                password = config.getEnvDependantProperty("mvpdAdmin.user.password");
                break;
            }
            case MVPD_PERMISSIONS: {
                email = config.getEnvDependantProperty("mvpdAdmin.permission.user.login");
                username = config.getEnvDependantProperty("mvpdAdmin.permission.user.name");
                password = config.getEnvDependantProperty("mvpdAdmin.permission.user.password");
                break;
            }
            case INVALID: {
                email = config.getEnvDependantProperty("mvpdAdmin.invalid.user.login");
                username = config.getEnvDependantProperty("mvpdAdmin.invalid.user.name");
                password = config.getEnvDependantProperty("mvpdAdmin.invalid.user.password");
                break;
            }
            case MVPD_GMAIL: {
                email = config.getEnvDependantProperty("mvpdAdmin.aqauser.login");
                username = config.getEnvDependantProperty("mvpdAdmin.aqauser.name");
                password = config.getEnvDependantProperty("mvpdAdmin.aqauser.password");
                break;
            }
            default: {
                Utilities.logSevereMessage("Unknown type of user: " + type.name());
                break;
            }
        }
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }
}