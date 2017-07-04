package com.nbcuni.test.cms.pageobjectutils.tvecms.brands;

import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivan_Karnilau on 3/31/2017.
 */
public interface PlatformMatcher {
    List<CmsPlatforms> getPlatforms();

    // Methods will return all Concerto related platforms for the specific Matcher.
    default List<CmsPlatforms> getConcertoPlatforms() {
        List<CmsPlatforms> allConcertoPlatforms = CmsPlatforms.getConcertoPlatforms();
        return getPlatforms().stream().filter(item -> allConcertoPlatforms.contains(item)).collect(Collectors.toList());
    }

    // Method will return one random concerto Platform from existed in specific matcher.
    default CmsPlatforms getRandomConcertoPlatform() {
        List<CmsPlatforms> concertoPlatforms = getConcertoPlatforms();
        Collections.shuffle(concertoPlatforms);
        return concertoPlatforms.get(0);
    }
}
