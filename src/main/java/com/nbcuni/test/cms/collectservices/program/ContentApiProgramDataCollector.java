package com.nbcuni.test.cms.collectservices.program;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;

/**
 * Created by Dzianis_Kulesh on 4/18/2017.
 * <p>
 * Implementation of interface which collect info about program node from Content API. Collect data for all brands both chiller and NON-chiller.
 */
public class ContentApiProgramDataCollector implements ProgramDataCollector {

    private String brand;

    public ContentApiProgramDataCollector(String brand) {
        this.brand = brand;
    }

    /**
     * *********************************************************************************
     * Method Name: collectProgramInfo
     * Description: method for collecting program DATA in current implementation from Content API.
     *
     * @param assetTitle - title of the program under test.
     * @return GlobalProgramEntity
     * ***********************************************************************************
     */
    @Override
    public GlobalProgramEntity collectProgramInfo(String assetTitle) {
        NodeApi nodeApi = new NodeApi(brand);
        GlobalNodeJson programNode = nodeApi.getProgramNodeByName(assetTitle);
        return ProgramJsonTransformer.getGlobalProgramEntity(programNode, brand);
    }

    @Override
    public GlobalProgramEntity collectRandomProgramInfo() {
        NodeApi nodeApi = new NodeApi(brand);
        GlobalNodeJson programNode = nodeApi.getRandomProgramNode();
        return ProgramJsonTransformer.getGlobalProgramEntity(programNode, brand);
    }

}
