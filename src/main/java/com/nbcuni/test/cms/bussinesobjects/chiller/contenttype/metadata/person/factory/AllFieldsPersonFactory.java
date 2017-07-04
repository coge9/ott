package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.ExternalLinkFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import org.springframework.stereotype.Component;


/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
@Component("withAllFieldsPerson")
public class AllFieldsPersonFactory implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setMetadataInfo(MetadataFactory.createAllPersonMetadata());
        personEntity.setExternalLinksInfo(ExternalLinkFactory.createRandomExternalLinkWithCount(2));
        personEntity.setMediaImages(MediaFactory.createMediaWithImageCount(2));
        return personEntity;
    }
}
