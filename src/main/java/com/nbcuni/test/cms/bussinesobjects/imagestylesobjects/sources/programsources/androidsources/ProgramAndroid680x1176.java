package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.androidsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.program_landscape_390_462;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramAndroid680x1176 implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList(program_landscape_390_462);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }
}
