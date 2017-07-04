package com.nbcuni.test.cms.utils.database;

import com.nbcuni.test.cms.utils.Config;

import java.sql.Connection;

public class HSqlTestDataService extends DataBaseService implements IDataBaseService {
    private volatile static HSqlTestDataService instance;
    private static boolean isInitialized = false;
    private final Config config = Config.getInstance();

    private HSqlTestDataService() {
        loadJdbcDriver(config.getHSqlDbDriver());
    }

    public static HSqlTestDataService getInstance() {
        if (instance == null) {
            synchronized (HSqlTestDataService.class) {
                if (instance == null) {
                    instance = new HSqlTestDataService();
                    isInitialized = true;
                }
            }
        }
        return instance;
    }

    public static boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public Connection getConnection() {
        return getConnection(config.getHSqlDbUrl(), config.getHSqlDbUserName(), config.getHSqlDbPassword());
    }

    @Override
    public Connection getSShConnection() {
        return null;
    }
}