package com.nbcuni.test.cms.backend.chiller.block.contenttype;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class ChannelReferenceAssociationsBlock extends AbstractContainer {

    @FindBy(xpath = ".//fieldset[contains(@id,'field_series_season_episode')]")
    private ChannelReferenceBlock channelReferenceBlock;

    public void enterAssociations(ChannelReferenceAssociations channelReferenceAssociations) {
        this.channelReferenceBlock.enterChannelReference(channelReferenceAssociations.getChannelReference());
    }

    public ChannelReferenceAssociations getChannelReferenceAssociationsInfo() {
        ChannelReference currentChannelReference = channelReferenceBlock.getChannelReferenceInfo();
        return new ChannelReferenceAssociations().setChannelReference(currentChannelReference);
    }
}
