package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.nbcuni.test.cms.utils.SimpleUtils;

/**
 * Created by Ivan_Karnilau on 22-Apr-16.
 */
public class AssociationsCreator {

    private AssociationsCreator(){
        super();
    }

    public static Associations getRandomAssociations() {
        Associations associations = new Associations();
        associations.setTags(TagsCreator.getRandomTags(4));
        return associations;
    }
}
