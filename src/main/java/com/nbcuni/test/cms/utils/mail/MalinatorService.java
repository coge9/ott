package com.nbcuni.test.cms.utils.mail;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.nbcuni.test.annotation.api.API;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MalinatorService {

    private String token;
    private String email;
    private String host;
    private List<MalinatorEmail> emails;
    public MalinatorService() {
        this.email = Config.getInstance().getMalinatorDefaultAddress();
        this.token = Config.getInstance().getMalinatorDefaultToken();
        host = Config.getInstance().getMalinatorHost();
    }

    MalinatorService(String email, String token) {
        this();
        this.email = email;
        this.token = token;
    }

    public void updateMails() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(host);
        urlBuilder.append("api/inbox?to=");
        urlBuilder.append(SimpleUtils.encodeStringToHTML(email));
        urlBuilder.append("&token=");
        urlBuilder.append(token);
        JsonElement inbox = JsonParserHelper.getInstance().getJson(
                urlBuilder.toString());
        JsonArray emailsArray = inbox.getAsJsonObject().get("messages")
                .getAsJsonArray();
        emails = JsonParserHelper.getInstance().getListOfJavaObjectsFromJson(
                emailsArray, MalinatorEmail.class);
        Collections.sort(emails, new MailComporator());
    }

    public List<MalinatorEmail> getEmails(Boolean... makeRequestToServer) {
        if (emails == null) {
            updateMails();
        } else {
            if (makeRequestToServer != null) {
                if (makeRequestToServer[0] == true) {
                    updateMails();
                }
            }
        }
        return emails;
    }

    public MalinatorEmail getRecentEmailBySubject(String subject) {
        getEmails(true);
        for (MalinatorEmail email : emails) {
            if (email.getSubject().equals(subject)) {
                return email;
            }
        }
        return null;
    }

    public MalinatorEmail getRecentEmailByFrom(String from) {
        getEmails();
        for (MalinatorEmail email : emails) {
            if (email.getFrom().equals(from)) {
                return email;
            }
        }
        return null;
    }

    public String getBodyOfEmail(String id) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(host);
        urlBuilder.append("api/email?id=");
        urlBuilder.append(id);
        urlBuilder.append("&token=");
        urlBuilder.append(token);
        String response = null;
        URL urlParam;
        try {
            urlParam = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) urlParam.openConnection();
            InputStream stream = conn.getInputStream();
            response = IOUtils.toString(stream, "UTF-8");
        } catch (Exception e) {
            Utilities.logSevereMessage("Error during getting code " + Utilities.convertStackTraceToString(e));

        }
        return response;
    }

    public void deleteEmail(String emailId) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(host);
        urlBuilder.append("api/expunge?msgid=");
        urlBuilder.append(emailId);
        try {
            new API().getRestRequest(urlBuilder.toString());
        } catch (Exception e) {
            Utilities.logSevereMessage("There is error during email deliting");
        }
    }

    public void deleteAllEmails() {
        List<MalinatorEmail> emails = this.getEmails(true);
        for (MalinatorEmail email : emails) {
            deleteEmail(email.getId());
        }
    }

    private class MailComporator implements Comparator<MalinatorEmail> {

        @Override
        public int compare(MalinatorEmail o1, MalinatorEmail o2) {
            if (o1.getTime() - o2.getTime() > 0) {
                return -1;
            } else if (o1.getTime() - o2.getTime() < 0) {
                return 1;
            } else {
                return 0;
            }
        }

    }
}
