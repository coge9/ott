package com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic;

import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.OrderType;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.SortingType;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic.Programs;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic.VideoType;

/**
 * Created by Ivan_Karnilau on 5/2/2017.
 */
public class Dynamic extends Module {

    private Boolean displayTitle;
    private ContentType contentType;
    private VideoType videoType;
    private Integer maxContentItem;
    private SortingType sortBy;
    private OrderType order;
    private Programs programs;

    public Dynamic() {
        setTitleVariant("3 tile");
    }

    public Boolean getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(Boolean displayTitle) {
        this.displayTitle = displayTitle;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public VideoType getVideoType() {
        return videoType;
    }

    public void setVideoType(VideoType videoType) {
        this.videoType = videoType;
    }

    public Integer getMaxContentItem() {
        return maxContentItem;
    }

    public void setMaxContentItem(Integer maxContentItem) {
        this.maxContentItem = maxContentItem;
    }

    public SortingType getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortingType sortBy) {
        this.sortBy = sortBy;
    }

    public OrderType getOrder() {
        return order;
    }

    public void setOrder(OrderType order) {
        this.order = order;
    }

    public Programs getPrograms() {
        return programs;
    }

    public void setPrograms(Programs programs) {
        this.programs = programs;
    }
}
