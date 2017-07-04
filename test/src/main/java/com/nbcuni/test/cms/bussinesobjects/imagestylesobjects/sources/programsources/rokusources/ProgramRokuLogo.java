package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 4/12/2017.
 */
public class ProgramRokuLogo implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList();

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }


}
