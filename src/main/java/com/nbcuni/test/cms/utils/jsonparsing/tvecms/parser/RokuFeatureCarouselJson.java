package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.webdriver.Utilities;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Ivan_Karnilau on 13-Jan-16.
 */
public class RokuFeatureCarouselJson {

    @SerializedName("id")
    private String id;

    @SerializedName("moduleType")
    private String moduleType;

    @SerializedName("properties")
    private RokuShelfProperties properties;

    @SerializedName("itemCount")
    private int itemCount;

    @SerializedName("tileType")
    private int tileType;

    @SerializedName("title")
    private String title;

    @SerializedName("alias")
    private String alias;

    @SerializedName("listId")
    private String listId;

    @SerializedName("dateModified")
    private String dateModified;

    @SerializedName("datePublished")
    private String datePublished;

    @SerializedName("dateCreated")
    private String dateCreated;

    @SerializedName("data")
    private JsonObject data;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public RokuShelfProperties getProperties() {
        return properties;
    }

    public void setProperties(RokuShelfProperties properties) {
        this.properties = properties;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getTile() {
        return title;
    }

    public void setTile(String tile) {
        this.title = tile;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public boolean verify(RokuShelfJson other) {
        Boolean status = true;
        Utilities.logInfoMessage("Validation of pages");
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
                + ((moduleType == null) ? 0 : moduleType.hashCode());
        result = prime * result
                + ((properties == null) ? 0 : properties.hashCode());
        result = prime
                * result
                + ((title == null) ? 0 : title.hashCode());
        result = prime
                * result
                + ((listId == null) ? 0
                : listId.hashCode());
        result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
        result = prime * result + ((datePublished == null) ? 0 : datePublished.hashCode());
        result = prime * result + ((dateModified == null) ? 0 : dateModified.hashCode());
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
        RokuFeatureCarouselJson other = (RokuFeatureCarouselJson) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (moduleType == null) {
            if (other.moduleType != null) {
                return false;
            }
        } else if (!moduleType.equals(other.moduleType)) {
            return false;
        }
        if (properties == null) {
            if (other.properties != null) {
                return false;
            }
        } else if (!properties.equals(other.properties)) {
            return false;
        }
        if (itemCount == 0) {
            if (other.itemCount != 0) {
                return false;
            }
        } else if (itemCount != other.itemCount) {
            return false;
        }
        if (tileType == 0) {
            if (other.tileType != 0) {
                return false;
            }
        } else if (tileType != other.tileType) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        if (listId == null) {
            if (other.listId != null) {
                return false;
            }
        } else if (listId != other.listId
                ) {
            return false;
        }
        if (dateModified == null) {
            if (other.dateModified != null) {
                return false;
            }
        } else if (dateModified != other.dateModified
                ) {
            return false;
        }
        if (datePublished == null) {
            if (other.datePublished != null) {
                return false;
            }
        } else if (datePublished != other.datePublished
                ) {
            return false;
        }
        if (dateCreated == null) {
            if (other.dateCreated != null) {
                return false;
            }
        } else if (dateCreated != other.dateCreated
                ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RokuShelfJson [id=" + id + ", moduleType=" + moduleType
                + ", properties=" + properties + ", itemCount="
                + itemCount + ", tileType=" + tileType + ", title=" + title
                + ", listId=" + listId + ", dateModified=" + dateModified + ", datePublished=" + datePublished + ", dateCreated=" + dateCreated + ", data=" + data.toString() + "]";
    }


    public Map<String, String> getFieldsByName(String... names) {
        Utilities.logInfoMessage("Get Field by name from list");
        Map<String, String> resultMap = new HashMap<String, String>();
        Random rand = new Random();
        Class c = getClass();
        for (String name : names) {
            try {
                if (name.equals("properties")) {
                    resultMap.put(name, c.getDeclaredField(name).get(this).toString());
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
}
