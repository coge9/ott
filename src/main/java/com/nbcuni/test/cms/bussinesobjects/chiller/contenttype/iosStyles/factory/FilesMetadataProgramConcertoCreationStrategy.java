package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.iosstyles.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.ImageGeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;
import com.nbcuni.test.cms.utils.thumbnails.appletv.program.AppleTVProgramThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.appletv.video.AppleTVVideoThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.cms.utils.thumbnails.ios.video.IosVideoThumbnails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 7/12/2016.
 */
@Component("imageStyles")
public class FilesMetadataProgramConcertoCreationStrategy {

    public FilesMetadata createFilesMetadataFor1600x900() {
        return getFilesMetadata(ProgramConcertoImageSourceType.SOURCE_1600x900);
    }

    public FilesMetadata createFilesMetadataFor1965x1108() {
        return getFilesMetadata(ProgramConcertoImageSourceType.SOURCE_1965x1108);
    }

    public FilesMetadata createFilesMetadataFor3Tile() {
        return getFilesMetadata(ProgramConcertoImageSourceType.SOURCE_3TILE);
    }

    private FilesMetadata getFilesMetadata(ProgramConcertoImageSourceType styleType) {
        FilesMetadata filesMetadata = new FilesMetadata();
        filesMetadata.setAssociations(new Associations());
        ImageGeneralInfo imageGeneralInfo = new ImageGeneralInfo();
        imageGeneralInfo.setHighResolution(false);
        imageGeneralInfo.setImageSourceType(styleType);
        filesMetadata.setImageGeneralInfo(imageGeneralInfo);
        return filesMetadata;
    }

    /**
     *
     * @param sources - types of source that should be published to Concerto API (e.g iOS 1600x900, roku 3tile)
     * @return - list of FilesMetadata object (item type,usage,source type)
     */
    public List<FilesMetadata> getListOfSourcesMetadaObject(ProgramConcertoImageSourceType... sources) {
        List<FilesMetadata> filesMetadataList = new ArrayList<>();
        for (ProgramConcertoImageSourceType type : sources) {
            filesMetadataList.add(getFilesMetadata(type));
        }
        return filesMetadataList;
    }

    public List<ThumbnailsCutInterface> getAllConcertoImageStyles() {
        List<ThumbnailsCutInterface> imageStyles = new LinkedList<>();
        imageStyles.addAll(AppleTVVideoThumbnails.getAllThumbnails());
        imageStyles.addAll(AppleTVProgramThumbnails.getAllThumbnails());
        imageStyles.addAll(IosVideoThumbnails.getAllThumbnails());
        return imageStyles;
    }

}
