package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.factory;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.RoleEntity;
import org.springframework.stereotype.Component;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
@Component("defaultRole")
public class RoleDefaultFactory implements ContentTypeCreationStrategy {

    @Override
    public Content createContentType() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setMetadataInfo(MetadataFactory.createRequiredRoleMetadataInfo());
        return roleEntity;
    }

}
