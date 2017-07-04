package com.nbcuni.test.cms.utils.jsonparsing.contentapi;

import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.CommonJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.PlatformNodeJson;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.webdriver.Utilities;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Aliaksei_Dzmitrenka on 2/21/2017.
 */
public class NodeApi extends ContentApi {

    /**
     * @author Aliaksei Dzmitrenka
     * <p/>
     * This class is represent Service for working Node Content API
     */


    private static final String NODE_URL_PART = "&entityType=node";
    private static final String VIDEO_TYPE = "ott_video";
    private static final String PROGRAM_TYPE = "ott_program";
    private static final String PROMO_TYPE = "ott_promo";

    public NodeApi(String brand) {
        super(brand);
        baseUrl = baseUrl + NODE_URL_PART;
    }


    /**
     * Allow to obtain random video data from Content API (as JSON)
     *
     * @return - GlobalNodeJson represent Video data
     */
    public GlobalNodeJson getRandomVideoNode() {
        return getNodeById(getAllVideos().getRandomNode());
    }

    /**
     * Allow to obtain random program data from Content API (as JSON)
     *
     * @return - GlobalNodeJson represent Program data
     */

    public GlobalNodeJson getRandomProgramNode() {
        return getNodeById(getAllPrograms().getRandomNode());
    }

    /**
     * Allow to obtain random published video data from Content API (as JSON)
     *
     * @return - GlobalNodeJson represent Video data
     */
    public GlobalNodeJson getRandomPublishedVideoNode() {
        return getNodeById(getAllPublishedVideos().getRandomNode());
    }

    /**
     * Allow to obtain first published Full Episode video data from Content API (as JSON)
     *
     * @return - GlobalNodeJson represent Video data of Full Episode
     */
    public GlobalNodeJson getFullEpisodeVideoNode() {
        Map<String, String> nodes = getAllPublishedVideos().getEntity();
        Map.Entry<String, String> fullEpisodes = nodes.entrySet().stream().
                filter(item -> getNodeById(item.getKey()).getMpxAsset().getFullEpisode()).
                findFirst().orElse(null);
        if (fullEpisodes != null) {
            return this.getNodeById(fullEpisodes.getKey());
        }
        throw new TestRuntimeException("Impossible to find Full Episodic Published video");
    }

    /**
     * Allow to obtain random published program data from Content API (as JSON)
     *
     * @return - GlobalNodeJson represent Program data
     */

    public GlobalNodeJson getRandomPublishedProgramNode() {
        return getNodeById(getAllPublishedPrograms().getRandomNode());
    }

    /**
     * Allow to verify is program present
     *
     * @param name - prgoram name (String)
     * @return - boolean value which represent is program exist
     */

    public boolean isProgramExist(String name) {
        return getAllPrograms().isEntityExist(name);
    }

    /**
     * Allow to obtain node object from Content API by name
     *
     * @param name - node name (String)
     * @return - GlobalNodeJson represent node data
     */

    public GlobalNodeJson getNodeByName(String name) {
        String nodeId = getNodeId(name);
        return getNodeById(nodeId);
    }

    /**
     * Allow to obtain video node object from Content API by video name
     *
     * @param name - video node name (String)
     * @return - GlobalNodeJson represent video node data
     */

    public GlobalNodeJson getVideoNodeByName(String name) {
        String nodeId = getAllVideos().getEntityId(name);
        return getNodeById(nodeId);
    }

    /**
     * Allow to obtain program node object from Content API by program name
     *
     * @param name - program node name (String)
     * @return - GlobalNodeJson represent program node data
     */

    public GlobalNodeJson getProgramNodeByName(String name) {
        String nodeId = getAllPrograms().getEntityId(name);
        return getNodeById(nodeId);
    }

    /**
     * Allow to obtain promo node object from Content API by promo name
     *
     * @param name - promo node name (String)
     * @return - GlobalNodeJson represent promo node data
     */

    public GlobalNodeJson getPromoNodeByName(String name) {
        String nodeId = getAllPromos().getEntityId(name);
        return getNodeById(nodeId);
    }

    /**
     * Allow to obtain node object from Content API by id
     *
     * @param id - node id (String)
     * @return - GlobalNodeJson represent node data
     */

    public GlobalNodeJson getNodeById(String id) {
        PlatformApi platformApi = new PlatformApi(brand);
        List<PlatformNodeJson> platforms = platformApi.getPlatforms();
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        GlobalNodeJson globalNode = jsonParserHelper.getJavaObjectFromJson(jsonParserHelper.getJson(addId(baseUrl, id)), GlobalNodeJson.class);
        if (globalNode.getType().contains(ContentType.TVE_PROGRAM.get().toLowerCase())) {
            globalNode.setImageSources(globalNode.getImageSources().stream().filter(getImageSourceFilteringCriteria(platforms)).collect(Collectors.toList()));
        }
        return globalNode;
    }

