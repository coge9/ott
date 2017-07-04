package com.nbcuni.test.cms.tests.backend.concerto.chiller.migration.users;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.people.PeopleEditRolePage;
import com.nbcuni.test.cms.backend.tvecms.pages.people.PeoplePage;
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
public class TC14574_UserMigration extends BaseAuthFlowTest {

    @Test(groups = "user_migration", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkUserThatMigrated(String brand) {
        List<User> users = EntityMySQLFactory.getInstance(MySqlTestDataService.getInstance("chiller"))
                .getUsers();
        Utilities.logInfoMessage("Users size is: " + users.size());
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //step 2
        PeoplePage peoplePage = mainRokuAdminPage.openPage(PeoplePage.class, brand);

        for (User user : users) {

            //Step 3
            if (peoplePage.isUserPresent(user.getUsername())) {
                PeopleEditRolePage editRolePage = peoplePage.openEditUserRolePage(user.getUsername());
                User userActual = editRolePage.getUserObject();
                editRolePage.clickSave();
                softAssert.assertTrue(user.verifyObject(userActual), "The user data is not matched", "User data is matched");
            } else {
                softAssert.assertFalse(true, "The User: " + user.getUsername() + "is not present at the content");
            }
        }
        softAssert.assertAll();
        Utilities.logInfoMessage("test is passed");

    }
}
