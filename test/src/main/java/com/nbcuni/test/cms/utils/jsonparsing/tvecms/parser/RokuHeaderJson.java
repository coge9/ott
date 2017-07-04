package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RokuHeaderJson implements Metadata {

    @SerializedName("id")
    private String id;

    @SerializedName("appId")
    private String appId;

    @SerializedName("mvpdId")
    private String mvpdId;

    @SerializedName("alias")
    private String alias;

    @SerializedName("contentId")
    private String contentId;

    @SerializedName("itemType")
    private String itemType;

    @SerializedName("images")
    private List<Image> images;

    public RokuHeaderJson() {
        super();
    }

    public RokuHeaderJson(String id, String appId, String mvpdId, String alias,
                          String imageUrl) {
        super();
        this.id = id;
        this.appId = appId;
        this.mvpdId = mvpdId;
        this.alias = alias;
        this.contentId = "";
        this.itemType = "Header";
        Image image = new Image();
        image.setName("header");
        image.setImageUrl(imageUrl);
        image.setHeight(Config.getInstance().getRokuHeaderImageHeight());
        image.setWidth(Config.getInstance().getRokuHeaderImageWidth());
        image.setUpdate(true);
        List<Image> im = new ArrayList<Image>();
        im.add(image);
        this.images = im;
    }

    @Override
    public String getMpxId() {
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return null;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMvpdId() {
        return mvpdId;
    }

    public void setMvpdId(String mvpdId) {
        this.mvpdId = mvpdId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "RokuHeaderJson [id=" + id + ", appId=" + appId + ", mvpdId="
                + mvpdId + ", alias=" + alias + ", contentId=" + contentId
                + ", itemType=" + itemType + ", images=" + images + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result
                + ((contentId == null) ? 0 : contentId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((images == null) ? 0 : images.hashCode());
        result = prime * result
                + ((itemType == null) ? 0 : itemType.hashCode());
        result = prime * result + ((mvpdId == null) ? 0 : mvpdId.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SoftAssert softAssert = new SoftAssert();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object thisValue = field.get(this);
                Object otherValue = field.get(obj);
                if (field.getName().equals("images")) {
                    softAssert.assertTrue(
                            ((List<Image>) thisValue).get(0).equals(
                                    ((List<Image>) otherValue).get(0)),
                            "There is error in Images");
                } else {
                    if (thisValue == null) {
                        softAssert.assertTrue(otherValue == null, "Field "
                                + field.getName() + " is not NULL as expected");
                    } else {
                        if (otherValue == null) {
                            softAssert.assertTrue(otherValue == null, "Field "
                                    + field.getName()
                                    + " is NULL but it should be set");
                        } else {
                            softAssert.assertEquals(thisValue, otherValue,
                                    "Field " + field.getName()
                                            + " doesn't match");
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                Utilities.logSevereMessage("ERROR DURING IMAGE OBJECT COMPARATION");
            } catch (IllegalAccessException e) {
                Utilities.logSevereMessage("ERROR DURING IMAGE OBJECT COMPARATION");
            }
        }
        return softAssert.isNotAnyError();
    }

    public class Image {
        private String name;
        private String imageUrl;
        private String width;
        private String height;
        private Boolean update;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public Boolean getUpdate() {
            return update;
        }

        public void setUpdate(Boolean update) {
            this.update = update;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result
                    + ((height == null) ? 0 : height.hashCode());
            result = prime * result
                    + ((imageUrl == null) ? 0 : imageUrl.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result
                    + ((update == null) ? 0 : update.hashCode());
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
            SoftAssert softAssert = new SoftAssert();
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals("this$0")) {
                    continue;
                }
                try {
                    Object thisValue = field.get(this);
                    Object otherValue = field.get(obj);
                    if (thisValue == null) {
                        softAssert.assertTrue(otherValue == null, "Field "
                                + field.getName() + " is not NULL as expected");
                    } else {
                        if (otherValue == null) {
                            softAssert.assertTrue(otherValue == null, "Field "
                                    + field.getName()
                                    + " is NULL but it should be set");
                        } else {
                            softAssert.assertEquals(thisValue, otherValue,
                                    "Field " + field.getName()
                                            + " doesn't match");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    Utilities.logSevereMessage("ERROR DURING IMAGE OBJECT COMPARATION");
                } catch (IllegalAccessException e) {
                    Utilities.logSevereMessage("ERROR DURING IMAGE OBJECT COMPARATION");
                }
            }
            return softAssert.isNotAnyError();
        }

        private RokuHeaderJson getOuterType() {
            return RokuHeaderJson.this;
        }

        @Override
        public String toString() {
            return "Image [name=" + name + ", imageUrl=" + imageUrl
                    + ", width=" + width + ", height=" + height + ", update="
                    + update + "]";
        }

    }

}
