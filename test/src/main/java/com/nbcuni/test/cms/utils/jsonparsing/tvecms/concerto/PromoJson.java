package com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto;

import com.nbcuni.test.cms.bussinesobjects.abstractentity.AbstractEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Link;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PromoJson extends AbstractEntity implements Cloneable {

    private String uuid;
    private String slug;
    private String title;
    private String promoKicker;
    private String promoTitle;
    private String promoDescription;
    private String itemType;
    private int revision;
    private boolean published;
    private ChannelReferencesJson program;
    private List<String> categories;
    private List<String> tags;
    private List<MediaJson> media;
    private List<LinkJson> links;

    public PromoJson() {
    }

    public PromoJson(Promo promo) {
        getObject(promo);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MediaJson> getMedia() {
        return media;
    }

    public void setMedia(List<MediaJson> media) {
        this.media = media;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<LinkJson> getLinks() {
        return links;
    }

    public void setLinks(List<LinkJson> links) {
        this.links = links;
    }


    public String getPromoKicker() {
        return promoKicker;
    }

    public void setPromoKicker(String promoKicker) {
        this.promoKicker = promoKicker;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public ChannelReferencesJson getProgram() {
        return program;
    }

    public void setProgram(ChannelReferencesJson program) {
        this.program = program;
    }

    private PromoJson getObject(Promo promo) {
        this.uuid = promo.getGeneralInfo().getUuid();
        this.itemType = ItemTypes.PROMO.getItemType();
        this.revision = promo.getGeneralInfo().getRevision();
        this.title = promo.getBasic().getTitle();
        this.slug = promo.getSlugInfo().getSlugValue();

        List<MediaJson> medias = new ArrayList<>();
        if (promo.getImage().getName() != null) {
            MediaJson mediaJson = new MediaJson();
            mediaJson.setObject(promo.getImage());
            medias.add(mediaJson);
        }
        this.media = medias;

        this.categories = promo.getAssociations().getCategories();
        this.tags = new ArrayList<>();
        this.program = new ChannelReferencesJson();
        this.published = true;
        List<LinkJson> linkJsons = new ArrayList<>();
        if (!CollectionUtils.isEmpty(promo.getLinks().getLinks())) {
            for (Link link : promo.getLinks().getLinks()) {
                LinkJson linkJson = new LinkJson();
                linkJson.getObject(link);
                linkJsons.add(linkJson);
            }
        }
        this.links = linkJsons;

        this.promoKicker = promo.getBasic().getPromoKicker() != null ? promo.getBasic().getPromoKicker() : "";
        this.promoDescription = promo.getBasic().getPromoDescription() != null ? promo.getBasic().getPromoDescription() : "";
        this.promoTitle = promo.getBasic().getPromoTitle() != null ? promo.getBasic().getPromoTitle() : "";
        return this;
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
