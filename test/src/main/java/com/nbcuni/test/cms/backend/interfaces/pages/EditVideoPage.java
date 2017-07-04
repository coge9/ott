package com.nbcuni.test.cms.backend.interfaces.pages;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;

import java.util.List;

/**
 * Created by Dzianis_Kulesh on 2/7/2017.
 * <p>
 * Interface is design to represent page for collecting video data.
 * It is implemented by any video edit page (e.g. for Chiller brand (we have specific page)).
 */
public interface EditVideoPage {


    String getTitle();

    // collecting MPX info
    MpxAsset getMpxInfo();

    // collecting Associtations
    Associations getAssociations();

    // collecting Promotional
    Promotional getPromotional();

    // collecting Slug info
    Slug getSlugInfo();

    // collecting image sources
    List<ImageSource> getImageSources(String brand);

    // opening devel page
    DevelPage openDevelPage();

}
