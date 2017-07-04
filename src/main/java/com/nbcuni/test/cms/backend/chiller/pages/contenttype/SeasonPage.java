package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.SeasonBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.GeneralInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.info.SeasonInfo;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 11-Apr-16.
 */
public class SeasonPage extends ContentTypePage {

    @FindBy(id = "edit-group_basic")
    private SeasonBlock seasonBlock;

    public SeasonPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public SeasonPage enterContentTypeData(Content content) {
        this.enterBasicData(content.getGeneralInfo());
        this.enterSeasonData(((Season) content).getSeasonInfo());
        enterCastCredit(content.getCastAndCredit());
        enterPromotionalData(content.getPromotional());
        enterExternalLinksData(content.getExternalLinksInfo());
        enterMediaData(content.getMediaImages().size());
        return this;
    }

    private SeasonPage enterBasicData(GeneralInfo generalInfo) {
        generalInfoBlock.expandTab();
        generalInfoBlock.enterTitle(generalInfo.getTitle());
        generalInfoBlock.enterSubhead(generalInfo.getSubhead());
        generalInfoBlock.enterShortDescription(generalInfo.getShortDescription());
        generalInfoBlock.enterMediumDescription(generalInfo.getMediumDescription());
        generalInfoBlock.enterLongDescription(generalInfo.getLongDescription());
        return this;
    }

    private SeasonPage enterSeasonData(SeasonInfo seasonInfo) {
        seasonBlock.expandTab();
        if (seasonInfo.getParentProgram() != null) {
            seasonBlock.selectProgram(seasonInfo.getProgram(), seasonInfo.getParentProgram().getType().getItemType());
        } else {
            seasonBlock.selectProgram(seasonInfo.getProgram());
        }
        seasonBlock.enterSeasonNumber(seasonInfo.getSeasonNumber());
        seasonBlock.enterProductionNumber(seasonInfo.getProductionNumber());
        seasonBlock.enterStartDate(seasonInfo.getStartDate());
        seasonBlock.enterEndDate(seasonInfo.getEndDate());
        return this;
    }

    @Override
    public SeasonPage create(Content content) {
        return (SeasonPage) this.enterContentTypeData(content).saveAsDraft();
    }

    @Override
    public SeasonPage createAndPublish(Content content) {
        return (SeasonPage) this.enterContentTypeData(content).saveAsDraft().publish();
    }

    @Override
    public Season getPageData() {
        return null;
    }
}
