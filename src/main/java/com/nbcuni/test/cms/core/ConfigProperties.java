package com.nbcuni.test.cms.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The Class ConfigProperties.
 */
@Component
@PropertySource(value = "classpath:config_tve_cms.properties")
public class ConfigProperties {

    /** The url. */
    @Value("${${env}.Url}")
    private String url;

    /** The user. */
    @Value("${${env}.User}")
    private String user;

    /** The password. */
    @Value("${${env}.Pass}")
    private String password;

    /** The db host. */
    @Value("${${env}.DB.Host}")
    private String dbHost;

    /** The db port. */
    @Value("${${env}.DB.Port}")
    private String dbPort;

    /** The db name. */
    @Value("${${env}.DB.Name}")
    private String dbName;

    /** The db user. */
    @Value("${${env}.DB.User}")
    private String dbUser;

    /** The db password. */
    @Value("${${env}.DB.Password}")
    private String dbPassword;

    /** The Apiary URL. */
    @Value("${Apiary.Url}")
    private String apiaryURL;

    /**Proxy Host**/
    @Value("${Proxy.Host}")
    private String proxyHost;

    @Value("${Proxy.Port}")
    private String proxyPort;


    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the db host.
     *
     * @return the db host
     */
    public String getDbHost() {
        return dbHost;
    }

    /**
     * Gets the db port.
     *
     * @return the db port
     */
    public String getDbPort() {
        return dbPort;
    }

    /**
     * Gets the db name.
     *
     * @return the db name
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Gets the db user.
     *
     * @return the db user
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * Gets the db password.
     *
     * @return the db password
     */
    public String getDbPassword() {
        return dbPassword;
    }

    public String getApiaryURL() {
        return apiaryURL;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    /**
     * Test.
     */

    @PostConstruct
    public void test() {
        System.out.println("APIARY url = " + apiaryURL);
        System.out.println("Testing url = " + url);
        System.out.println("proxy host = " + proxyHost);
        System.out.println("proxy port = " + proxyPort);
        System.out.println("user = " + user);
        System.out.println("pass = " + password);
        System.out.println("dbHost = " + dbHost);
        System.out.println("dbPort = " + dbPort);
        System.out.println("dbName = " + dbName);
        System.out.println("dbUser = " + dbUser);
        System.out.println("dbPassword = " + dbPassword);
    }

}
