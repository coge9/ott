package com.nbcuni.test.cms.utils.database.mysql;

import com.nbcuni.test.cms.utils.Config;

/**
 * Created by Aleksandra_Lishaeva on 5/6/16.
 */
public class SshEntity {

    public static final String LOCAL_HOST = "127.0.0.1";
    private String sshUser;
    private String sshPassword;
    private String sshHost;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;
    private int databasePort;
    private int localPort;

    public int getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public String getSshUser() {
        return sshUser;
    }

    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public SshEntity getChillerDevDatabse() {
        Config config = Config.getInstance();
        this.setSshHost(config.getAcquiaSSHost());
        this.setSshPassword(config.getAcquiaSSHPassword());
        this.setSshUser(config.getAcquiaSSHUser());
        this.setDatabaseName(config.getChillerDatabaseName());
        this.setDatabasePassword(config.getChillerDatabasePassword());
        this.setDatabaseUser(config.getChillerDatabaseUser());
        this.setDatabasePort(Integer.parseInt(config.getChillerDatabasePort()));
        this.setLocalPort(Integer.parseInt(config.getLocalFreePort()));
        return this;
    }
}
