package com.nbcuni.test.cms.utils;

import com.dropbox.core.*;
import com.nbcuni.test.webdriver.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DropBoxUtil {
    public static final String ACCESS_TOKEN = Config.getInstance()
            .getDropBoxAccessToken();
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "YYYY-dd-MM");
    public static Date date = Calendar.getInstance().getTime();


    public static void auth() throws IOException, DbxException {
        // Get your app key and secret from the Dropbox developers website.

        final String APP_KEY = "INSERT_APP_KEY";
        final String APP_SECRET = "INSERT_APP_SECRET";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
                Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out
                .println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        //String code = "";
        //System.out.println(code);
        //DbxAuthFinish authFinish = webAuth.finish(code);
        //String accessToken = authFinish.accessToken;
        //System.out.println(accessToken);

    }

    public static synchronized String uploadFile(final File inputFile, final String fileName) {
        String sharableUrl = null;
        if (dateFormat.format(date).equals(
                dateFormat.format(Calendar.getInstance().getTime()))) {
            date = Calendar.getInstance().getTime();
        }
        DbxRequestConfig config = new DbxRequestConfig("", Locale.getDefault()
                .toString());
        DbxClient client = new DbxClient(config, ACCESS_TOKEN);
        try {
            client.createFolder("/" + dateFormat.format(date));
        } catch (DbxException e1) {
            Utilities.logSevereMessage("Error during creating folder in dropbox");
        }
        String folderPath = "/" + dateFormat.format(date) + "/";

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(inputFile);
            DbxEntry uploadedFile = client.uploadFile(folderPath + fileName, DbxWriteMode.add(),
                    inputFile.length(), inputStream);
            sharableUrl = client.createShareableUrl(uploadedFile.path);
            sharableUrl = sharableUrl.replace("www", "dl");
        } catch (FileNotFoundException e) {
            Utilities.logSevereMessage("Error during uploading file to Dropbox. File not found");
        } catch (DbxException e) {
            Utilities.logSevereMessage("Error during uploading file to Dropbox. Server exception"
                    + e.getMessage());
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during uploading file to Dropbox. Input/Output exception"
                    + e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Utilities.logSevereMessageThenFail("Unable to close stream ");
            }
        }
        return sharableUrl;
    }
}
