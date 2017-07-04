package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.factory;


import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import org.springframework.stereotype.Component;

@Component("requiredPersonWithImageWithoutUsage")
public class WithRequiredPersonAndImagesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {

        PersonEntity personEntity = new PersonEntity();
        personEntity.setMetadataInfo(MetadataFactory.createRequiredPersonMetadataInfo());
        personEntity.setMediaImages(MediaFactory.createMediaWithImageCountWithoutUsages(2));
        return personEntity;
    }
}
