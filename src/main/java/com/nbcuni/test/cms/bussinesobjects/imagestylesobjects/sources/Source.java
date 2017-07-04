package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;

import java.util.List;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public interface Source {

    List<ImageStyles> getImageStyles();

    default String getConcertoUsage() {
        return null;
    }
}
