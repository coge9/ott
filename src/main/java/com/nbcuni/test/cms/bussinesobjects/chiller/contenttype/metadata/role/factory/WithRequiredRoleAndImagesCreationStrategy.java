package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.factory;


import com.nbcuni.test.cms.bussinesobjects.chiller.chillerdatafactory.MediaFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataFactory;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.RoleEntity;
import org.springframework.stereotype.Component;

@Component("requiredRoleWithImageWithoutUsage")
public class WithRequiredRoleAndImagesCreationStrategy implements ContentTypeCreationStrategy {
    @Override
    public Content createContentType() {

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setMetadataInfo(MetadataFactory.createRequiredRoleMetadataInfo());
        roleEntity.setMediaImages(MediaFactory.createMediaWithImageCountWithoutUsages(2));
        return roleEntity;
    }
}
