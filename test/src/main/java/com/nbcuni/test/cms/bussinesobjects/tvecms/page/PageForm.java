package com.nbcuni.test.cms.bussinesobjects.tvecms.page;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.ListItemsJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.PageModulesJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 18.03.2016.
 */
public class PageForm implements Cloneable {

    protected List<ListItemsJson> itemsJson = new ArrayList<>();
    private String title = null;
    private String machineName = null;
    private String uuid = null;
    private String revision = null;
    private CmsPlatforms platform = null;
    private Slug alias = new Slug();
    private PanelizerTemplates layout = null;
    private String time = null;
    private List<Module> modules = new ArrayList<>();
    private String pageID = null;
    private String stringPlatform = null;

    public PageForm() {
    }

    public String getStringPlatform() {
        return stringPlatform;
    }

    public void setStringPlatform(String stringPlatform) {
        this.stringPlatform = stringPlatform;
    }

    public String getTitle() {
        return title;
    }

    public PageForm setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMachineName() {
        return machineName;
    }

    public PageForm setMachineName(String machineName) {
        this.machineName = machineName;
        return this;
    }

    public CmsPlatforms getPlatform() {
        return platform;
    }

    public PageForm setPlatform(CmsPlatforms platform) {
        this.platform = platform;
        return this;
    }

    public Slug getAlias() {
        return alias;
    }

    public PageForm setAlias(Slug alias) {
        this.alias = alias;
        return this;
    }

    public PanelizerTemplates getLayout() {
        return layout;
    }

    public PageForm setLayout(PanelizerTemplates layout) {
        this.layout = layout;
        return this;
    }

    public String getPageID() {
        return pageID;
    }

    public PageForm setPageID(String pageID) {
        this.pageID = pageID;
        return this;
    }

    public String getTime() {
        return time;
    }

    public PageForm setTime(String time) {
        this.time = time;
        return this;
    }

    public List<ListItemsJson> getItemsJson() {
        for (Module module : modules) {
            ListItemsJson itemJson = new ListItemsJson();
            itemJson.setItemType(ItemTypes.COLLECTIONS.getItemType());
            itemJson.setUuid(module.getUUID());
            itemsJson.add(itemJson);
        }
        return itemsJson;
    }

    public void setItemsJson(List<ListItemsJson> itemsJson) {
        this.itemsJson = itemsJson;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Module> getModules() {
        return modules;
    }

    public PageForm setModules(List<Module> modules) {
        this.modules = modules;
        return this;
    }

    public List<PageModulesJson> getShelveIds() {
        List<PageModulesJson> modulesJsons = new ArrayList<>();
        if (modules != null) {
            for (Module module : modules) {
                PageModulesJson pageModulesJson = new PageModulesJson();
                pageModulesJson.setId(module.getId());
                pageModulesJson.setModuleType("");
                modulesJsons.add(pageModulesJson);
            }
        }
        return modulesJsons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageForm pageForm = (PageForm) o;

        if (title != null ? !title.equals(pageForm.title) : pageForm.title != null) return false;
        if (machineName != null ? !machineName.equals(pageForm.machineName) : pageForm.machineName != null)
            return false;
        if (platform != null ? !platform.equals(pageForm.platform) : pageForm.platform != null) return false;
        if (alias != null ? !alias.equals(pageForm.alias) : pageForm.alias != null) return false;
        return layout != null ? layout.equals(pageForm.layout) : pageForm.layout == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (machineName != null ? machineName.hashCode() : 0);
        result = 31 * result + (platform != null ? platform.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PageForm{" +
                "title='" + title + '\'' +
                ", machineName='" + machineName + '\'' +
                ", platform='" + platform + '\'' +
                ", alias='" + alias + '\'' +
                ", layout='" + layout + '\'' +
                '}';
    }

    @Override
    public Object clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        PageForm pageForm = new PageForm();
        pageForm
                .setTitle(title)
                .setMachineName(machineName)
                .setAlias(alias)
                .setPlatform(platform)
                .setLayout(layout);
        return pageForm;
    }
}
