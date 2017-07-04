package com.nbcuni.test.cms.utils.thumbnails.imagestyles;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Ivan_Karnilau on 4/14/2017.
 */
public class AppleTVStylesMatcher implements StylesMatcher {

    private Set<ImageStyles> getImageStyles() {
        return Sets.newHashSet(Arrays.asList(
                // Video styles
                ImageStyles.landscape_132x74_x1,
                ImageStyles.landscape_132x74_x1_5,
                ImageStyles.landscape_216x122_x1,
                ImageStyles.landscape_216x122_x1_5,
                ImageStyles.landscape_255x143_x1,
                ImageStyles.landscape_255x143_x1_5,
                ImageStyles.landscape_393x221_x1,
                ImageStyles.landscape_393x221_x1_5,
                ImageStyles.landscape_390x219_x1,
                ImageStyles.landscape_720x405_x1,

                //Program styles
                ImageStyles.landscape_127x71_x1,
                ImageStyles.landscape_127x71_x1_5,
                ImageStyles.landscape_255x143_x1,
                ImageStyles.landscape_255x143_x1_5,
                ImageStyles.square_600x600_x1,
                ImageStyles.landscape_771x292_x1,
                ImageStyles.landscape_1920x486_x1,
                ImageStyles.landscape_540x304_x1,
                ImageStyles.landscape_540x304_x1_5,
                ImageStyles.landscape_1704x440_x1,
                ImageStyles.landscape_1920x1080_x1));
    }

    @Override
    public Set<ImageStyles> getImageStyles(String brand) {
        return this.getImageStyles();
    }
}
