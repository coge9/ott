package com.nbcuni.test.cms.utils.mpx.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MpxThumbnail {

    private String title;
    private String guid;
    private String id;
    private String ownerId;

    @SerializedName("plfile$height")
    private Integer height;

    @SerializedName("plfile$width")
    private Integer width;

    @SerializedName("plfile$assetTypeIds")
    private List<String> assetTypeIds;

    @SerializedName("plfile$assetTypes")
    private List<String> assetTypes;

    @SerializedName("plfile$mediaId")
    private String mediaId;

    @SerializedName("plfile$streamingUrl")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return id.substring(id.lastIndexOf("/") + 1);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public List<String> getAssetTypeIds() {
        return assetTypeIds;
    }

    public void setAssetTypeIds(List<String> assetTypeIds) {
        this.assetTypeIds = assetTypeIds;
    }

    public List<String> getAssetTypes() {
        return assetTypes;
    }

    public void setAssetTypes(List<String> assetTypes) {
        this.assetTypes = assetTypes;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MpxThumbnail [title=" + title + ", guid=" + guid + ", id=" + id
                + ", ownerId=" + ownerId + ", height=" + height + ", width="
                + width + ", assetTypeIds=" + assetTypeIds + ", assetTypes="
                + assetTypes + ", mediaId=" + mediaId + ", url=" + url + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((assetTypeIds == null) ? 0 : assetTypeIds.hashCode());
        result = prime * result
                + ((assetTypes == null) ? 0 : assetTypes.hashCode());
        result = prime * result + ((guid == null) ? 0 : guid.hashCode());
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((mediaId == null) ? 0 : mediaId.hashCode());
        result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((width == null) ? 0 : width.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MpxThumbnail other = (MpxThumbnail) obj;
        if (assetTypeIds == null) {
            if (other.assetTypeIds != null)
                return false;
        } else if (!assetTypeIds.equals(other.assetTypeIds))
            return false;
        if (assetTypes == null) {
            if (other.assetTypes != null)
                return false;
        } else if (!assetTypes.equals(other.assetTypes))
            return false;
        if (guid == null) {
            if (other.guid != null)
                return false;
        } else if (!guid.equals(other.guid))
            return false;
        if (height == null) {
            if (other.height != null)
                return false;
        } else if (!height.equals(other.height))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (mediaId == null) {
            if (other.mediaId != null)
                return false;
        } else if (!mediaId.equals(other.mediaId))
            return false;
        if (ownerId == null) {
            if (other.ownerId != null)
                return false;
        } else if (!ownerId.equals(other.ownerId))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (width == null) {
            if (other.width != null)
                return false;
        } else if (!width.equals(other.width))
            return false;
        return true;
    }

}
