package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata;

import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.SimpleUtils;

import static com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetadataConstants.*;


/**
 * Created by Alena_Aukhukova on 6/20/2016.
 */
public class MetadataFactory {

    private MetadataFactory(){
        super();
    }

    public static MetadataInfo createAllPersonMetadata() {
        String postfix = SimpleUtils.getRandomString(4);
        MetadataInfo metadataInfo = createRequiredPersonMetadataInfo();
        setCommonFields(metadataInfo);
        metadataInfo.setLastName(LAST_PERSON_NAME + postfix)
                .setMiddleName(MIDDLE_PERSON_NAME + postfix);
        return metadataInfo;
    }

    public static MetadataInfo createAllRoleMetadata() {
        String postfix = SimpleUtils.getRandomString(4);
        MetadataInfo metadataInfo = createRequiredRoleMetadataInfo();
        setCommonFields(metadataInfo);
        metadataInfo.setFirstName(FIRST_ROLE_NAME + postfix)
                .setLastName(LAST_ROLE_NAME + postfix)
                .setMiddleName(MIDDLE_ROLE_NAME + postfix)
                .setItemType(ItemTypes.ROLE.getItemType());
        return metadataInfo;
    }

    private static MetadataInfo setCommonFields(MetadataInfo metadataInfo) {
        String postfix = SimpleUtils.getRandomString(4);
        return metadataInfo.setBio(BIO + postfix)
                .setPrefix(PREFIX + postfix)
                .setSuffix(SUFFIX + postfix);
    }

    public static MetadataInfo createRequiredRoleMetadataInfo() {
        String postfix = SimpleUtils.getRandomString(4);
        return new MetadataInfo()
                .setFirstName(FIRST_ROLE_NAME + postfix)
                .setItemType(ItemTypes.ROLE.getItemType());
    }

    public static MetadataInfo createRequiredPersonMetadataInfo() {
        String postfix = SimpleUtils.getRandomString(4);
        return new MetadataInfo()
                .setFirstName(FIRST_PERSON_NAME + postfix)
                .setItemType(ItemTypes.PERSON.getItemType());
    }
}
