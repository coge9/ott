package com.nbcuni.test.cms.utils.thumbnails.imagestyles;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Ivan_Karnilau on 4/14/2017.
 */
public class IOSStylesMatcher implements StylesMatcher {

    private Set<ImageStyles> getImageStyles() {
        return Sets.newHashSet(Arrays.asList(
                ImageStyles.landscape_widescreen_size1024_x1,
                ImageStyles.landscape_widescreen_size350_x2,
                ImageStyles.landscape_widescreen_size640_x2));
    }

    @Override
    public Set<ImageStyles> getImageStyles(String brand) {
        return this.getImageStyles();
    }
}
