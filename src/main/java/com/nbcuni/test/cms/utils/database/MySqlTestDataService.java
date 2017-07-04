package com.nbcuni.test.cms.utils.database;

import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.database.mysql.SshEntity;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.Utilities;

import java.sql.Connection;


public class MySqlTestDataService extends DataBaseService implements IDataBaseService {
    private volatile static MySqlTestDataService instance;
    private static boolean isInitialized = false;
    private static String brand;

    private MySqlTestDataService() {
        loadJdbcDriver(Config.getInstance().getMySqlDbDriver());
    }

    public static MySqlTestDataService getInstance(String brand) {

        if (instance == null) {
            synchronized (MySqlTestDataService.class) {
                if (instance == null) {
                    instance = new MySqlTestDataService();
                    isInitialized = true;
                }
            }
        }
        MySqlTestDataService.brand = brand;
        return instance;
    }

    public static boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public Connection getConnection() {
        final Config config = Config.getInstance();

        return getConnection(
                config.getMySqlDbUrl() + config.getMySqlDbServer() + ":"
                        + config.getMySqlDbPort() + "/" + config.getMySqlDbName(brand),
                config.getMySqlDbUserName(), config.getMySqlDbPassword());


    }

    @Override
    public Connection getSShConnection() {
        final Config config = Config.getInstance();
        SshEntity sshEntity = new SshEntity().getChillerDevDatabse();
        doSshTunnel(sshEntity);
        try {
            return getConnection(
                    config.getMySqlDbUrl() + SshEntity.LOCAL_HOST + ":"
                            + sshEntity.getLocalPort() + "/" + sshEntity.getDatabaseName(),
                    sshEntity.getDatabaseUser(), sshEntity.getDatabasePassword());
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
        throw new TestRuntimeException("The Database Connection failed with error above");
    }

}