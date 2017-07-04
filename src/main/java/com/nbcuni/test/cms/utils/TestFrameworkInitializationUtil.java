package com.nbcuni.test.cms.utils;

import com.nbcuni.test.cms.utils.database.LiquibaseUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dzianis_Kulesh on 4/20/2017.
 * <p>
 * This class contains test methods required for framework initialization.
 */
public class TestFrameworkInitializationUtil {

    private static Config config = Config.getInstance();

    // Intitialize in-memory data basae. Used to store test information.
    public static void initDatabase() {
        // Init DATABASE
        config.setEnvironment(System.getProperty("env"));
        LiquibaseUtil.importDataFromStorageWithDrop(
                config.getPathToResouces() + config.getHSqlDbStorageFile(),
                config.getHSqlDbName());
        LiquibaseUtil.importDataFromStorageWithOutDrop(
                config.getPathToResouces() + "/ots_roku_aqa_db.xml",
                config.getHSqlDbName());
    }

    // Create temp folder if not exist.
    public static void setTempFolder() {
        final File tempDir = new File(config.getPathToTempFiles());
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }

    // Create file for build report. Use in email notification for local jenkins.
    public static void createBuildInfoFile() {
        File buildInfoFile = new File("BuildInfo.html");
        buildInfoFile.delete();
        String info = "";
        String buildNumber;
        Map<String, Object> parameters = new HashMap<String, Object>();
        buildNumber = getCmsBuild();
        parameters.put("project", "TVE CMS");
        parameters.put("build", buildNumber);
        info = Freemarker.getStringFromTemplate("reportTemplate" + File.separator + "BuildLine.ftl", parameters);
        parameters.put("info", info);
        FileUtil.writeToFile(buildInfoFile, Freemarker.getStringFromTemplate(
                "reportTemplate" + File.separator + "EmailTemplate.ftl", parameters), true);
    }

    // Get build number curently deployed to environment.
    private static String getCmsBuild() {
        String rokuUrl = Config.getInstance().getRokuHomePage(System.getProperty("brand"));
        String rokuPropertyName = "TVE Core: build tag";
        String userName = Config.getInstance().getRokuUserLogin();
        String userPassword = Config.getInstance().getRokuUserPassword();
        String rokuBuild = getBuildNumber(rokuUrl, userName, userPassword,
                rokuPropertyName);
        if (rokuBuild != null) {
            Utilities.logSevereMessage("TVE CMS BUILD: " + rokuBuild);

        } else {
            rokuBuild = "undefined";
        }
        return rokuBuild;
    }

    // Take build number from CMS Admin
    private static String getBuildNumber(String url, String name, String password,
                                         String propertyName) {
        if (url == null) {
            return null;
        }
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        String loginUrl = url + "user";
        String reportUrl = url + "admin/reports/status";
        try {
            HttpClient httpClient = new HttpClient();

            PostMethod postMethod = new PostMethod(loginUrl);
            postMethod.addRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            postMethod.addRequestHeader("Cookie",
                    "third_party_cookie_enabled=true; has_js=1");
            postMethod.setRequestBody("name=" + name + "&pass=" + password
                    + "&form_build_id=&form_id=user_login&op=Log+in");
            httpClient.executeMethod(postMethod);
            postMethod.getResponseBodyAsString();
            Header cookieHeader = postMethod.getResponseHeader("Set-Cookie");
            GetMethod getMethod = new GetMethod(reportUrl);
            getMethod.addRequestHeader("Cookie", cookieHeader.getValue());
            httpClient.executeMethod(getMethod);
            String resp = getMethod.getResponseBodyAsString();
            int start = resp.indexOf(propertyName
                    + "</td><td class=\"status-value\">");
            if (start == -1) {
                return null;
            }
            int end = resp.indexOf("</td></tr>", start);
            String buildNumber = resp.substring(start + propertyName.length()
                    + 30, end);
            return buildNumber;
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("Error during get buildNumber");
        }
        return null;
    }


}
