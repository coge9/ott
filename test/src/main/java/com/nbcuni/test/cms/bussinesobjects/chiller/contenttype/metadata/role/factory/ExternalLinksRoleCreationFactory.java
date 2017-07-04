package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.ExternalLinkFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.RoleEntity;
import org.springframework.stereotype.Component;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
@Component("externalLinkRole")
public class ExternalLinksRoleCreationFactory implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setMetadataInfo(MetadataFactory.createRequiredRoleMetadataInfo());
        roleEntity.setExternalLinksInfo(ExternalLinkFactory.createRandomExternalLinkWithCount(1));
        return roleEntity;
    }
}
