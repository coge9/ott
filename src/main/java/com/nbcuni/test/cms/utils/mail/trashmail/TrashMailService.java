package com.nbcuni.test.cms.utils.mail.trashmail;

import com.google.gson.JsonElement;
import com.nbcuni.test.annotation.api.API;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.Freemarker;
import com.nbcuni.test.lib.ApiUtils;
import com.nbcuni.test.webdriver.Utilities;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class TrashMailService {

    private static final String serviceEndPoint = Config.getInstance().getTrashmailApiEndpoint();
    private static final String LOGIN_URL = serviceEndPoint + "&cmd=login&fe-login-user=%s&fe-login-pass=%s";
    private static final String CREATE_URL = serviceEndPoint + "&cmd=create_dea&session_id=%s";
    private static final String DELETE_URL = serviceEndPoint + "&cmd=destroy_dea&session_id=%s";
    private static final String DELETE_BODY_FORMAT = "{\"data\":\"%s\"}";
    private static final String CREATE_TEMPLATE = "TrashmailCreate.ftl";
    private static API api = new API();

    public static String createDisposableEmail(String disposableEmail,
                                               String destinationEmail) {
        String emailId = null;
        String sessionId = login();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", disposableEmail.split("@")[0]);
        parameters.put("domain", disposableEmail.split("@")[1]);
        parameters.put("destination", destinationEmail);
        parameters.put("forwards", 10);
        parameters.put("expireDays", 30);
        String requestBody = Freemarker.getStringFromTemplate(CREATE_TEMPLATE, parameters);
        try {
            HttpURLConnection connection = ApiUtils.createConnection(String.format(CREATE_URL, sessionId));
            connection.setRequestMethod(api.REQUEST_POST);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", api.REQUEST_CONTENTTYPE_JSON);
            JsonElement response = api.postRestRequest(connection, requestBody);
            JsonElement element = response.getAsJsonObject().get("data").getAsJsonArray().get(0);
            if (element.isJsonObject()) {
                emailId = element.getAsJsonObject().get("id").getAsString();
            } else {
                Utilities.logSevereMessage("Error during email creation " + response.getAsJsonObject().get("data").getAsJsonArray().get(0).getAsString());
            }
        } catch (Exception e) {
            Utilities.logSevereMessage("Failed during creating disposable email " + Utilities.convertStackTraceToString(e));
        }
        return emailId;
    }

    public static Boolean deleteDisposableEmail(String id) {
        Boolean status = false;
        String sessionId = login();
        try {
            HttpURLConnection connection = ApiUtils.createConnection(String.format(DELETE_BODY_FORMAT, id));
            connection.setRequestMethod(api.REQUEST_POST);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", api.REQUEST_CONTENTTYPE_JSON);
            JsonElement response = api.postRestRequest(connection, String.format(DELETE_BODY_FORMAT, id));
            Boolean isDeleted = response.getAsJsonObject().get("success").getAsBoolean();
            Utilities.logSevereMessage(response.toString());
            if (isDeleted) {
                status = true;
            }
        } catch (Exception e) {
            Utilities.logSevereMessage("Failed during creating disposable email " + Utilities.convertStackTraceToString(e));
        }
        return status;
    }

    private static String login() {
        disableCertificateCheck();
        String name = Config.getInstance().getTrashmailUser();
        String password = Config.getInstance().getTrashmailPassword();
        String url = String.format(LOGIN_URL, name, password);
        String sessionId = null;
        try {
            JsonElement response = api.getRestRequest(url);
            sessionId = response.getAsJsonObject().get("msg").getAsJsonObject().get("session_id").getAsString();
        } catch (Exception e) {
            Utilities.logSevereMessage("Failed during login to trashmail " + Utilities.convertStackTraceToString(e));
        }
        return sessionId;
    }

    private static void disableCertificateCheck() {
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllX509TrustManager()}, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            Utilities.logSevereMessage("Error during disabling security check " + Utilities.convertStackTraceToString(e));
        }
    }
}
