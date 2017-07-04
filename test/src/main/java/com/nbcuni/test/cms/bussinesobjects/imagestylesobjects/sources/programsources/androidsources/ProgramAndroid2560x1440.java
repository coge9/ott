package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.androidsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramAndroid2560x1440 implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList(program_landscape_1280_435, program_landscape_590_331, program_landscape_535_300, program_landscape_640_360,
            program_portrait_720_406, program_landscape_554_312, program_landscape_439_247, program_portrait_540_304, program_landscape_494_278, program_landscape_390_220,
            program_landscape_702_394, program_landscape_1920_912, program_landscape_323_182);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }
}
