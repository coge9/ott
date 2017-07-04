package com.nbcuni.test.cms.utils.thumbnails.imagestyles;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Ivan_Karnilau on 4/14/2017.
 */
public class UniversalSearchStylesMatcher implements StylesMatcher {

    private Set<ImageStyles> getImageStyles() {
        Set<ImageStyles> styles = Sets.newHashSet(Arrays.asList(
                ImageStyles.video_source,
                ImageStyles.three_tile_program_one,
                ImageStyles.program_source_withlogo));
        return styles;
    }

    @Override
    public Set<ImageStyles> getImageStyles(String brand) {
        return this.getImageStyles();
    }
}
