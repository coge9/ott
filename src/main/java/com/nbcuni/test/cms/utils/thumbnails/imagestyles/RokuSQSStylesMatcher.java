package com.nbcuni.test.cms.utils.thumbnails.imagestyles;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Ivan_Karnilau on 4/14/2017.
 */
public class RokuSQSStylesMatcher implements StylesMatcher {

    private Set<ImageStyles> getImageStyles() {
        Set<ImageStyles> styles = Sets.newHashSet(Arrays.asList(
                ImageStyles.landscape_1290x608_x1,
                ImageStyles.landscape_340x191_x1,
                ImageStyles.landscape_1429x608_x1, ImageStyles.logo_source));
        return styles;
    }

    @Override
    public Set<ImageStyles> getImageStyles(String brand) {
        return this.getImageStyles();
    }
}
