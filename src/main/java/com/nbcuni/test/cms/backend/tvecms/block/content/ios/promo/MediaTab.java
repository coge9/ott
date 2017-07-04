package com.nbcuni.test.cms.backend.tvecms.block.content.ios.promo;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.cms.utils.thumbnails.ios.promo.IosPromoThumbnails;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan_Karnilau on 8/30/2016.
 */
public class MediaTab extends AbstractContainer {

    @FindBy(id = "edit-field-promo-media")
    private VideoContentImage media;

    public void uploadImage(String path) {
        media.uploadImage(path);
    }

    public MediaImage getImage() {
        return media.getImage();
    }

    public List<MediaImage> getPromoThumbnails() {
        Map<ThumbnailsCutInterface, String> promoThumbnails = media.getThumbnails(IosPromoThumbnails.getAllThumbnails());
        List<MediaImage> imageStyles = new ArrayList<>();
        for (Map.Entry<ThumbnailsCutInterface, String> promoThumbnail : promoThumbnails.entrySet()) {
            MediaImage mediaImage = new MediaImage();
            String href = promoThumbnail.getValue();
            if (href.contains("?itok=")) {
                href = href.substring(0, href.indexOf("?itok="));
            }
            mediaImage
                    .setUrl(href)
                    .setName(promoThumbnail.getKey().getImageName())
                    .setWidth(promoThumbnail.getKey().getRequiredAppWidth())
                    .setHeight(promoThumbnail.getKey().getRequiredAppHeight());
            imageStyles.add(mediaImage);
        }
        return imageStyles;
    }

    public VideoContentImage getMedia() {
        return media;
    }
}
