package com.nbcuni.test.cms.tests.backend.concerto.chiller.migration.users;

import com.nbcuni.test.cms.backend.chiller.pages.migration.ImportPage;
import com.nbcuni.test.cms.backend.chiller.pages.migration.MigrationPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.User;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.MySqlTestDataService;
import com.nbcuni.test.cms.utils.database.mysql.EntityMySQLFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 5/16/16.
 */
public class TC14574_RoleMigration extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     Make sure modules for Migrate UI are switched on
     'Migrate UI'
     "TVE Migration"
     Steps:

     1.Go To CMS as admin
     Verify: The Admin Panel is present

     2.Go To Content->Migrate page
     Verify: There is a list of migrated groups

     3.Go To users group
     Verify: There is list of item to migrate
     Users
     Role

     4.Check Role and perform Import
     Verify: The migration is done without errore

     5.Go To People Page of CMS /admin/people
     Verify: There is a list of users existed on the CMS

     6.Make query tho the chiller prod database's snapshot and compare result with users that are migrated
     SELECT r.*
     FROM
     roles r
     WHERE (r.id > '2')
     Verify: The number of roles are matched
     * */

    @Test(groups = "user_migration", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testUserMigrationCount(String brand) {
        List<User> users = EntityMySQLFactory.getInstance(MySqlTestDataService.getInstance("chiller"))
                .getUsers();
        Utilities.logInfoMessage("Users size is: " + users.size());
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //step 2
        ImportPage importPage = rokuBackEndLayer.performMigration(MigrationPage.Groups.USER, ImportPage.Task.ALL);

        //Step 3
        int actualRoles = importPage.getImportResults(ImportPage.Task.ROLE);
        softAssert.assertFalse(importPage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);
        softAssert.assertEquals(users.size(), actualRoles, "The Roles number is not matched with imported", "The Roles count to migration is matched", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("test is passed");

    }
}
