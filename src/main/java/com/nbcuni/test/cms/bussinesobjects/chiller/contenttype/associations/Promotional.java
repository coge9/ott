package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.google.common.base.Objects;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;

public class Promotional {

    private String promotionalKicker;
    private String promotionalTitle;
    private String promotionalDescription;
    private MediaImage mediaImage = new MediaImage();

    public String getPromotionalKicker() {
        return promotionalKicker;
    }

    public void setPromotionalKicker(String promotionalKicker) {
        this.promotionalKicker = promotionalKicker;
    }

    public String getPromotionalTitle() {
        return promotionalTitle;
    }

    public void setPromotionalTitle(String promotionalTitle) {
        this.promotionalTitle = promotionalTitle;
    }

    public String getPromotionalDescription() {
        return promotionalDescription;
    }

    public void setPromotionalDescription(String promotionalDescription) {
        this.promotionalDescription = promotionalDescription;
    }

    public MediaImage getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(MediaImage mediaImage) {
        this.mediaImage = mediaImage;
    }

    public boolean isObjectNull() {
        return promotionalKicker == null && promotionalTitle == null && promotionalDescription == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotional other = (Promotional) o;
        if (promotionalKicker == null) {
            if (other.promotionalKicker != null) {
                return false;
            }
        } else if (!promotionalKicker.equals(other.promotionalKicker)) {
            return false;
        }

        if (promotionalTitle == null) {
            if (other.promotionalTitle != null) {
                return false;
            }
        } else if (!promotionalTitle.equals(other.promotionalTitle)) {
            return false;
        }

        if (promotionalDescription == null) {
            if (other.promotionalDescription != null) {
                return false;
            }
        } else if (!promotionalDescription.equals(other.promotionalDescription)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(promotionalKicker, promotionalTitle, promotionalDescription, mediaImage);
    }
}
