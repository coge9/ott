package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import org.springframework.stereotype.Component;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
@Component("emptyPerson")
public class PersonEmptyFactory implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        PersonEntity personEntity = new PersonEntity();
        return personEntity;
    }
}
