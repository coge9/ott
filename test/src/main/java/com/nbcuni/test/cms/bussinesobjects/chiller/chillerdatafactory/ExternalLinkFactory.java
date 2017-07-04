package com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.utils.SimpleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena_Aukhukova on 6/20/2016.
 */
public class ExternalLinkFactory {

    private ExternalLinkFactory(){
        super();
    }

    /**
     * Create external links with random data for 'link title' and 'link url'
     *
     * @param linksCount number of objects
     * @return list of ExternalLinksInfo entities
     */
    public static List<ExternalLinksInfo> createRandomExternalLinkWithCount(int linksCount) {
        List<ExternalLinksInfo> externalLinksInfoList = new ArrayList<ExternalLinksInfo>();
        for (int i = 0; i < linksCount; i++) {
            ExternalLinksInfo link = new ExternalLinksInfo();
            link.setExtrenalLinkTitle(SimpleUtils.getRandomString(6)).setExtrenalLinkUrl(SimpleUtils.getRandomString(6));
            externalLinksInfoList.add(link);
        }
        return externalLinksInfoList;
    }
}
