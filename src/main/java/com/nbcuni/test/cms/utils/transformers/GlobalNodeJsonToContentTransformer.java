package com.nbcuni.test.cms.utils.transformers;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;

/**
 * Created by aleksandra_lishaeva on 5/10/17.
 */
public class GlobalNodeJsonToContentTransformer {

    private GlobalNodeJson globalNodeJson = null;
    private String brand = null;
    private NodeApi nodeApi = null;

    /**
     * Get Content item, based on an asset title using NodeApi
     *
     * @param assetTitle - title of the asset each Content object you would like to get
     * @return - content entity object
     */
    public Content transformToContent(String assetTitle) {
        brand = System.getProperty("brand");
        nodeApi = new NodeApi(brand);
        Content item = null;
        globalNodeJson = nodeApi.getNodeByName(assetTitle);
        switch (globalNodeJson.getType()) {
            case "ott_program":
                item = ProgramJsonTransformer.getGlobalProgramEntity(globalNodeJson, brand);
                break;
            case "ott_video":
                item = VideoJsonTransformer.getGlobalVideoEntity(globalNodeJson, brand);
                break;
            case "ott_promo":
                item = PromoJsonTransformer.getGlobalPromoEntity(globalNodeJson, brand);
                break;
            default:
                throw new TestRuntimeException("There is no corresponding case operator for "+globalNodeJson.getType());
        }
        return item;
    }

    /**
     * Get Content item, based on a Global Json object using NodeApi
     *
     * @param nodeJson - object of Global Node Json
     * @return - content entity object
     */
    public Content transformToContent(GlobalNodeJson nodeJson) {
        brand = System.getProperty("brand");
        Content item = null;
        switch (globalNodeJson.getType()) {
            case "ott_program":
                item = ProgramJsonTransformer.getGlobalProgramEntity(globalNodeJson, brand);
                break;
            case "ott_video":
                item = VideoJsonTransformer.getGlobalVideoEntity(globalNodeJson, brand);
                break;
            case "ott_promo":
                item = ProgramJsonTransformer.getGlobalProgramEntity(globalNodeJson, brand);
                break;
            default:
                throw new TestRuntimeException("There is no corresponding case operator for "+globalNodeJson.getType());
        }
        return item;
    }
}
