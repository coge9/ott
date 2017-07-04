package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Tag;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Ivan_Karnilau on 12-May-16.
 */
public class FilesMetadataCreationStrategy {

    private FilesMetadataCreationStrategy(){
        super();
    }

    public static FilesMetadata createFilesMetadata() {
        FilesMetadata filesMetadata = new FilesMetadata();
        Associations associations = new Associations();
        associations
                .addTag(new Tag(SimpleUtils.getRandomString(6)))
                .addCategories(Category.BACKSTAGE.getCategory());
        filesMetadata
                .setImageGeneralInfo(ImageGeneralInfoCreationStrategy.createFilesMetadata());
        filesMetadata.setAssociations(associations);

        return filesMetadata;
    }
}
