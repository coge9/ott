package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class ChannelReferenceBlock extends AbstractContainer {

    private static final String NONE = "- NONE -";

    @FindBy(xpath = ".//*[contains(@name,'[show]')]")
    private DropDownList series;

    @FindBy(xpath = ".//*[contains(@name,'[season]')]")
    private DropDownList season;

    @FindBy(xpath = ".//*[contains(@name,'[episode]')]")
    private DropDownList episode;


    public void enterChannelReference(ChannelReference channelReference) {
        if (channelReference.isObjectNull()) {
            return;
        }
        this.selectSeries(channelReference.getSeries());
        this.selectSeason(channelReference.getSeason());
        this.selectEpisode(channelReference.getEpisode());
    }

    public ChannelReference getChannelReferenceInfo() {
        String currentSeries = series.getSelectedValue();
        currentSeries = currentSeries.equalsIgnoreCase(NONE) ? null : currentSeries;
        String type = series.getOptgroup(currentSeries);
        ChannelReference channelReference = new ChannelReference();
        if (ItemTypes.EVENT.getItemType().equalsIgnoreCase(type)) {
            channelReference.setItemType(ItemTypes.EVENT.getItemType());
        }
        String currentSeason = season.getSelectedValue();
        currentSeason = currentSeason.equalsIgnoreCase(NONE) ? null : currentSeason;
        String currentEpisode = episode.getSelectedValue();
        currentEpisode = currentEpisode.equalsIgnoreCase(NONE) ? null : currentEpisode;
        return channelReference.setSeries(currentSeries).setSeason(currentSeason).setEpisode(currentEpisode);
    }

    private void selectSeries(String series) {
        this.series.selectWithAjaxWaiting(series);
    }

    private void selectSeason(String season) {
        this.season.selectWithAjaxWaiting(season);
    }

    private void selectEpisode(String episode) {
        this.episode.selectFromDropDown(episode);
    }
}
