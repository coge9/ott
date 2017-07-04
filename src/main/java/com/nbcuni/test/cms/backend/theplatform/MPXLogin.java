package com.nbcuni.test.cms.backend.theplatform;

import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SikuliUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.sikuli.script.Screen;

/*********************************************
 * publisher.nbcuni.com MPXLogin Library. Copyright
 *
 * @author Brandon Clark
 * @version 1.0 Date: January 16, 2014
 *********************************************/

public class MPXLogin {
    private final SikuliUtil util;
    private final String imagePath;


    public MPXLogin(final Config config) {
        util = new SikuliUtil(config, new Screen());
        imagePath = config.getPathToImages();
    }

    public void login(final String username, final String password) {
        util.waitForImgPresent(imagePath + "Login/SignIn_Ctr.png");
        util.waitForImgPresent(imagePath + "Login/UserName_Txb.png");

        Utilities.logInfoMessage("Enter '" + username + "' in the 'Username' text box");
        util.type(imagePath + "Login/UserName_Txb.png", username);

        Utilities.logInfoMessage("Enter '" + password + "' in the 'Password' text box");
        util.type(imagePath + "Login/PassWord_Txb.png", password);

        Utilities.logInfoMessage("Click the 'Login' button");
        util.click(imagePath + "Login/ss.png");//"Login/SignIn_Btn.png");
        util.waitForAllAssetsToLoad();
    }

    public void signOut() {
        Utilities.logInfoMessage("Click the 'Sign Out' button");
        util.click(imagePath + "Login/SignOut_Btn.png");
        util.waitForImgPresent(imagePath + "Login/SignIn_Ctr.png");
        util.click(imagePath + "Login/SignIn_Ctr.png");
    }
}
