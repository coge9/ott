package com.nbcuni.test.cms.utils.thumbnails.imagestyles;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;

import java.util.Set;

/**
 * Created by Ivan_Karnilau on 4/14/2017.
 */
public interface StylesMatcher {
    Set<ImageStyles> getImageStyles(String brand);
}
