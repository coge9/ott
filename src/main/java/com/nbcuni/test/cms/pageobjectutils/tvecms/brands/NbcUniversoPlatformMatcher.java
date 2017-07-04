package com.nbcuni.test.cms.pageobjectutils.tvecms.brands;

import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ivan_Karnilau on 3/31/2017.
 */
public class NbcUniversoPlatformMatcher implements PlatformMatcher {
    @Override
    public List<CmsPlatforms> getPlatforms() {
        return Arrays.asList(
                CmsPlatforms.ANDROID,
                CmsPlatforms.IOS
        );
    }
}
