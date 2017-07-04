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
public class XboxOneStylesMatcher implements StylesMatcher {

    private Set<ImageStyles> getImageStyles() {
        Set<ImageStyles> styles = Sets.newHashSet(Arrays.asList(
                ImageStyles.landscape_1367x770_x1,
                ImageStyles.landscape_517x292_x1,
                ImageStyles.landscape_460x256_x1,
                ImageStyles.landscape_1920x1080_x1));
        return styles;
    }

    @Override
    public Set<ImageStyles> getImageStyles(String brand) {
        switch (RokuBrandNames.getBrandByName(brand)) {
            case BRAVO: {
            }
            case SYFY: {
                return this.getImageStyles();
            }
            default: {
                return new HashSet<>();
            }
        }
    }
}
