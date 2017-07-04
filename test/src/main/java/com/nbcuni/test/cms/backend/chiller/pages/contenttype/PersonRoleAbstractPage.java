package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.MetadataInfoBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetaDataEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataInfo;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.metadata.MetadataJson;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Alena_Aukhukova on 6/20/2016.
 */
public abstract class PersonRoleAbstractPage extends ContentTypePage {

    @FindBy(id = "edit-group_general_info")
    private MetadataInfoBlock metadataInfoBlock;

    public PersonRoleAbstractPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public MetadataInfoBlock getMetadataInfoBlock() {
        metadataInfoBlock.expandTab();
        return metadataInfoBlock;
    }

    public MetadataJson getMetadataForPublishing(MetaDataEntity metaDataEntity) {
        MetadataJson metadataJson = new MetadataJson(metaDataEntity);
        metadataJson.setSlug(getSlugInfo().getSlugValue());
        return metadataJson;
    }

    public void enterMetadataInfo(MetaDataEntity metaDataEntity) {
        getMetadataInfoBlock().enterMetadataInfo(metaDataEntity.getMetadataInfo());
    }

    public MetadataInfo getMetadataInfo() {
        return getMetadataInfoBlock().getMetadataInfo();
    }

}
