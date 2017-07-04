package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;


public class PersonPage extends PersonRoleAbstractPage {

    public PersonPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    public PersonPage enterContentTypeData(Content content) {
        enterMetadataInfo((PersonEntity) content);
        enterExternalLinksData(content.getExternalLinksInfo());
        enterMediaData(content.getMediaImages());
        enterSlugData(content.getSlugInfo());
        return this;
    }

    @Override
    public PersonPage create(Content content) {
        this.enterContentTypeData(content).saveAsDraft();
        return this;
    }

    @Override
    public PersonPage createAndPublish(Content content) {
        this.enterContentTypeData(content).publish();
        return this;
    }

    @Override
    public PersonEntity getPageData() {
        PersonEntity personEntityEntity = new PersonEntity();
        personEntityEntity.setMetadataInfo(getMetadataInfo());
        personEntityEntity.setExternalLinksInfo(getExternalLinksData());
        personEntityEntity.setSlugInfo(getSlugInfo());
        return personEntityEntity;
    }
}
