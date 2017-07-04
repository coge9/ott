package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.EpisodeInfoBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.info.EpisodeInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 12-Apr-16.
 */
public class EpisodePage extends ContentTypePage {

    @FindBy(id = "edit-group_basic")
    private EpisodeInfoBlock episodeInfoBlock;

    public EpisodePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public EpisodePage enterContentTypeData(Content content) {
        this.enterBasicData(content.getGeneralInfo());
        this.enterEpisodeData(((Episode) content).getEpisodeInfo());
        enterCastCredit(content.getCastAndCredit());
        enterExternalLinksData(content.getExternalLinksInfo());
        enterPromotionalData(content.getPromotional());
        enterMediaData(content.getMediaImages());
        return this;
    }

    private EpisodePage enterBasicData(GeneralInfo generalInfo) {
        generalInfoBlock.expandTab();
        generalInfoBlock.enterTitle(generalInfo.getTitle());
        generalInfoBlock.enterSubhead(generalInfo.getSubhead());
        generalInfoBlock.enterShortDescription(generalInfo.getShortDescription());
        generalInfoBlock.enterMediumDescription(generalInfo.getMediumDescription());
        generalInfoBlock.enterLongDescription(generalInfo.getLongDescription());
        return this;
    }

    private EpisodePage enterEpisodeData(EpisodeInfo episodeInfo) {
        if (episodeInfo.isObjectNull()) {
            return this;
        }
        episodeInfoBlock.expandTab();
        episodeInfoBlock.selectSeries(episodeInfo.getParentSeries().getGeneralInfo().getTitle());
        episodeInfoBlock.selectSeason(episodeInfo.getParentSeason().getGeneralInfo().getTitle());
        episodeInfoBlock.checkEpisodeType(episodeInfo.getEpisodeType());
        episodeInfoBlock.enterEpisodeNumber(episodeInfo.getEpisodeNumber());
        episodeInfoBlock.enterSecondaryEpisodeNumber(episodeInfo.getSecondaryEpisodeNumber());
        episodeInfoBlock.enterOriginalAirDate(episodeInfo.getOriginalAirDate());
        episodeInfoBlock.selectRating(episodeInfo.getRating());
        episodeInfoBlock.enterProductionNumber(episodeInfo.getProductionNumber());
        episodeInfoBlock.enterSupplementaryAirDate(episodeInfo.getSupplementaryAirDate());
        episodeInfoBlock.enterSupplementaryAirTime(episodeInfo.getSupplementaryAirDate());
        return this;
    }

    @Override
    public EpisodePage create(Content content) {
        return (EpisodePage) this.enterContentTypeData(content).saveAsDraft();
    }

    @Override
    public EpisodePage createAndPublish(Content content) {
        return (EpisodePage) this.enterContentTypeData(content).publish();
    }

    @Override
    public Episode getPageData() {
        return null;
    }
}
