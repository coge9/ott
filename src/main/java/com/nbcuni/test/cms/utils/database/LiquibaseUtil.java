package com.nbcuni.test.cms.utils.database;

import com.nbcuni.test.webdriver.Utilities;
import liquibase.Liquibase;
import liquibase.database.jvm.HsqlConnection;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;

import java.sql.Connection;

/**
 * Util class for initializing Test DB with values from predefined storage
 */
public class LiquibaseUtil {
    /**
     * Imports change set from data storage
     *
     * @param storage
     * @param context
     */
    private static void importDataFromStorage(final String storage, final String context, boolean drop) {
        Utilities.logInfoMessage("Import test data from storage: '" + storage + "', context: " + context + "'");
        final Connection connection = HSqlTestDataService.getInstance().getConnection();
        try {
            final ResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
            final HsqlConnection hsconn = new HsqlConnection(connection);
            final Liquibase liquibase = new Liquibase(storage, resourceAccessor, hsconn);
            if (drop) {
                liquibase.dropAll();
            }
            liquibase.update(context);
            hsconn.close();
        } catch (final Exception e) {
            Utilities.logSevereMessageThenFail("Error during database initialization");
        }
    }

    public static void importDataFromStorageWithDrop(final String storage, final String context) {
        importDataFromStorage(storage, context, true);
    }

    public static void importDataFromStorageWithOutDrop(final String storage, final String context) {
        importDataFromStorage(storage, context, false);
    }

}