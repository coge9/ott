package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.androidsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramLogo implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList(program_logo_400_117, program_logo_600_176, program_logo_800_234, program_logo_1200_351,
            program_logo_1600_468, program_logo_259_76, program_logo_198_58);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }
}
