package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.webdriver.Utilities;

import java.util.*;

public class RokuPageJson extends AbstractEntity implements Metadata, Cloneable {


    @SerializedName("name")
    private String name;

    @SerializedName("pageId")
    private String pageId;

    @SerializedName("description")
    private String description;

    @SerializedName("modules")
    private List<PageModulesJson> modules;

    @SerializedName("alias")
    private String alias;

    @SerializedName("appId")
    private String appId;

    @SerializedName("pages")
    private List<String> pages;

    @SerializedName("meta")
    private List<String> meta;

    @SerializedName("dateModified")
    private String dateModified;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<PageModulesJson> getModules() {
        return modules;
    }

    public void setModules(List<PageModulesJson> modules) {
        this.modules = modules;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getMpxId() {
        return null;
    }

    @Override
    public String getId() {
        return pageId;
    }

    public void setId(String pageId) {
        this.pageId = pageId;
    }

    @Override
    public String getTitle() {
        return null;
    }

    public List<String> getMeta() {
        return meta;
    }

    public void setMeta(List<String> meta) {
        this.meta = meta;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((pageId == null) ? 0 : pageId.hashCode());
        result = prime * result
                + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result
                + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((alias == null) ? 0 : alias.hashCode());
        result = prime
                * result
                + ((modules == null) ? 0 : modules
                .hashCode());
        result = prime
                * result
                + ((pages == null) ? 0
                : pages.hashCode());
        result = prime * result + ((meta == null) ? 0 : meta.hashCode());
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
        RokuPageJson other = (RokuPageJson) obj;
      /*  if (pageId == null) {
            if (other.pageId != null) {
                return false;
            }
        } else if (!pageId.equals(other.pageId)) {
            return false;
        }*/
      /*  if (appId == null) {
            if (other.appId != null) {
                return false;
            }
        } else if (!appId.equals(other.appId)) {
            return false;
        }*/
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (alias == null) {
            if (other.alias != null) {
                return false;
            }
        } else if (!alias.equals(other.alias)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (modules == null) {
            if (other.modules != null) {
                return false;
            }
        } else if (modules.size() != other.modules.size()) {
            Utilities.logSevereMessage("The size of items lists are not same");
            return false;
        } else if (!modules.containsAll(other.modules) && other.modules.containsAll(modules)) {
            Utilities.logSevereMessage("The items lists are not same");
            return false;
        }
        if (pages == null) {
            if (other.pages != null) {
                return false;
            }
        } else if (pages.size() != other.pages.size()) {
            Utilities.logSevereMessage("The size of items lists are not same");
            return false;
        }
        if (meta == null) {
            if (other.meta != null) {
                return false;
            }
        } else if (meta.size() != other.meta.size()) {
            Utilities.logSevereMessage("The size of items lists are not same");
            return false;
        }
        return true;
    }

    public Map<String, String> getFieldsByName(String... names) {
        Utilities.logInfoMessage("Get Field by name from list");
        Map<String, String> resultMap = new HashMap<String, String>();
        Random rand = new Random();
        Class c = getClass();
        for (String name : names) {
            try {
                if (name.equals("modules") || name.equals("pages") || name.equals("meta")) {
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

    public RokuPageJson getObject(PageForm pageInfo, String brand) {
        Utilities.logInfoMessage("Set Page JSON Object as expected data");
        RokuPageJson rokuPageJson = new RokuPageJson();
        rokuPageJson.setName(pageInfo.getTitle());
        rokuPageJson.setId(pageInfo.getPageID());
        rokuPageJson.setDescription("");
        rokuPageJson.setModules(pageInfo.getShelveIds());
        rokuPageJson.setAlias(pageInfo.getAlias().getSlugValue());
        rokuPageJson.setAppId(pageInfo.getPlatform().getAppId(brand));
        rokuPageJson.setMeta(new ArrayList<>());
        rokuPageJson.setPages(new ArrayList<>());
        rokuPageJson.setDateModified(pageInfo.getTime());
        return rokuPageJson;
    }

}
