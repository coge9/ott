package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.*;

public class RokuProgramJson implements Metadata, Cloneable {


    @SerializedName("itemType")
    protected String itemType;
    @SerializedName("title")
    protected String title;
    @SerializedName("id")
    protected String id;
    @SerializedName("mpxId")
    protected String mpxId;
    @SerializedName("description")
    protected String description;
    @SerializedName("seriesType")
    private String seriesType;
    @SerializedName("seriesCategory")
    private String seriesCategory;
    @SerializedName("showColor")
    private String showColor;
    @SerializedName("images")
    private List<Image> images = new ArrayList<>();
    @SerializedName("dateModified")
    private String dateModified;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return itemType;
    }

    public void setType(String type) {
        this.itemType = type;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public String getSeriesCategory() {
        return seriesCategory;
    }

    public void setSeriesCategory(String seriesCategory) {
        this.seriesCategory = seriesCategory;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImages(List<Image> images) {
        this.images.addAll(images);
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowType() {
        return seriesType;
    }

    public void setShowType(String showType) {
        this.seriesType = showType;
    }

    public String getShowColor() {
        return showColor;
    }

    public void setShowColor(String showColor) {
        this.showColor = showColor;
    }

    @Override
    public String getMpxId() {
        return mpxId;
    }

    public void setMpxId(String mpxId) {
        this.mpxId = mpxId;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public boolean verify(RokuProgramJson other) {
        Boolean status = true;
        Utilities.logInfoMessage("Validation of programs");
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Utilities.logInfoMessage("Validation of the field " + field.getAnnotation(SerializedName.class).value());
            try {
                Object expected = field.get(other);
                Object actual = field.get(this);
                if (expected == null || actual == null) {
                    if (actual != null || expected != null) {
                        Utilities.logSevereMessage("Field is wrong. Expected: " + expected + ", but found: " + actual);
                        status = false;
                    } else {
                        Utilities.logInfoMessage("OK!");
                    }
                } else if (!actual.equals(expected)) {
                    Utilities.logSevereMessage("Field is wrong. Expected: " + expected + ", but found: " + actual);
                    status = false;
                } else {
                    Utilities.logInfoMessage("OK!");
                }
            } catch (IllegalArgumentException e) {
                Utilities.logSevereMessage("Error during verification " + Utilities.convertStackTraceToString(e));
            } catch (IllegalAccessException e) {
                Utilities.logSevereMessage("Error during verification " + Utilities.convertStackTraceToString(e));
            }
        }
        if (status) {
            Utilities.logInfoMessage("Validation passed");
        } else {
            Utilities.logSevereMessage("Validation failed");
        }
        return status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((title == null) ? 0 : title.hashCode());
        result = prime * result
                + ((itemType == null) ? 0 : itemType.hashCode());
        result = prime
                * result
                + ((seriesType == null) ? 0 : seriesType
                .hashCode());
        result = prime
                * result
                + ((seriesCategory == null) ? 0
                : seriesCategory.hashCode());
        result = prime * result + ((showColor == null) ? 0 : showColor.hashCode());
//        result = prime
//                * result
//                + ((images == null) ? 0 : images
//                .hashCode());
        result = prime * result + ((mpxId == null) ? 0 : mpxId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RokuProgramJson other = (RokuProgramJson) obj;
        if (id == null) {
            if (other.id != null) {
                Utilities.logSevereMessage("id1: [" + id + "] id2: [" + other.id + "]");
                return false;
            }
        } else if (!id.equals(other.id)) {
            Utilities.logSevereMessage("id1: [" + id + "] id2: [" + other.id + "]");
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                Utilities.logSevereMessage("description1: [" + description + "] description2: [" + other.description + "]");
                return false;
            }
        } else if (description.length() != other.description.length()) {
            Utilities.logSevereMessage("description1: [" + description + "] description2: [" + other.description + "]");
            return false;
        }
        if (itemType == null) {
            if (other.itemType != null) {
                Utilities.logSevereMessage("itemType1: [" + itemType + "] itemType2: [" + other.itemType + "]");
                return false;
            }
        } else if (!itemType.equals(other.itemType)) {
            Utilities.logSevereMessage("itemType1: [" + itemType + "] itemType2: [" + other.itemType + "]");
            return false;
        }
        if (seriesType == null) {
            if (other.seriesType != null) {
                Utilities.logSevereMessage("seriesType1: [" + seriesType + "] seriesType2: [" + other.seriesType + "]");
                return false;
            }
        } else if (!seriesType.equals(other.seriesType)) {
            Utilities.logSevereMessage("seriesType1: [" + seriesType + "] seriesType2: [" + other.seriesType + "]");
            return false;
        }
        if (showColor == null) {
            if (other.showColor != null) {
                Utilities.logSevereMessage("showColor1: [" + showColor + "] showColor2: [" + other.showColor + "]");
                return false;
            }
        } else if (!showColor.equals(other.showColor)) {
            Utilities.logSevereMessage("showColor1: [" + showColor + "] showColor2: [" + other.showColor + "]");
            return false;
        }
        if (seriesCategory == null) {
            if (other.seriesCategory != null) {
                Utilities.logSevereMessage("seriesCategory1: [" + seriesCategory + "] seriesCategory2: [" + other.seriesCategory + "]");
                return false;
            }
        } else if (!seriesCategory.equals(other.seriesCategory)) {
            Utilities.logSevereMessage("seriesCategory1: [" + seriesCategory + "] seriesCategory2: [" + other.seriesCategory + "]");
            return false;
        }
        if (images == null) {
            if (other.images != null) {
                return false;
            }
            new SoftAssert().assertContainsAll(other.images, images, "Not all images are published").isNotAnyError();
        }
        if (mpxId == null) {
            if (other.mpxId != null) {
                Utilities.logSevereMessage("mpxId1: [" + mpxId + "] mpxId2: [" + other.mpxId + "]");
                return false;
            }
        } else if (!mpxId.equalsIgnoreCase(other.mpxId)
                ) {
            Utilities.logSevereMessage("The size of items lists are not same");
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "RokuProgramJSON [id=" + id + ", title=" + title
                + ", description=" + description + ", itemType="
                + itemType + ", seriesCategory=" + seriesCategory + ", showColor=" + showColor
                + ", seriesType=" + seriesType + ", mpxId=" + mpxId
                + ";, images=" + images + "]";
    }


    public Map<String, String> getFieldsByName(String... names) {
        Utilities.logInfoMessage("Get Field by name from list");
        Map<String, String> resultMap = new HashMap<String, String>();
        Random rand = new Random();
        Class c = getClass();
        for (String name : names) {
            try {
                if (name.equals("seasons") || name.equals("thumbnails") || name.equals("customFields")) {
                    Object k = c.getDeclaredField(name).get(this);
                    @SuppressWarnings("unchecked")
                    List<String> array = (List<String>) c.getDeclaredField(name).get(this);
                    resultMap.put(name, array.get(rand.nextInt(array.size())));
                } else {
                    resultMap.put(name, (String) c.getDeclaredField(name).get(this));
                }
            } catch (NoSuchFieldException e) {
                Utilities.logSevereMessageThenFail("Havent field with this name!!!");
                e.printStackTrace();
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
            }
        }
        return resultMap;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
