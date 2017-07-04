package com.nbcuni.test.cms.utils.jsonparsing.services.registryservice;

import com.google.gson.JsonElement;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.Freemarker;
import com.nbcuni.test.cms.utils.httpclient.CustomHttpClient;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandra_Lishaeva on 5/19/16.
 */
public class RegistryServiceHelper {

    private CustomHttpClient httpClient = new CustomHttpClient();
    private InputStream stream = null;
    private Config config = Config.getInstance();
    private String brand;

    public RegistryServiceHelper(String brand) {
        this.brand = brand;
    }

    public JsonElement getJsonElement(String url, String body) {
        Utilities.logInfoMessage("Requesting JSON from " + url);
        Map<String, String> headers = new HashMap<>();
        headers.put("api_key", config.getRegistryServiceApiKey(Instance.STAGE));
        headers.put("accessKey", config.getRegistryServiceAccessKey(Instance.STAGE, brand));
        headers.put("Content-Type", "application/json");
        HttpResponse response = httpClient.httpPost(url, body, headers);
        try {
            stream = response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonElement mainJson = JsonParserHelper.getInstance().getJsonFromInputStream(stream);
        if (!mainJson.isJsonObject()) {
            Utilities.logSevereMessage("Response at URL: " + url + " is not JSON object");
            return null;
        }

        return mainJson;

    }

    public String getUuid(RegistryServiceEntity serviceBuilder) {
        JsonElement mainJson = getJsonElement(config.getRegistryServiceDomain(Instance.STAGE), bodyBuilder(serviceBuilder));
        JsonElement element = mainJson.getAsJsonObject();
        RegestryServiceResponse response = JsonParserHelper.getInstance()
                .getJavaObjectFromJson(element, RegestryServiceResponse.class);
        return response.getUuid();
    }

    //TODO: Update it to using freemarker

    private String bodyBuilder(RegistryServiceEntity serviceBuilder) {
        Map<String, Object> values = new HashMap<>();
        values.put("title", serviceBuilder.getName());
        values.put("contentType", serviceBuilder.getType());
        values.put("season", String.valueOf(serviceBuilder.getSeasonNumber()));
        values.put("episode", String.valueOf(serviceBuilder.getEpisodeNumber()));
        return Freemarker.getStringFromTemplate(Config.getInstance().getPathToFreemarkerTemplateUuid(serviceBuilder.getType()), values);
    }
}
