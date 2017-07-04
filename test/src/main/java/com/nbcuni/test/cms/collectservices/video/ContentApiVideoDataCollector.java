package com.nbcuni.test.cms.collectservices.video;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;

/**
 * Created by Dzianis_Kulesh on 4/18/2017.
 * <p>
 * Implementation of interface which collect info about video node from Content API. Collect data for all brands both chiller and NON-chiller.
 */
public class ContentApiVideoDataCollector implements VideoDataCollector {

    private String brand;

    public ContentApiVideoDataCollector(String brand) {
        this.brand = brand;
    }

    /**
     * *********************************************************************************
     * Method Name: collectVideoInfo
     * Description: method for collecting video DATA in current implementation from Content API.
     *
     * @param assetTitle - title of the video under test.
     * @return GlobalVideoEntity
     * ***********************************************************************************
     */
    @Override
    public GlobalVideoEntity collectVideoInfo(String assetTitle) {
        NodeApi nodeApi = new NodeApi(brand);
        GlobalNodeJson videoNode = nodeApi.getVideoNodeByName(assetTitle);
        return VideoJsonTransformer.getGlobalVideoEntity(videoNode, brand);
    }

    @Override
    public GlobalVideoEntity collectRandomVideoInfo() {
        NodeApi nodeApi = new NodeApi(brand);
        GlobalNodeJson videoNode = nodeApi.getRandomVideoNode();
        return VideoJsonTransformer.getGlobalVideoEntity(videoNode, brand);
    }
}
