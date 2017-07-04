package com.nbcuni.test.cms.pageobjectutils.tvecms;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;

public class VoidShelf {

    private String title = null;
    private Slug alias = null;
    private String id = null;
    private String settings = null;
    private String type = null;

    public VoidShelf() {
        super();
    }

    public VoidShelf(String title, Slug alias, String settings) {
        super();
        this.title = title;
        this.alias = alias;
        this.settings = settings;
    }

    public String getTitle() {
        return title;
    }

    public VoidShelf setTitle(String title) {
        this.title = title;
        return this;
    }

    public Slug getAlias() {
        return alias;
    }

    public void setAlias(Slug alias) {
        this.alias = alias;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result
                + ((settings == null) ? 0 : settings.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        VoidShelf other = (VoidShelf) obj;
        if (alias == null) {
            if (other.alias != null)
                return false;
        } else if (!alias.equals(other.alias))
            return false;
        if (settings == null) {
            if (other.settings != null)
                return false;
        } else if (!settings.equals(other.settings))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "VoidShelf [title=" + title + ", alias=" + alias + ", settings="
                + settings + "]";
    }

}
