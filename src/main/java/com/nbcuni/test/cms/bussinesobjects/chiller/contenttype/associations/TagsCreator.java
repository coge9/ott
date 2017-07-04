package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations;

import com.nbcuni.test.cms.utils.SimpleUtils;

import java.util.ArrayList;
import java.util.List;

public class TagsCreator {

    private static final String TAGS_PREFIX = "AQA tag";

    public static Tag getRandomTag() {
        Tag tag = new Tag();
        tag.setTag(TAGS_PREFIX + SimpleUtils.getRandomString(4));
        tag.setDescription(SimpleUtils.getRandomStringWithRandomLength(30));
        tag.setWeight(SimpleUtils.getRandomIntInRange(0, 5));
        return tag;
    }

    public static List<Tag> getRandomTags(int tagsCount) {
        List<Tag> toReturn = new ArrayList<>();
        for (int i = 0; i < tagsCount; i++) {
            toReturn.add(new Tag(TAGS_PREFIX + SimpleUtils.getRandomString(4)));
        }
        return toReturn;
    }

}
