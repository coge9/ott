package com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses;

/**
 * Created by Dzianis Kulesh on 3/23/2017.
 */
public class ModuleNodeJson {

    /**
     * This class is represent concrete module json for Content API
     */

    private Integer id;
    private String title;
    private String type;
    private String alias;
    private String updated;
    private String created;
    private String uid;
    private String vid;
    private String display_title;
    private String vuuid;
    private String uuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDisplay_title() {
        return display_title;
    }

    public void setDisplay_title(String display_title) {
        this.display_title = display_title;
    }

    public String getVuuid() {
        return vuuid;
    }

    public void setVuuid(String vuuid) {
        this.vuuid = vuuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModuleNodeJson that = (ModuleNodeJson) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) {
            return false;
        }
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) {
            return false;
        }
        if (created != null ? !created.equals(that.created) : that.created != null) {
            return false;
        }
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) {
            return false;
        }
        if (vid != null ? !vid.equals(that.vid) : that.vid != null) {
            return false;
        }
        if (display_title != null ? !display_title.equals(that.display_title) : that.display_title != null)
            return false;
        if (vuuid != null ? !vuuid.equals(that.vuuid) : that.vuuid != null) {
            return false;
        }
        return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (vid != null ? vid.hashCode() : 0);
        result = 31 * result + (display_title != null ? display_title.hashCode() : 0);
        result = 31 * result + (vuuid != null ? vuuid.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ModuleNodeJson{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", alias='").append(alias).append('\'');
        sb.append(", updated='").append(updated).append('\'');
        sb.append(", created='").append(created).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", vid='").append(vid).append('\'');
        sb.append(", display_title='").append(display_title).append('\'');
        sb.append(", vuuid='").append(vuuid).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
