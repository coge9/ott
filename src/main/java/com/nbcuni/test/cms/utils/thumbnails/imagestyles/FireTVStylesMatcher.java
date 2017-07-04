package com.nbcuni.test.cms.utils.thumbnails.imagestyles;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan_Karnilau on 4/14/2017.
 */
public class FireTVStylesMatcher implements StylesMatcher {

    private Set<ImageStyles> getImageStyles() {
       return Sets.newHashSet(Arrays.asList(
                ImageStyles.landscape_1920x915_x1,
                ImageStyles.landscape_390x220_x1,
                ImageStyles.landscape_494x278_x1,
                ImageStyles.landscape_702x394_x1,
                ImageStyles.landscape_1284x1080_x1,
                ImageStyles.landscape_1920x912_x1,
                ImageStyles.landscape_390x462_x1,
                ImageStyles.logo_400x117,
                ImageStyles.logo_198x58_x1,
                ImageStyles.logo_259x76_x1,
                ImageStyles.logo_1600x468_x1,
                ImageStyles.logo_1200x351_x1,
                ImageStyles.logo_800x234_x1,
                ImageStyles.logo_600x176_x1));
    }

    public Set<ImageStyles> getImageStylesForCNBC() {
        Set<ImageStyles> styles = Sets.newHashSet(Arrays.asList(
                ImageStyles.landscape_384x216_x1,
                ImageStyles.landscape_432x243_x1,
                ImageStyles.landscape_1600x900_x1));
        return styles;
    }

    @Override
    public Set<ImageStyles> getImageStyles(String brand) {
        switch (RokuBrandNames.getBrandByName(brand)) {
            case CNBC: {
                return this.getImageStylesForCNBC();
            }
            case USA: {
            }
            case BRAVO: {
            }
            case SYFY: {
            }
            case EONLINE: {
            }
            case TELEMUNDO: {
                return this.getImageStyles();
            }
            default: {
                return new HashSet<>();
            }
        }
    }
}
