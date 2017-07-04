package com.nbcuni.test.cms.utils.jsonparsing.contentapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.PlatformNodeJson;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by Dzianis Kulesh on 3/23/2017.
 */
public class PlatformApi extends ContentApi {

    /**
     * @author Dzianis Kulesh
     * <p/>
     * This class is represent Service for working Platform Content API
     */


    private static final String PLATFORM_URL_PART = "&entityType=platform";
    private List<PlatformNodeJson> platformsList;

    public PlatformApi(String brand) {
        super(brand);
        baseUrl = baseUrl + PLATFORM_URL_PART;
    }

    /**
     * Allow to obtain platform from Content API by name(title)
     *
     * @param name - platform name (String)
     * @return - PlatformNodeJson represent platform data
     */

    public PlatformNodeJson getPlatformByName(String name) {
        for (PlatformNodeJson platform : getPlatforms()) {
            if (platform.getName().equalsIgnoreCase(name)) {
                return platform;
            }
        }
        return null;
    }

    /**
     * Allow to obtain platform from Content API by machine name
     *
     * @param machineName - platform machine name (String)
     * @return - PlatformNodeJson represent platform data
     */

    public PlatformNodeJson getPlatformByMachineName(String machineName) {
        for (PlatformNodeJson platform : getPlatforms()) {
            if (platform.getMachineName().equalsIgnoreCase(machineName)) {
                return platform;
            }
        }
        return null;
    }

    /*
    * This method allow to Identify roku platform setting. Either roku sould be published to
    *  concerto or serial API.
    *
    * */
    public boolean isRokuPublishedToConcerto() {
        PlatformNodeJson platform = getPlatformByName(CmsPlatforms.ROKU.getAppName());
        APIType instance = platform.getServiceInstance();
        if (APIType.AMAZON.equals(instance)) {
            return true;
        }
        return false;
    }

    /**
     * Methods do obtaining data from Content API and parse of all platforms.
     * This parsed platforms are stored to class variable. So one parsed they will be cached and subsequent
     * calls will not perform parsing.
     *
     * @return list of platforms as PlatformNodeJson object.
     */

    public List<PlatformNodeJson> getPlatforms() {
        if (platformsList == null) {
            platformsList = new ArrayList<PlatformNodeJson>();
            JsonElement globalElement = JsonParserHelper.getInstance().getJson(baseUrl);
            JsonObject platforms = globalElement.getAsJsonObject().get("output").getAsJsonObject().get("TveAqaApiGetPlatform").getAsJsonObject();
            for (Map.Entry<String, JsonElement> platformEntry : platforms.entrySet()) {
                PlatformNodeJson platform = JsonParserHelper.getInstance().getJavaObjectFromJson(platformEntry.getValue(), PlatformNodeJson.class);
                platformsList.add(platform);
            }
        }
        return platformsList;
    }

    public List<CmsPlatforms> getCmsPlatforms() {
        Set<CmsPlatforms> toReturn = new TreeSet<CmsPlatforms>();
        this.getPlatforms().forEach(item ->
                CollectionUtils.addIgnoreNull(toReturn, CmsPlatforms.getPlatformByMachineNameAndApiType(item.getMachineName(), item.getServiceInstance())));
        return new ArrayList<CmsPlatforms>(toReturn);
    }

    /**
     * Method identify is passed platform configured on CMS
     *
     * @param platform - platform to verify
     * @return - boolean value if platform is configured.
     */
    public boolean isPlatformConfigured(CmsPlatforms platform) {
        return getCmsPlatforms().contains(platform);
    }

}