    /**
     * Allow to obtain node object from Content API by id
     *
     * @param platforms - platforms existed in the system.
     * @return - Predicate<ImageSource> filtering criteria for the image sources.
     */
    private Predicate<ImageSource> getImageSourceFilteringCriteria(List<PlatformNodeJson> platforms) {
        return item -> {
            for (PlatformNodeJson platform : platforms) {
                if (platform.getName().toLowerCase().contains(item.getPlatform().getAppName().toLowerCase())) {
                    if (platform.getName().contains(CmsPlatforms.ROKU.getAppName())) {
                        return platform.getServiceInstance().equals(APIType.AMAZON) && item.getPlatform().equals(CmsPlatforms.ROKU_SQS) ||
                                platform.getServiceInstance().equals(APIType.API_SERVICE) && item.getPlatform().equals(CmsPlatforms.ROKU);
                    }
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * Allow to obtain node id from Content API by name
     *
     * @param name - node name (String)
     * @return - String represent node id
     */

    private String getNodeId(String name) {
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        CommonJson commonJson = jsonParserHelper.getJavaObjectFromJson(jsonParserHelper.getJson(baseUrl), CommonJson.class);
        return commonJson.getEntityId(name);
    }

    private String addId(String baseUrl, String id) {
        return baseUrl + "&nid=" + id;
    }

    private String addType(String baseUrl, String type) {
        return baseUrl + "&type=" + type;
    }

    private String addPublished(PublishState state) {
        return "&status=" + state.getNodeApiValue();
    }


    // method allow to get CommonJson containing only program nodes
    private CommonJson getAllPrograms() {
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        return jsonParserHelper.getJavaObjectFromJson(jsonParserHelper.getJson(addType(baseUrl, PROGRAM_TYPE)), CommonJson.class);
    }

    // method allow to get CommonJson containing only video nodes
    private CommonJson getAllVideos() {
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        return jsonParserHelper.getJavaObjectFromJson(jsonParserHelper.getJson(addType(baseUrl, VIDEO_TYPE)), CommonJson.class);
    }

    // method allow to get CommonJson containing only published program nodes
    public CommonJson getAllPublishedPrograms() {
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        return jsonParserHelper.getJavaObjectFromJson(jsonParserHelper.getJson(addType(baseUrl, PROGRAM_TYPE) + addPublished(PublishState.PUBLISHED)), CommonJson.class);
    }

    // method allow to get CommonJson containing only published video nodes
    private CommonJson getAllPublishedVideos() {
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        return jsonParserHelper.getJavaObjectFromJson(jsonParserHelper.getJson(addType(baseUrl, VIDEO_TYPE) + addPublished(PublishState.PUBLISHED)), CommonJson.class);
    }

    // method allow to get CommonJson containing only promo nodes
    private CommonJson getAllPromos() {
        JsonParserHelper jsonParserHelper = JsonParserHelper.getInstance();
        return jsonParserHelper.getJavaObjectFromJson(jsonParserHelper.getJson(addType(baseUrl, PROMO_TYPE)), CommonJson.class);
    }

    public List<GlobalProgramEntity> getPrograms() {
        List<GlobalProgramEntity> programs = new LinkedList<>();
        Set<Map.Entry<String, String>> programSet = getAllPublishedPrograms().getEntity().entrySet();
        ExecutorService d = Executors.newFixedThreadPool(20);
        programSet.parallelStream().forEach(entry -> d.submit((Runnable) () ->
                programs.add(ProgramJsonTransformer.getGlobalProgramEntity(getNodeById(entry.getKey()), brand))));
        d.shutdown();
        try {
            d.awaitTermination(15, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Utilities.logSevereMessageThenFail("There is an error during getting programs.");
        }
        return programs;
    }

    public List<GlobalVideoEntity> getVideos() {
        List<GlobalVideoEntity> videos = new LinkedList<>();
        Set<Map.Entry<String, String>> videoSet = getAllPublishedVideos().getEntity().entrySet();
        ExecutorService d = Executors.newFixedThreadPool(20);
        videoSet.parallelStream().forEach(entry -> d.submit((Runnable) () ->
                videos.add(VideoJsonTransformer.getGlobalVideoEntity(getNodeById(entry.getKey()), brand))));
        d.shutdown();
        try {
            d.awaitTermination(15, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Utilities.logSevereMessageThenFail("There is an error during getting videos.");
        }
        return videos;
    }
}
